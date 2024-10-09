package util;

public class OpenFile {
    private int flag;   // 0为读，1为写
    private File file;
    private Pointer read;
    private Pointer write;

    public OpenFile(int flag, File file) {
        this.flag = flag;
        this.file = file;
    }

    public int getFlag() {
        return flag;
    }

    public Pointer getRead() {
        return read;
    }

    public Pointer getWrite() {
        return write;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setRead(Pointer read) {
        this.read = read;
    }

    public void setWrite(Pointer write) {
        this.write = write;
    }
}
