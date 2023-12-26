package vip.xiaonuo.dev.modular.mxDraw.service;

import vip.xiaonuo.dev.modular.mxDraw.param.CadToParam;
import vip.xiaonuo.dev.modular.mxDraw.param.ConvertData;

public interface MxDrawService {

    /**
     * DWG文件转换编辑格式mxweb
     * @param convertData
     * @return
     */
    String convert(ConvertData convertData);

    /**
     * DWG文件转成PDF
     * @param cadToPdfParam
     * @return
     */
    String cadToPdf(CadToParam cadToPdfParam);

    /**
     * DWG文件转成JPG
     * @param cadToPdfParam
     * @return
     */
    String cadToJpg(CadToParam cadToPdfParam);

    /**
     * DWG文件转换浏览格式wgh
     * @param cadToParam
     * @return
     */
    String convertWgh(CadToParam cadToParam);
}
