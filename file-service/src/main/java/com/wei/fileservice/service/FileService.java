package com.wei.fileservice.service;

import entity.DirDesc;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {


    String saveFile(MultipartFile uploadFile, String path) ;

    boolean isFileExist(String path);

    File getFile(String path);

    void deleteFileByIp(String ip);

    List<DirDesc> list();

    boolean deleteFileByPath(String s);
}
