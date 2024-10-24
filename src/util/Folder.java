package util;

public class Folder {
    private String folderName;      //文件夹名
    private String type;            //文件夹类型
    private String property;        //文件夹属性
    private int diskNum;            //文件夹起始盘块号
    private String path;            //文件夹路径

    public Folder(String folderName){
        this.folderName = folderName;
    }

    public Folder(String folderName, int diskNum, String path) {
        this.folderName = folderName;
        this.type = "Folder";
        this.diskNum = diskNum;
        this.path = path;
    }

    public String getFolderName() {
        return folderName;
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

    public String getPath() {
        return path;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
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

    public void setPath(String path) {
        this.path = path;
    }

    public String toString() {
        return folderName;
    }
}
