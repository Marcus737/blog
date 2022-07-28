package com.wei.fileservice.service;

import entity.DirDesc;
import entity.FileDesc;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private String getSavePath() {
        return FileUtils.getUserDirectoryPath() + File.separator + "file_save_dir" + File.separator;
    }

    @Override
    public String saveFile(MultipartFile uploadFile, String path) {
        path = getSavePath() + path;
        try {
            File file = FileUtils.getFile(path);
            FileUtils.createParentDirectories(file);
            file.createNewFile();
            FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return "ok";
    }

    @Override
    public boolean isFileExist(String path) {
        path = getSavePath() + path;
        File file = FileUtils.getFile(path);
        return file.exists();
    }

    @Override
    public File getFile(String path) {
        if (!isFileExist(path)) {
            return null;
        }
        path = getSavePath() + path;
        return FileUtils.getFile(path);
    }

    @Override
    public void deleteFileByIp(String ip) {
        String path = getSavePath() + ip;
        File file = FileUtils.getFile(path);
        FileUtils.deleteQuietly(file);
    }

    private List<File> listFiles(File file){
        ArrayList<File> files = new ArrayList<>();
        String[] list = file.list();
        if (list == null) {
            return files;
        }
        for (String dn : list) {
            File f = new File(file, dn);
            files.add(f);
        }
        return files;
    }


    @Override
    public List<DirDesc> list() {
        String path = getSavePath();
        File file = FileUtils.getFile(path);
        List<File> dirs = listFiles(file);
        List<DirDesc> descList = new ArrayList<>();
        for (File ft : dirs) {
            DirDesc dirDesc = new DirDesc();
            dirDesc.setPath(ft.getAbsolutePath());
            List<FileDesc> fileDescList = new ArrayList<>();
            List<File> files = listFiles(ft);
            for (File f : files) {
                FileDesc fileDesc = new FileDesc();
                fileDesc.setFileName(f.getName());
                fileDesc.setSize(formatFileSize(f.length()));
                fileDescList.add(fileDesc);
            }
            dirDesc.setFileDescList(fileDescList);
            descList.add(dirDesc);
        }
        return descList;
    }

    @Override
    public boolean deleteFileByPath(String s) {
        String path = getSavePath() + s;
        File file = new File(path);
        if (file.exists()){
            return file.delete();
        }
        return true;
    }

    private String formatFileSize(long size){
        return String.format("%.2fmb", (double) size / 1024 /1024);
    }
}
