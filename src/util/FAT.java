package util;

public class FAT {
    private String type;
    private int index;
    private Object object;        // 用于存储文件对象和文件夹对象

    public FAT(int index, String type, Object object) {
        this.index = index;
        this.type = type;
        this.object = object;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }



//    public void printFat(){
//        for(int i = 0; i < fat.length; i++){
//            System.out.println("FAT[" + i + "] = " + fat[i]);
//        }
//    }
}
