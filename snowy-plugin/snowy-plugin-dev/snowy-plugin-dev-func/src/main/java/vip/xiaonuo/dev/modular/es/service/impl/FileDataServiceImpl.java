package vip.xiaonuo.dev.modular.es.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.dev.modular.es.entity.FileData;
import vip.xiaonuo.dev.modular.es.param.EsSearchParam;
import vip.xiaonuo.dev.modular.es.service.FileDataService;
import vip.xiaonuo.dev.modular.es.service.FileDataRepository;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/31 11:29
 */
@Component
@Slf4j
public class FileDataServiceImpl implements FileDataService {

    @Resource
    private FileDataRepository fileDataRepository;

    // 指定要应用的 Pipeline
    private  static  final  String pipelineName = "attachment";

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private ElasticsearchOperations elasticsearchOperations;

    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void save(FileData fileData) {
        // 创建 IndexRequest 对象并设置要保存的文档内容
        IndexRequest indexRequest = new IndexRequest("file_data");
        indexRequest.setPipeline(pipelineName);
        indexRequest.source(elasticsearchOperations.getElasticsearchConverter().mapObject(fileData));

        // 使用 RestHighLevelClient 执行保存请求
        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es 存储文档失败---");
            //throw new RuntimeException(e);
        }
    }

    @Override
    public Page<FileData> searchFullKey(EsSearchParam esSearchParam) {
        if (StrUtil.isEmpty(esSearchParam.getSearchKey())) {
            return new Page<>();
        }

        // 构建 bool 查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("createUser", Objects.requireNonNull(StpLoginUserUtil.getLoginUser()).getId()))
                .should(QueryBuilders.matchQuery("data", esSearchParam.getSearchKey()))
                .should(QueryBuilders.matchQuery("name", esSearchParam.getSearchKey()))
                .should(QueryBuilders.matchQuery("createUserName",esSearchParam.getSearchKey()))
                .should(QueryBuilders.matchQuery("attachment.content", esSearchParam.getSearchKey())); // 添加匹配 attachment.content 字段的条件

        // 构建查询
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withHighlightFields(
                        new HighlightBuilder.Field("name"),
                        new HighlightBuilder.Field("attachment.content"),
                        new HighlightBuilder.Field("data"),
                        new HighlightBuilder.Field("createUserName")
                )
                .withHighlightBuilder(new HighlightBuilder().preTags("<span style='color:red'>").postTags("</span>").requireFieldMatch(false))
                .withPageable(PageRequest.of(
                        ObjectUtil.isNull(esSearchParam.getCurrent()) ? 0 : esSearchParam.getCurrent(),
                        ObjectUtil.isNull(esSearchParam.getSize()) ? 10 : esSearchParam.getSize()
                ))
                .build();

        // 执行查询
        SearchHits<FileData> searchHits = elasticsearchRestTemplate.search(searchQuery, FileData.class);

        // 处理高亮结果
        List<FileData> resultList = searchHits.getSearchHits().stream().map(hit -> {
            FileData fileData = hit.getContent();
            Map<String, List<String>> highlightFields = hit.getHighlightFields();

            String highlightName = String.join("", highlightFields.getOrDefault("name", Collections.singletonList(fileData.getName())));
            fileData.setName(highlightName);

            String highlightData = String.join("", highlightFields.getOrDefault("attachment.content", Collections.singletonList(fileData.getData())));
            fileData.setData(highlightData);

            String highlightUserName= String.join("", highlightFields.getOrDefault("createUserName", Collections.singletonList(fileData.getData())));
            fileData.setCreateUserName(highlightUserName);

            return fileData;
        }).collect(Collectors.toList());

        // 构建分页结果
        Page<FileData> page = new Page<>(esSearchParam.getCurrent(), esSearchParam.getSize(), searchHits.getTotalHits());
        page.setRecords(resultList);
        return page;
    }

    @Override
    public List<String> getAssociationalWordOther(EsSearchParam esSearchParam) {
        // 构建查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchBoolPrefixQuery("name", esSearchParam.getSearchKey()));

        // 构建高亮查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withHighlightFields(new HighlightBuilder.Field("name"))
                .withHighlightBuilder(new HighlightBuilder().preTags("<span style='color:red'>").postTags("</span>"))
                .build();

        // 执行查询
        SearchHits<FileData> searchHits = elasticsearchRestTemplate.search(searchQuery, FileData.class);

        // 提取高亮结果并去重

        return searchHits.stream()
                .map(hit -> {
                    Map<String, List<String>> highlightFields = hit.getHighlightFields();
                    return String.join("", highlightFields.getOrDefault("name", Collections.singletonList(hit.getContent().getName())));
                })
                .distinct()
                .limit(9)
                .collect(Collectors.toList());
    }
}
