package util;

public class FAT {
    private int[] fat;
    private int size;

    public FAT(int size){
        this.size = size;
        fat = new int[this.size];
        for(int i = 0; i < size; i++){
            fat[i] = 0;
        }
    }

    public int[] getFat(){
        return fat;
    }

    public void setFat(int[] fat){
        this.fat = fat;
    }

    public int getFatEntry(int index){
        return fat[index];
    }

    public void setFatEntry(int index, int value){
        fat[index] = value;
    }

    // 检查是否为满
    public boolean isFull(){
        for(int i = 0; i < fat.length; i++){
            if(fat[i] == 0){
                return false;
            }
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    // 返回空闲的磁盘块号
    public int getFreeBlock() {
        for (int i = 2; i < size; i++) {
            if (fat[i] == 0) {
                return i;
            }
        }
        return -1;
    }
//    public void printFat(){
//        for(int i = 0; i < fat.length; i++){
//            System.out.println("FAT[" + i + "] = " + fat[i]);
//        }
//    }
}
