package vip.xiaonuo.pan.modular.fileCenter.operation.preview.product;

import vip.xiaonuo.pan.modular.fileCenter.entity.ThumbImage;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.Previewer;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.domain.PreviewFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LocalStoragePreviewer extends Previewer {

    public LocalStoragePreviewer(){

    }
    public LocalStoragePreviewer(ThumbImage thumbImage) {
        setThumbImage(thumbImage);
    }

    @Override
    protected InputStream getInputStream(PreviewFile previewFile) {
        //设置文件路径
        File file = FileCenterUtil.getLocalSaveFile(previewFile.getFileUrl());
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return inputStream;

    }

}
