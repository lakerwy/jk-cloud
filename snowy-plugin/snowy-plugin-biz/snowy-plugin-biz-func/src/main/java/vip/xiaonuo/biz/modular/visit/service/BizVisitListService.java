package vip.xiaonuo.biz.modular.visit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.visit.entity.BizVisitList;
import vip.xiaonuo.biz.modular.visit.param.*;

import java.util.List;

public interface BizVisitListService extends IService<BizVisitList> {

    Page<BizVisitList> page(BizVisitListPageParam bizVisitListPageParam);

    void add(BizVisitListAddParam bizVisitListAddParam);


    void edit(BizVisitListEditParam bizVisitListEditParam);


    void delete(List<BizVisitListIdParam> bizVisitListIdParam);

    BizVisitList detail(BizVisitListIdParam bizVisitListIdParam);


    BizVisitList queryEntity(String id);
}
