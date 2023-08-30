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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitPlan;
import vip.xiaonuo.biz.modular.recruit.enums.BizRecruitResumeEnum;
import vip.xiaonuo.biz.modular.recruit.enums.BizRecruitResumeStageEnum;
import vip.xiaonuo.biz.modular.recruit.param.*;
import vip.xiaonuo.biz.modular.recruit.param.dto.*;
import vip.xiaonuo.biz.modular.recruit.service.BizRecruitPlanService;
import vip.xiaonuo.biz.modular.recruit.service.BizResumePerserService;
import vip.xiaonuo.biz.modular.recruit.utils.ResumeParserUtil;
import vip.xiaonuo.biz.modular.recruit.vo.*;
import vip.xiaonuo.biz.modular.task.service.SysOrgService;
import vip.xiaonuo.biz.modular.task.vo.BizSysOrgVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitResume;
import vip.xiaonuo.biz.modular.recruit.mapper.BizRecruitResumeMapper;
import vip.xiaonuo.biz.modular.recruit.service.BizRecruitResumeService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Resource
    BizRecruitResumeMapper bizRecruitResumeMapper;

    @Resource
    BizRecruitPlanService bizRecruitPlanService;

    @Resource
    SysOrgService sysOrgService;

    @Override
    public IPage<BizRecruitResumeVo> page(BizRecruitResumePageParam bizRecruitResumePageParam) {
        QueryWrapper<BizRecruitResume> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getType())) {
            queryWrapper.eq("a.TYPE", bizRecruitResumePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getResumeResource())) {
            queryWrapper.eq("a.RESUME_RESOURCE", bizRecruitResumePageParam.getResumeResource());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getName())) {
            queryWrapper.like("a.NAME", bizRecruitResumePageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getGender())) {
            queryWrapper.eq("a.GENDER", bizRecruitResumePageParam.getGender());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getIdCard())) {
            queryWrapper.like("a.ID_CARD", bizRecruitResumePageParam.getIdCard());
        }
        if(ObjectUtil.isNotEmpty(bizRecruitResumePageParam.getCollege())) {
            queryWrapper.like("a.COLLEGE", bizRecruitResumePageParam.getCollege());
        }
        if(ObjectUtil.isAllNotEmpty(bizRecruitResumePageParam.getSortField(), bizRecruitResumePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizRecruitResumePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizRecruitResumePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizRecruitResumePageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return bizRecruitResumeMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
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
        recruitResume.setResumeResource(resumeAddDto.getResumeResource());
        recruitResume.setPlanId(resumeAddDto.getPlanId());
        recruitResume.setStatus(BizRecruitResumeEnum.SEND.getValue());
        recruitResume = parser(recruitResume);
        //判断同职位，同个手机号是否已投递
        if(ObjectUtil.isNotEmpty(recruitResume.getPlanId()) && ObjectUtil.isNotEmpty(recruitResume.getPhone())) {
            List<BizRecruitResume> oldList = this.list(new QueryWrapper<BizRecruitResume>().lambda().eq(BizRecruitResume::getPlanId, recruitResume.getPlanId()).eq(BizRecruitResume::getPhone, recruitResume.getPhone()));
            if(CollUtil.isNotEmpty(oldList)){
                throw new CommonException("当前手机号的简历已投递过该岗位");
            }
        }
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
                    recruitResume.setContJobExp(result.getStr("cont_job_exp "));
                    recruitResume.setContCertificate(result.getStr("cont_certificate"));
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

    @Override
    public void update(ResumeUpdateDto resumeUpdateDto) {
        BizRecruitResume recruitResume = this.queryEntity(resumeUpdateDto.getId());
        String colName = resumeUpdateDto.getColName().toLowerCase();
        //状态
        if("status".equals(colName)){
            recruitResume.setStatus(resumeUpdateDto.getColValue());
        }
        //标签
        if("tags".equals(colName)){
            recruitResume.setTags(resumeUpdateDto.getColValue());
        }
        this.updateById(recruitResume);
    }

    @Override
    public ResumeChannelTotalVo channelTotal(ResumeChannelTotalDto resumeChannelTotalDto) {
        //分类统计
        ResumeChannelTotalVo resultVo = new ResumeChannelTotalVo();
        //条件
        QueryWrapper<BizRecruitResume> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BizRecruitResume::getDeleteFlag,"NOT_DELETE");
        if(ObjectUtil.isNotEmpty(resumeChannelTotalDto.getStartDate()) && ObjectUtil.isNotEmpty(resumeChannelTotalDto.getEndDate())) {
            queryWrapper.lambda().between(BizRecruitResume::getCreateTime, resumeChannelTotalDto.getStartDate(), resumeChannelTotalDto.getEndDate());
        }
        List<BizRecruitResume> resumeList = this.list(queryWrapper);
        Map<String,List<BizRecruitResume>> groupList = resumeList.stream().collect(Collectors.groupingBy(x->x.getResumeResource()));

        //1. 各个渠道各个状态简历统计表
        List<ResumeChannelNumVo> channelNumVoList = new ArrayList<>();
        for (Map.Entry<String,List<BizRecruitResume>> item:groupList.entrySet()) {
            ResumeChannelNumVo channelNumVo = new ResumeChannelNumVo();
            //各个状态统计
            channelNumVo.setResumeResource(item.getKey());
            long touDiNum = item.getValue().size();
            long youxiaoNum = item.getValue().stream().filter(x-> collStatus("youxiaoNum").contains(x.getStatus())
                            || (x.getStatus().equals(BizRecruitResumeEnum.STOP.getValue()) && collStatusStage("youxiaoNum").contains(x.getStatusStage()))).count();
            long faceNum = item.getValue().stream().filter(x-> collStatus("faceNum").contains(x.getStatus())
                            || (x.getStatus().equals(BizRecruitResumeEnum.STOP.getValue()) && collStatusStage("faceNum").contains(x.getStatusStage()))).count();
            long firstPassNum = item.getValue().stream().filter(x-> collStatus("firstPassNum").contains(x.getStatus())
                            || (x.getStatus().equals(BizRecruitResumeEnum.STOP.getValue()) && collStatusStage("firstPassNum").contains(x.getStatusStage()))).count();
            long facePassNum = item.getValue().stream().filter(x-> collStatus("facePassNum").contains(x.getStatus())
                            || (x.getStatus().equals(BizRecruitResumeEnum.STOP.getValue()) && collStatusStage("facePassNum").contains(x.getStatusStage()))).count();
            long tryNum = item.getValue().stream().filter(x->x.getStatus().equals(BizRecruitResumeEnum.ARRIVE.getValue())).count();
            channelNumVo.setTouDiNum(touDiNum);
            channelNumVo.setYouXiaoNum(youxiaoNum);
            channelNumVo.setFaceNum(faceNum);
            channelNumVo.setFirstPassNum(firstPassNum);
            channelNumVo.setFacePassNum(facePassNum);
            channelNumVo.setTryNum(tryNum);
            channelNumVoList.add(channelNumVo);
        }

        //2. 汇总
        ResumeChannelNumVo channelNumVo_HZ = new ResumeChannelNumVo();
        if(CollUtil.isNotEmpty(channelNumVoList)) {
            channelNumVo_HZ.setResumeResource("汇总");
            channelNumVo_HZ.setTouDiNum(channelNumVoList.stream().mapToLong(x -> x.getTouDiNum()).sum());
            channelNumVo_HZ.setYouXiaoNum(channelNumVoList.stream().mapToLong(x -> x.getYouXiaoNum()).sum());
            channelNumVo_HZ.setFaceNum(channelNumVoList.stream().mapToLong(x -> x.getFaceNum()).sum());
            channelNumVo_HZ.setFirstPassNum(channelNumVoList.stream().mapToLong(x -> x.getFirstPassNum()).sum());
            channelNumVo_HZ.setFacePassNum(channelNumVoList.stream().mapToLong(x -> x.getFacePassNum()).sum());
            channelNumVo_HZ.setTryNum(channelNumVoList.stream().mapToLong(x -> x.getTryNum()).sum());
        }

        List<ResumeChannelNumVo> temp = new ArrayList<>();
        temp.addAll(channelNumVoList);
        temp.add(channelNumVo_HZ);
        resultVo.setChannelNumVoList(temp);

        //3. 漏斗-汇总
        List<ResumeChannel_LD_HZ> ld_HZ = new ArrayList<>();
        ld_HZ.add(new ResumeChannel_LD_HZ("简历投递量",channelNumVo_HZ.getTouDiNum()));
        ld_HZ.add(new ResumeChannel_LD_HZ("有效简历量",channelNumVo_HZ.getYouXiaoNum()));
        ld_HZ.add(new ResumeChannel_LD_HZ("视频/面试人数",channelNumVo_HZ.getFaceNum()));
        ld_HZ.add(new ResumeChannel_LD_HZ("初试通过数",channelNumVo_HZ.getFirstPassNum()));
        ld_HZ.add(new ResumeChannel_LD_HZ("面试通过人数",channelNumVo_HZ.getFacePassNum()));
        ld_HZ.add(new ResumeChannel_LD_HZ("试用人数",channelNumVo_HZ.getTryNum()));
        resultVo.setLd_HZ(ld_HZ);

        //4. 漏斗-转化率
        List<ResumeChannel_LD_ZHL> ld_ZHL = new ArrayList<>();
        ld_ZHL.add(new ResumeChannel_LD_ZHL("简历投递量",1D));
        ld_ZHL.add(new ResumeChannel_LD_ZHL("有效简历量",division(channelNumVo_HZ.getYouXiaoNum(),channelNumVo_HZ.getTouDiNum())));
        ld_ZHL.add(new ResumeChannel_LD_ZHL("视频/面试人数",division(channelNumVo_HZ.getFaceNum(),channelNumVo_HZ.getYouXiaoNum())));
        ld_ZHL.add(new ResumeChannel_LD_ZHL("初试通过数",division(channelNumVo_HZ.getFirstPassNum(),channelNumVo_HZ.getFaceNum())));
        ld_ZHL.add(new ResumeChannel_LD_ZHL("面试通过人数",division(channelNumVo_HZ.getFacePassNum(),channelNumVo_HZ.getFirstPassNum())));
        ld_ZHL.add(new ResumeChannel_LD_ZHL("试用人数",division(channelNumVo_HZ.getTryNum(),channelNumVo_HZ.getFacePassNum())));
        resultVo.setLd_ZHL(ld_ZHL);

        //5. 饼图-渠道占比
        List<ResumeChannel_BT> bt_Channel = new ArrayList<>();
        for (ResumeChannelNumVo item:channelNumVoList) {
            bt_Channel.add(new ResumeChannel_BT(item.getResumeResource(),division(item.getTouDiNum(),channelNumVo_HZ.getTouDiNum())));
        }
        resultVo.setBT_Channel(bt_Channel);

        //6. 柱状图-有效简历占比
        ResumeChannel_ZZT zzt_YX = new ResumeChannel_ZZT();
        List<String> xAxis = new ArrayList<>();
        List<Double> yAxis = new ArrayList<>();
        for (ResumeChannelNumVo item:channelNumVoList) {
            xAxis.add(item.getResumeResource());
            yAxis.add(division(item.getYouXiaoNum(),channelNumVo_HZ.getYouXiaoNum()));
        }
        zzt_YX.setXAxis(xAxis);
        zzt_YX.setYAxis(yAxis);
        resultVo.setZzt_YX(zzt_YX);

        //7. 饼图-面试人数占比
        List<ResumeChannel_BT> bt_face = new ArrayList<>();
        for (ResumeChannelNumVo item:channelNumVoList) {
            bt_face.add(new ResumeChannel_BT(item.getResumeResource(),division(item.getFaceNum(),channelNumVo_HZ.getFaceNum())));
        }
        resultVo.setBt_face(bt_face);

        //8. 柱状图-使用人数占比
        ResumeChannel_ZZT zzt_try = new ResumeChannel_ZZT();
        List<String> xAxisTry = new ArrayList<>();
        List<Double> yAxisTry = new ArrayList<>();
        for (ResumeChannelNumVo item:channelNumVoList) {
            xAxisTry.add(item.getResumeResource());
            yAxisTry.add(division(item.getTryNum(),channelNumVo_HZ.getTryNum()));
        }
        zzt_try.setXAxis(xAxisTry);
        zzt_try.setYAxis(yAxisTry);
        resultVo.setZzt_try(zzt_try);

        //9. 柱状图 招聘未达成-原因
        ResumeChannel_ZZT zzt_refuse = new ResumeChannel_ZZT();
        List<String> xAxisRefuse = new ArrayList<>();
        List<Double> yAxisRefuse = new ArrayList<>();
        List<Long> zAxisRefuse = new ArrayList<>();
        List<BizRecruitResume> refuseList = resumeList.stream().filter(x->x.getStatus().equals(BizRecruitResumeEnum.REFUSE.getValue())).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(refuseList)){
            List<String> reasons = refuseList.stream().map(x->x.getReason()).distinct().collect(Collectors.toList());
            for (String item:reasons) {
                xAxisRefuse.add(item);
                long nums = refuseList.stream().filter(x->x.getReason().equals(item)).collect(Collectors.toList()).size();
                zAxisRefuse.add(nums);
                yAxisRefuse.add(division(nums,(long)refuseList.size()));
            }
            zzt_refuse.setXAxis(xAxisRefuse);
            zzt_refuse.setYAxis(yAxisRefuse);
            zzt_refuse.setZAxis(zAxisRefuse);
        }
        resultVo.setZzt_refuse(zzt_refuse);

        //10. 柱状图 试用
        ResumeChannel_ZZT zzt_arrive = new ResumeChannel_ZZT();
        List<String> xAxisArrive = new ArrayList<>();
        List<Long> zAxisArrive = new ArrayList<>();
        List<BizRecruitResume> arriveList = resumeList.stream().filter(x->x.getStatus().equals(BizRecruitResumeEnum.ARRIVE.getValue())).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(arriveList)){
            List<String> resources = arriveList.stream().map(x->x.getResumeResource()).distinct().collect(Collectors.toList());
            for(String item : resources){
                xAxisArrive.add(item);
                zAxisArrive.add(arriveList.stream().filter(x->x.getResumeResource().equals(item)).count());
            }
            zzt_arrive.setXAxis(xAxisArrive);
            zzt_arrive.setZAxis(zAxisArrive);
        }
        resultVo.setZzt_resource(zzt_arrive);

        //11. 柱状图-部门
        ResumeChannel_ZZT zzt_dept = new ResumeChannel_ZZT();
        List<String> xAxisDept = new ArrayList<>();
        List<Double> yAxisDept = new ArrayList<>();
        List<Long> zAxisDept = new ArrayList<>();
        List<Long> tAxisDept = new ArrayList<>();
        String year = StrUtil.toString(DateTime.parse(resumeChannelTotalDto.getStartDate()).getYear());
        List<BizRecruitPlan> planList = bizRecruitPlanService.list(new QueryWrapper<BizRecruitPlan>().lambda().eq(BizRecruitPlan::getYear,year).eq(BizRecruitPlan::getStatus,"1"));
        List<String> orgIds = planList.stream().map(x->x.getOrgId()).distinct().collect(Collectors.toList());
        for (String orgId:orgIds) {
            List<BizRecruitPlan> tmpplans = planList.stream().filter(x->x.getOrgId().equals(orgId)).collect(Collectors.toList());
            BizSysOrgVo orgVo = sysOrgService.getOrgById(orgId);
            List<BizRecruitResume> tmpresumes = this.list(new QueryWrapper<BizRecruitResume>().lambda().in(BizRecruitResume::getPlanId,tmpplans.stream().map(x->x.getId()).collect(Collectors.toList())).eq(BizRecruitResume::getStatus,BizRecruitResumeEnum.ARRIVE.getValue()));
            xAxisDept.add(orgVo.getOrgName());
            zAxisDept.add((long)tmpplans.size());
            tAxisDept.add((long)tmpresumes.size());
            yAxisDept.add(division((long)tmpresumes.size(),(long)tmpplans.size()));
        }
        zzt_dept.setXAxis(xAxisDept);
        zzt_dept.setYAxis(yAxisDept);
        zzt_dept.setZAxis(zAxisDept);
        zzt_dept.setTAxis(tAxisDept);
        resultVo.setZzt_dept(zzt_dept);
        return resultVo;
    }

    @Override
    public void refuse(ResumeRefuseDto resumeRefuseDto) {
        BizRecruitResume bizRecruitResume = this.queryEntity(resumeRefuseDto.getId());
        bizRecruitResume.setStatus(BizRecruitResumeEnum.REFUSE.getValue());
        bizRecruitResume.setStatusStage(BizRecruitResumeStageEnum.PEDING_STAGE.getValue());
        bizRecruitResume.setReason(resumeRefuseDto.getReason());
        this.updateById(bizRecruitResume);
    }


    /* 计算-除法 */
    public double division(Long x, Long y){
        if(ObjectUtil.isEmpty(x) || ObjectUtil.isEmpty(y)){
            return 0;
        }
        if(y == 0){
            return 0;
        }
        return (double) x/y;
    }

    /* 简历状态集 */
    public List<String> collStatus(String type){
        List<String> statusList = new ArrayList<>();
//        statusList.add(BizRecruitResumeEnum.SEND.getValue());
//        statusList.add(BizRecruitResumeEnum.STOP.getValue());
        statusList.add(BizRecruitResumeEnum.PRE_INTERVIEW.getValue());
        statusList.add(BizRecruitResumeEnum.FIRST_PASS.getValue());
        statusList.add(BizRecruitResumeEnum.RETEST_PASS.getValue());
        statusList.add(BizRecruitResumeEnum.PENDING.getValue());
        statusList.add(BizRecruitResumeEnum.ARRIVE.getValue());
        statusList.add(BizRecruitResumeEnum.REFUSE.getValue());
        if("youxiaoNum".equals(type)){
            return statusList;
        }
        if("faceNum".equals(type)){
            statusList.remove(BizRecruitResumeEnum.PRE_INTERVIEW.getValue());
            return statusList;
        }
        if("firstPassNum".equals(type)){
            statusList.remove(BizRecruitResumeEnum.PRE_INTERVIEW.getValue());
            return statusList;
        }
        if("facePassNum".equals(type)){
            statusList.remove(BizRecruitResumeEnum.PRE_INTERVIEW.getValue());
            statusList.remove(BizRecruitResumeEnum.FIRST_PASS.getValue());
            return statusList;
        }
        return statusList;
    }

    /* 简历状态-阶段集 */
    public List<String> collStatusStage(String type){
        List<String> statusStageList = new ArrayList<>();
        //statusStageList.add(BizRecruitResumeStageEnum.FITER_STAGE.getValue());
        statusStageList.add(BizRecruitResumeStageEnum.FIRST_STAGE.getValue());
        statusStageList.add(BizRecruitResumeStageEnum.RETEST_STAGE.getValue());
        statusStageList.add(BizRecruitResumeStageEnum.PEDING_STAGE.getValue());
        if("youxiaoNum".equals(type)){
            return statusStageList;
        }
        if("faceNum".equals(type)){
            return statusStageList;
        }
        if("firstPassNum".equals(type)){
            statusStageList.remove(BizRecruitResumeStageEnum.FIRST_STAGE.getValue());
            return statusStageList;
        }
        if("facePassNum".equals(type)){
            statusStageList.remove(BizRecruitResumeStageEnum.FIRST_STAGE.getValue());
            statusStageList.remove(BizRecruitResumeStageEnum.RETEST_STAGE.getValue());
            return statusStageList;
        }
        return statusStageList;
    }

}
