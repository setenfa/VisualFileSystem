package util;

public class OpenFile {
    private int flag;   // 0为读，1为写
    private String name;
    private int number; // 文件起始盘块号
    private int length; // 文件长度
    private Pointer read;
    private Pointer write;

    public OpenFile(int flag, String name, int number, int length) {
        this.flag = flag;
        this.name = name;
        this.number = number;
        this.length = length;
    }

    public int getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public int getLength() {
        return length;
    }

    public Pointer getRead() {
        return read;
    }

    public Pointer getWrite() {
        return write;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setRead(Pointer read) {
        this.read = read;
    }

    public void setWrite(Pointer write) {
        this.write = write;
    }
}
