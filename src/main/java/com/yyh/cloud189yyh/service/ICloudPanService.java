package com.yyh.cloud189yyh.service;

import com.yyh.cloud189yyh.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 云盘 service
 */
public interface ICloudPanService {

    String authorize();

    String accessToken() throws Exception;

    User userInfo() throws Exception;

    Map<String, List> listFiles(Long folderId) throws Exception;

    Object searchFiles(long folderId,String filename);

    String getFileDownloadUrl(long fileId) throws Exception;

    Object createFolder(long parentFolderId,String folderName);

    Object renameFile(long fileId,String destFileName);

    Object deleteFolder(long fileId);

    void setToken(String token);
}
