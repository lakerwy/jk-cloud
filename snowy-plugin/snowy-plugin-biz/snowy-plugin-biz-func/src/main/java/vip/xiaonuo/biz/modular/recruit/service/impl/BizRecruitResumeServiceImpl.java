/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.recruit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.recruit.param.*;
import vip.xiaonuo.biz.modular.recruit.param.dto.ResumeAddDto;
import vip.xiaonuo.biz.modular.recruit.param.dto.ResumeParserDto;
import vip.xiaonuo.biz.modular.recruit.param.dto.ResumeRegistSaveDto;
import vip.xiaonuo.biz.modular.recruit.service.BizResumePerserService;
import vip.xiaonuo.biz.modular.recruit.utils.ResumeParserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitResume;
import vip.xiaonuo.biz.modular.recruit.mapper.BizRecruitResumeMapper;
import vip.xiaonuo.biz.modular.recruit.service.BizRecruitResumeService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-招聘-简历库Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/23 17:08
 **/
@DS("slave_1")
@Service
public class BizRecruitResumeServiceImpl extends ServiceImpl<BizRecruitResumeMapper, BizRecruitResume> implements BizRecruitResumeService {
    @Resource
    BizResumePerserService bizResumePerserService;

    @Override
    public Page<BizRecruitResume> page(BizRecruitResumePageParam bizRecruitResumePageParam) {
        QueryWrapper<BizRecruitResume> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getType())) {
            queryWrapper.lambda().eq(BizRecruitResume::getType, bizRecruitResumePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getResumeResource())) {
            queryWrapper.lambda().eq(BizRecruitResume::getResumeResource, bizRecruitResumePageParam.getResumeResource());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getName())) {
            queryWrapper.lambda().like(BizRecruitResume::getName, bizRecruitResumePageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getGender())) {
            queryWrapper.lambda().eq(BizRecruitResume::getGender, bizRecruitResumePageParam.getGender());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getIdCard())) {
            queryWrapper.lambda().like(BizRecruitResume::getIdCard, bizRecruitResumePageParam.getIdCard());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getCollege())) {
            queryWrapper.lambda().like(BizRecruitResume::getCollege, bizRecruitResumePageParam.getCollege());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getCollegeType())) {
            queryWrapper.lambda().eq(BizRecruitResume::getCollegeType, bizRecruitResumePageParam.getCollegeType());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getMajor())) {
            queryWrapper.lambda().like(BizRecruitResume::getMajor, bizRecruitResumePageParam.getMajor());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getDegree())) {
            queryWrapper.lambda().eq(BizRecruitResume::getDegree, bizRecruitResumePageParam.getDegree());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getRecruit())) {
            queryWrapper.lambda().eq(BizRecruitResume::getRecruit, bizRecruitResumePageParam.getRecruit());
        }
        if(ObjectUtil.isAllNotEmpty(bizRecruitResumePageParam.getSortField(), bizRecruitResumePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizRecruitResumePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizRecruitResumePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizRecruitResumePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizRecruitResume::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizRecruitResumeAddParam bizRecruitResumeAddParam) {
        BizRecruitResume bizRecruitResume = BeanUtil.toBean(bizRecruitResumeAddParam, BizRecruitResume.class);
        this.save(bizRecruitResume);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizRecruitResumeEditParam bizRecruitResumeEditParam) {
        BizRecruitResume bizRecruitResume = this.queryEntity(bizRecruitResumeEditParam.getId());
        BeanUtil.copyProperties(bizRecruitResumeEditParam, bizRecruitResume);
        this.updateById(bizRecruitResume);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizRecruitResumeIdParam> bizRecruitResumeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizRecruitResumeIdParamList, BizRecruitResumeIdParam::getId));
    }

    @Override
    public BizRecruitResume detail(BizRecruitResumeIdParam bizRecruitResumeIdParam) {
        return this.queryEntity(bizRecruitResumeIdParam.getId());
    }

    @Override
    public BizRecruitResume queryEntity(String id) {
        BizRecruitResume bizRecruitResume = this.getById(id);
        if(ObjectUtil.isEmpty(bizRecruitResume)) {
            throw new CommonException("业务表-招聘-简历库不存在，id值为：{}", id);
        }
        return bizRecruitResume;
    }

    @Override
    public void addAuto(ResumeAddDto resumeAddDto) {
        BizRecruitResume recruitResume = new BizRecruitResume();
        recruitResume.setType("社招");
        recruitResume.setResumeName(resumeAddDto.getResumeName());
        recruitResume.setResumeUrl(resumeAddDto.getResumeUrl());
        recruitResume.setResumeAttach(resumeAddDto.getResumeAttach());
        recruitResume.setResumeResource(recruitResume.getResumeResource());
        recruitResume.setPlanId(resumeAddDto.getPlanId());
        recruitResume = parser(recruitResume);
        this.save(recruitResume);
    }

    /**
     * 简历解析方法
     * @param recruitResume 简历实体
     * @return
     */
    public BizRecruitResume parser(BizRecruitResume recruitResume){
        try {
            if(StrUtil.isEmpty(recruitResume.getResumeUrl())) return recruitResume;
            //解析JSONObject resumeInfo = JSONUtil.parseObj("{\"status\":{\"message\":\"success\",\"code\":200},\"result\":{\"cont_job_skill\":\"个技能:\\n1、掌握软件测试规范流程,并掌握各种测试档的编写,包括测试计划、测试需求、测试例、缺陷报\\n告、测试总结;\\n2、能熟练搭建并使缺陷管理具禅道;\\n3、熟悉Linux操作下基本命令的操作,能使VI编辑器,软件的解压、安装、进程服务管理;\\n4、熟悉数据库mysql,能熟悉运mysql创建数据库及数据表,对数据进增删查改;\\n5、熟悉机app业务功能测试,熟练使monkey结合adb命令对app进动化测试;\\n6、熟悉掌握Charles对web端、机端进http协议和https协议抓包分析,并开展接测试;\\n7、能熟练使jmeter做接测试、性能测试;\\n8、熟练python+selenium+unittest+htmltestrunner进UI动化测试框架的实现,能解决常的问题,如\\n窗切换、定位动态元素等;\\n9、了解Jenkins持续集成具, 如配置项、构建、设置邮箱发送报告;\\n10、参与性能测试流程, 有性能测试经验;\",\"surname\":\"彭\",\"training_objs\":[],\"social_exp_objs\":[],\"college\":\"中南林业科技学\",\"english_level\":\"大学英语6级\",\"all_cert_objs\":[{\"cert_name\":\"大学英语4级\"},{\"cert_name\":\"大学英语6级\"}],\"resume_type\":\"2\",\"skills_objs\":[{\"skills_level\":\"掌握\",\"skills_name\":\"软件测试\"},{\"skills_level\":\"熟练\",\"skills_name\":\"禅道\"},{\"skills_level\":\"熟悉\",\"skills_name\":\"Linux\"},{\"skills_level\":\"熟悉\",\"skills_name\":\"编辑\"},{\"skills_level\":\"熟悉\",\"skills_name\":\"数据库\"},{\"skills_level\":\"熟悉\",\"skills_name\":\"mysql\"},{\"skills_level\":\"熟悉\",\"skills_name\":\"功能测试\"},{\"skills_level\":\"熟悉\",\"skills_name\":\"http协议\"},{\"skills_level\":\"熟练\",\"skills_name\":\"jmeter\"},{\"skills_level\":\"熟练\",\"skills_name\":\"性能测试\"},{\"skills_level\":\"熟练\",\"skills_name\":\"python\"},{\"skills_level\":\"熟练\",\"skills_name\":\"selenium\"},{\"skills_level\":\"熟练\",\"skills_name\":\"UI\"},{\"skills_level\":\"了解\",\"skills_name\":\"Jenkins\"}],\"education_objs\":[{\"edu_content\":\"作经历\",\"edu_college_dept\":\"涉外学院\",\"end_date\":\"2020.08\",\"edu_college\":\"中南林业科技学\",\"edu_college_type\":\"0\",\"start_date\":\"2016.09\"}],\"languages\":\"英语\",\"resume_integrity\":\"32\",\"cont_certificate\":\"英语四级证书、英语六级证书、计算机级\\n项经验\\n项: 简知\\n学历:本科\",\"email\":\"pr2451928306@163.com\",\"lang_objs\":[{\"language_name\":\"英语\"}],\"grad_time\":\"2020.08\",\"cert_objs\":[{\"langcert_lang\":\"英语\",\"langcert_name\":\"大学英语4级\"},{\"langcert_lang\":\"英语\",\"langcert_name\":\"大学英语6级\"}],\"proj_exp_objs\":[],\"work_year_norm\":\"0\",\"work_year\":\"0\",\"phone\":\"15580802521\",\"resume_parse_time\":\"2023-08-23 18:01:17\",\"work_year_inf\":\"0\",\"raw_text\":\"彭蓉\\n联系电话: 15580802521\\n电邮箱: pr2451928306@163.com\\n教育背景\\n2016.09-2020.08 中南林业科技学涉外学院\\n作经历\\n2020.03-2023.04 州简知信息科技有限公司\\n作内容:\\n1、参加项需求、测试例的评审;\\n2、编写测试例, 执例并跟踪测试结果;\\n3、提交 bug并跟踪 bug的整个命周期, 协助开发员进产品的改进和优化;\\n4、完成功能测试、接测试,以及提交Bug并跟踪和管理;\\n5、协助开发定位bug,以及进性能测试;\\n6、使python+selenium+unittest+htmltestrunner+Jenkins进UI动化测试;\\n证书技能\\n个技能:\\n1、掌握软件测试规范流程,并掌握各种测试档的编写,包括测试计划、测试需求、测试例、缺陷报\\n告、测试总结;\\n2、能熟练搭建并使缺陷管理具禅道;\\n3、熟悉Linux操作下基本命令的操作,能使VI编辑器,软件的解压、安装、进程服务管理;\\n4、熟悉数据库mysql,能熟悉运mysql创建数据库及数据表,对数据进增删查改;\\n5、熟悉机app业务功能测试,熟练使monkey结合adb命令对app进动化测试;\\n6、熟悉掌握Charles对web端、机端进http协议和https协议抓包分析,并开展接测试;\\n7、能熟练使jmeter做接测试、性能测试;\\n8、熟练python+selenium+unittest+htmltestrunner进UI动化测试框架的实现,能解决常的问题,如\\n窗切换、定位动态元素等;\\n9、了解Jenkins持续集成具, 如配置项、构建、设置邮箱发送报告;\\n10、参与性能测试流程, 有性能测试经验;\\n证书奖励: 英语四级证书、英语六级证书、计算机级\\n项经验\\n项: 简知\\n学历:本科\\n年龄: 23岁\\n1994019831895ca61Xd509W0F1dTypG4U_2cQuyklg~~\\n19940198\\n31\\n89\\n5c\\na6\\n1X\\nd5\\n09\\nW\\n0F\\n1d\\nT\\nyp\\nG\\n4U\\n_2\\ncQ\\nuy\\nkl\\ng~\\n~\\n项描述: 该软件是款在线学习平台, 是款APP, 主要为性户提供学习资源, 同时户\\n也可以通过视频、图书的式进学习, 该软件主要可分为、成墙、听书、我的已购、个中\\n等模块。\\n我的职责:\\n1、Android版本前端的业务测试、参与需求评审会议,整理测试需求;\\n2、根据产品需求编写测试例,并执测试例;\\n3、负责使Adb、Monkey命令对机APP进稳定性测试;\\n4、发现bug及时提交到项管理具禅道上,并对bug进管理和跟踪;\\n5、进安装卸载测试、兼容性测试、交叉事件测试﹔\\n6、Jmeter根据接档进接测试,利Charles抓包具分析bug进定位;\\n7、使adb命令抓取志;\\n8、参与项评审,协助运维完成产品的发布;\\n项: 简知\\n项描述: 简知,是简知科技旗下专为 3- 12岁孩提供多学科在线学习服务的智能化少素质教\\n育平台, 拥有写字课、阅读课和少轻课等多个学科的课程产品。\\n我的职责:\\n1、参与需求评审会议,参与编写测试计划和测试例;\\n2、对整个前端进功能测试、兼容性测试、界测试, 将发现的 bug提交到禅道上;\\n3、使 Linux命令监控志, 定位 bug;\\n4、使 python+selenium+unittest +htmltestrunner编写动化脚本, 进动化测试;\\n5、使 jmeter具根据开发给的接档进接测试;\\n6、编写测试总结报告;\\n项三: 财商课堂\\n项描述:简知财商课堂是家庭理财投资站式学习平台, 主要模块有课程精选、每听书、学员故事、热课程\\n、户管理等模块, 该软件易学、易、易维护; 该软件各功能点都提供有相应的帮助信息按钮, 为户操作使\\n提供指导。\\n我的职责:\\n1、参与需求分析和评审,以及制定测试计划;\\n2、编写测试例并执,参与测试例的评审;\\n3、使项管理具禅道对bug进管理和跟踪;\\n4、进兼容性测试、交叉事件测试;\\n5、根据开发提供的接档,使jmeter进接测试;\\n6、提交缺陷报告并写测试总结,参与测试总结评审;\\n项四: 简知书院\\n项描述: 简知书院是款以 c/s架构的在线听书学习平台,以型阅读学习营的式,开展 180\\n天读书计划。\\n我的职责:\\n1、主要负责Android版本的业务功能测试;\\n2、根据产品需求编写测试例,并执测试例;\\n3、发现bug及时提交到项管理具禅道上,并对bug进管理和跟踪;\\n4、版本更新后,根据软件功能的变化,进回归测试;\\n5、参与测试总结评审;\\n19940198\\n31\\n89\\n5c\\na6\\n1X\\nd5\\n09\\nW\\n0F\\n1d\\nT\\nyp\\nG\\n4U\\n_2\\ncQ\\nuy\\nkl\\ng~\\n~\",\"cont_basic_info\":\"彭蓉\\n联系电话: 15580802521\\n电邮箱: pr2451928306@163.com\\n年龄: 23岁\\n1994019831895ca61Xd509W0F1dTypG4U_2cQuyklg~~\\n19940198\\n31\\n89\\n5c\\na6\\n1X\\nd5\\n09\\nW\\n0F\\n1d\\nT\\nyp\\nG\\n4U\\n_2\\ncQ\\nuy\\nkl\\ng~\\n~\\n项描述: 该软件是款在线学习平台, 是款APP, 主要为性户提供学习资源, 同时户\\n也可以通过视频、图书的式进学习, 该软件主要可分为、成墙、听书、我的已购、个中\\n等模块。\\n我的职责:\\n1、Android版本前端的业务测试、参与需求评审会议,整理测试需求;\\n2、根据产品需求编写测试例,并执测试例;\\n3、负责使Adb、Monkey命令对机APP进稳定性测试;\\n4、发现bug及时提交到项管理具禅道上,并对bug进管理和跟踪;\\n5、进安装卸载测试、兼容性测试、交叉事件测试﹔\\n6、Jmeter根据接档进接测试,利Charles抓包具分析bug进定位;\\n7、使adb命令抓取志;\\n8、参与项评审,协助运维完成产品的发布;\\n项: 简知\\n项描述: 简知,是简知科技旗下专为 3- 12岁孩提供多学科在线学习服务的智能化少素质教\\n育平台, 拥有写字课、阅读课和少轻课等多个学科的课程产品。\\n我的职责:\\n1、参与需求评审会议,参与编写测试计划和测试例;\\n2、对整个前端进功能测试、兼容性测试、界测试, 将发现的 bug提交到禅道上;\\n3、使 Linux命令监控志, 定位 bug;\\n4、使 python+selenium+unittest +htmltestrunner编写动化脚本, 进动化测试;\\n5、使 jmeter具根据开发给的接档进接测试;\\n6、编写测试总结报告;\\n项三: 财商课堂\\n项描述:简知财商课堂是家庭理财投资站式学习平台, 主要模块有课程精选、每听书、学员故事、热课程\\n、户管理等模块, 该软件易学、易、易维护; 该软件各功能点都提供有相应的帮助信息按钮, 为户操作使\\n提供指导。\\n我的职责:\\n1、参与需求分析和评审,以及制定测试计划;\\n2、编写测试例并执,参与测试例的评审;\\n3、使项管理具禅道对bug进管理和跟踪;\\n4、进兼容性测试、交叉事件测试;\\n5、根据开发提供的接档,使jmeter进接测试;\\n6、提交缺陷报告并写测试总结,参与测试总结评审;\\n项四: 简知书院\\n项描述: 简知书院是款以 c/s架构的在线听书学习平台,以型阅读学习营的式,开展 180\\n天读书计划。\\n我的职责:\\n1、主要负责Android版本的业务功能测试;\\n2、根据产品需求编写测试例,并执测试例;\\n3、发现bug及时提交到项管理具禅道上,并对bug进管理和跟踪;\\n4、版本更新后,根据软件功能的变化,进回归测试;\\n5、参与测试总结评审;\",\"name\":\"彭蓉\",\"job_exp_objs\":[],\"age\":\"23\",\"resume_name\":\"cv20230823_180117489_280.\",\"cont_education\":\"2016.09-2020.08 中南林业科技学涉外学院\\n作经历\\n2020.03-2023.04 州简知信息科技有限公司\\n作内容:\\n1、参加项需求、测试例的评审;\\n2、编写测试例, 执例并跟踪测试结果;\\n3、提交 bug并跟踪 bug的整个命周期, 协助开发员进产品的改进和优化;\\n4、完成功能测试、接测试,以及提交Bug并跟踪和管理;\\n5、协助开发定位bug,以及进性能测试;\\n6、使python+selenium+unittest+htmltestrunner+Jenkins进UI动化测试;\",\"college_type\":\"0\"},\"eval\":{\"salary\":7300},\"tags\":{\"industries\":[],\"pos_types\":[],\"pos_tags\":[],\"skills_tags\":[{\"tag_weight\":0.6627367496490479,\"tag_name\":\"selenium\"},{\"tag_weight\":0.6451789855957032,\"tag_name\":\"jmeter\"},{\"tag_weight\":0.6363886833190918,\"tag_name\":\"monkey\"},{\"tag_weight\":0.6057627677917481,\"tag_name\":\"测试框架\"},{\"tag_weight\":0.5914662837982178,\"tag_name\":\"禅道\"},{\"tag_weight\":0.5902946949005127,\"tag_name\":\"性能测试\"},{\"tag_weight\":0.5856215476989747,\"tag_name\":\"adb命令\"},{\"tag_weight\":0.5677203178405762,\"tag_name\":\"缺陷管理\"},{\"tag_weight\":0.5097500324249268,\"tag_name\":\"功能测试\"},{\"tag_weight\":0.5058286190032959,\"tag_name\":\"jenkins持续集成\"},{\"tag_weight\":0.49932341575622563,\"tag_name\":\"python\"},{\"tag_weight\":0.4959739685058594,\"tag_name\":\"linux操作\"},{\"tag_weight\":0.4800576686859131,\"tag_name\":\"数据库mysql\"},{\"tag_weight\":0.4526265621185303,\"tag_name\":\"mysql\"},{\"tag_weight\":0.43610839843750004,\"tag_name\":\"charles\"},{\"tag_weight\":0.36684389114379884,\"tag_name\":\"web端\"},{\"tag_weight\":0.34056658744812013,\"tag_name\":\"https\"},{\"tag_weight\":0.31230127811431885,\"tag_name\":\"ui\"}]}}");
            JSONObject resumeInfo = ResumeParserUtil.resumeParser(recruitResume.getResumeName(),recruitResume.getResumeUrl());
            //处理简历解析(解析有返回值)
            if (ObjectUtil.isNotEmpty(resumeInfo)) {
                recruitResume.setResumeParserJson(JsonUtils.object2json(resumeInfo));
                //获取状态信息
                JSONObject status = resumeInfo.getJSONObject("status");
                String code = status.getStr("code");
                if (ObjectUtil.isNotEmpty(code) && code.equals("200")) {
                    //解析成功，获取结果
                    JSONObject result = resumeInfo.getJSONObject("result");
                    recruitResume.setName(result.getStr("name"));
                    recruitResume.setAge(result.getStr("age"));
                    recruitResume.setGender(result.getStr("gender"));
                    recruitResume.setMaritalStatus(result.getStr("marital_status"));
                    recruitResume.setBirthday(result.getStr("birthday"));
                    recruitResume.setIdCard(result.getStr("id_card"));
                    recruitResume.setRace(result.getStr("race"));
                    recruitResume.setNationality(result.getStr("nationality"));
                    recruitResume.setPolitStatus(result.getStr("polit_status"));
                    recruitResume.setApplyJob(result.getStr("apply_job"));
                    recruitResume.setWorkYear(result.getStr("work_year"));
                    recruitResume.setWorkStatus(result.getStr("work_status"));
                    recruitResume.setWorkSalary(result.getStr("work_salary"));
                    recruitResume.setWorkLocation(result.getStr("work_location"));
                    recruitResume.setGradTime(result.getStr("grad_time"));
                    recruitResume.setCollege(result.getStr("college"));
                    recruitResume.setCollegeType(result.getStr("college_type"));
                    recruitResume.setCollegeDept(result.getStr("college_dept"));
                    recruitResume.setMajor(result.getStr("major"));
                    recruitResume.setDegree(result.getStr("degree"));
                    recruitResume.setRecruit(result.getStr("recruit"));
                    recruitResume.setEmail(result.getStr("email"));
                    recruitResume.setPhone(result.getStr("phone"));
                    recruitResume.setExpectSalary(result.getStr("expect_salary"));
                    recruitResume.setExpectTime(result.getStr("expect_time"));
                    recruitResume.setResumeJson(result.toString());
                }
                //增加接口调用记录
                String message = status.getStr("message");
                BizResumePerserAddParam bizResumePerserAddParam = new BizResumePerserAddParam();
                bizResumePerserAddParam.setCode(code);
                bizResumePerserAddParam.setMessage(message);
                bizResumePerserAddParam.setExtJson(JsonUtils.object2json(resumeInfo));
                JSONObject account = resumeInfo.getJSONObject("account");
                if(ObjectUtil.isNotEmpty(account)){
                    bizResumePerserAddParam.setUid(account.getStr("uid"));
                    bizResumePerserAddParam.setUsageLimit(account.getStr("usage_limit"));
                    bizResumePerserAddParam.setUsageRemaining(account.getStr("usage_remaining"));
                }
                bizResumePerserService.add(bizResumePerserAddParam);
            }
            return recruitResume;
        }catch (Exception e){
            return recruitResume;
        }
    }


    @Override
    public void resumeParser(ResumeParserDto resumeParserDto) {
        BizRecruitResume recruitResume = this.queryEntity(resumeParserDto.getId());
        if(StrUtil.isEmpty(recruitResume.getResumeUrl())) {
            throw new CommonException("当前简历路径不存在");
        }
        recruitResume = parser(recruitResume);
        this.updateById(recruitResume);
    }

    @Override
    public void registerSave(ResumeRegistSaveDto resumeRegistSaveDto) {
        BizRecruitResume recruitResume = this.queryEntity(resumeRegistSaveDto.getId());
        recruitResume.setRegistJson(resumeRegistSaveDto.getRegistJson());
        this.updateById(recruitResume);
    }

    @Override
    public String registerDetail(BizRecruitResumeIdParam bizRecruitResumeIdParam) {
        BizRecruitResume recruitResume = this.queryEntity(bizRecruitResumeIdParam.getId());
        return recruitResume.getRegistJson();
    }

}
