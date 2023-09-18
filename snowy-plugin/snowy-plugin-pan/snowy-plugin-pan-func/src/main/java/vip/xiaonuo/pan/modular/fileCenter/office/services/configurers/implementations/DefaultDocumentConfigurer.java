/**
 *
 * (c) Copyright Ascensio System SIA 2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package vip.xiaonuo.pan.modular.fileCenter.office.services.configurers.implementations;

import vip.xiaonuo.pan.modular.fileCenter.office.documentserver.managers.document.DocumentManager;
import vip.xiaonuo.pan.modular.fileCenter.office.documentserver.models.filemodel.Document;
import vip.xiaonuo.pan.modular.fileCenter.office.documentserver.models.filemodel.Permission;
import vip.xiaonuo.pan.modular.fileCenter.office.documentserver.storage.FileStoragePathBuilder;
import vip.xiaonuo.pan.modular.fileCenter.office.documentserver.util.file.FileUtility;
import vip.xiaonuo.pan.modular.fileCenter.office.documentserver.util.service.ServiceConverter;
import vip.xiaonuo.pan.modular.fileCenter.office.services.configurers.DocumentConfigurer;
import vip.xiaonuo.pan.modular.fileCenter.office.services.configurers.wrappers.DefaultDocumentWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;

@Service
@Primary
public class DefaultDocumentConfigurer implements DocumentConfigurer<DefaultDocumentWrapper> {

    @Autowired
    private DocumentManager documentManager;

    @Autowired
    private FileStoragePathBuilder storagePathBuilder;

    @Autowired
    private FileUtility fileUtility;

    @Autowired
    private ServiceConverter serviceConverter;

    public void configure(Document document, DefaultDocumentWrapper wrapper){  // define the document configurer
        UserFile userFile = wrapper.getUserFile();

        String fileName = userFile.getFileName() + "." + userFile.getExtendName();  // get the fileName parameter from the document wrapper
        Permission permission = wrapper.getPermission();  // get the permission parameter from the document wrapper

        document.setTitle(fileName);  // set the title to the document config
        document.setUrl(wrapper.getPreviewUrl());  // set the URL to download a file to the document config
        document.setFileType(fileUtility.getFileExtension(fileName).replace(".",""));  // set the file type to the document config
        document.getInfo().setFavorite(wrapper.getFavorite());  // set the favorite parameter to the document config

        String key =  serviceConverter.  // get the document key
                        generateRevisionId(userFile.getId() + userFile.getCreateTime());

        document.setKey(key);  // set the key to the document config
        document.setPermissions(permission);  // set the permission parameters to the document config
    }
}
