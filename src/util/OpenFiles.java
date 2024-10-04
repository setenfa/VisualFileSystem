package util;
import java.util.ArrayList;
public class OpenFiles {
    private ArrayList<OpenFile> files;      // 已打开文件集合
    private int length;                     // 已打开文件数

    public OpenFiles(){
        files = new ArrayList<OpenFile>();
        length = 0;
    }

    public void addFile(OpenFile openFile){
        files.add(openFile);
    }

    public void removeFile(OpenFile openFile){
        files.remove(openFile);
    }

    public ArrayList<OpenFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<OpenFile> files) {
        this.files = files;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
