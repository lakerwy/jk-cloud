package vip.xiaonuo.pan.modular.fileCenter.operation.write.product;

import lombok.extern.slf4j.Slf4j;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.pan.modular.fileCenter.operation.write.Writer;
import vip.xiaonuo.pan.modular.fileCenter.operation.write.domain.WriteFile;
import vip.xiaonuo.pan.modular.fileCenter.util.UFOPUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class LocalStorageWriter extends Writer {
    @Override
    public void write(InputStream inputStream, WriteFile writeFile) {
        try (FileOutputStream out = new FileOutputStream(UFOPUtils.getStaticPath() + writeFile.getFileUrl())){
            int read;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            throw new CommonException("待写入的文件不存在:{}", e);
        } catch (IOException e) {
            throw new CommonException("IO异常:{}", e);
        }
    }
}
