package vip.xiaonuo.pan.modular.fileCenter.operation.write;


import vip.xiaonuo.pan.modular.fileCenter.operation.write.domain.WriteFile;

import java.io.InputStream;

public abstract class Writer {
    public abstract void write(InputStream inputStream, WriteFile writeFile);
}
