package util;

import java.lang.StringBuilder;
public class File {
    private String fileName;    //文件名
    private String type;        //文件类型
    private String property;    //文件属性
    private int diskNum;        //文件起始盘块号
    private int length;         //文件长度
    private String content;     //文件内容
    private String path;        //文件路径

    public File(String fileName, int diskNum, String property, String path) {
        this.fileName = fileName;
        this.diskNum = diskNum;
        this.length = 0;
        this.property = property;
        this.path = path;
        this.type = "File";
    }

    public String getFileName() {
        return fileName;
    }

    public String getType() {
        return type;
    }

    public String getProperty() {
        return property;
    }

    public int getDiskNum() {
        return diskNum;
    }

    public int getLength() {
        return length;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setDiskNum(int diskNum) {
        this.diskNum = diskNum;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
