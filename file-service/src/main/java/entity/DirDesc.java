package entity;

import java.util.List;

public class DirDesc {
    private String path;
    private List<FileDesc> fileDescList;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<FileDesc> getFileDescList() {
        return fileDescList;
    }

    public void setFileDescList(List<FileDesc> fileDescList) {
        this.fileDescList = fileDescList;
    }
}
