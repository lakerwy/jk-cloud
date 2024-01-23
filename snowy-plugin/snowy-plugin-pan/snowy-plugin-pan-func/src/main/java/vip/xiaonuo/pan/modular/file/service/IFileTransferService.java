package vip.xiaonuo.pan.modular.file.service;

import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.param.DownloadFileParam;
import vip.xiaonuo.pan.modular.file.param.PreviewParam;
import vip.xiaonuo.pan.modular.file.param.UploadFileParam;
import vip.xiaonuo.pan.modular.file.vo.UploadFileVo;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/11 9:12
 */
public interface IFileTransferService {
    UploadFileVo uploadFileSpeed(UploadFileParam uploadFileParam);

    List<UserFile> uploadFile(HttpServletRequest request, UploadFileParam uploadFileParam);

    void downloadFile(HttpServletResponse httpServletResponse, List<String> fileIds,String rightType);
    void downloadUserFileList(HttpServletResponse httpServletResponse, String filePath, String fileName, List<String> userFileIds,String rightType);
    void previewFile(HttpServletResponse httpServletResponse, PreviewParam previewParam);
    void previewPictureFile(HttpServletResponse httpServletResponse, PreviewParam previewParam);
    void deleteFile(PanFile panFile);

    Long selectStorageSizeByUserId();
}
