package core;

import util.*;
import static res.GlobalResources.*;

/**
 * 实现文件系统的一些功能
 */
public class FileSys {
    private FAT fats;
    private OpenFiles openFiles;

    public FileSys() {
        fats = new FAT(128);
        openFiles = new OpenFiles();
        fats.setFatEntry(0, 255);   //系统
        fats.setFatEntry(1, 255);   //根目录
    }

    public void createFile(String fileName, String property) {
        if(fats.isFull()) {
            System.out.println("磁盘已满，无法创建文件");
            return;
        }
        if(property.equals("0")) {
            System.out.println("只读文件无法创建");
            return;
        }
        String path = ((Folder)TREE.getCurrentNode().getUserObject()).getPath() + fileName;   //还未实现如何取出路径
        int index;
        for (index = 2; index < fats.getSize(); index++) {
            if (fats.getFatEntry(index) == 0) {
                fats.setFatEntry(index, 255);
                // 未联动可视化
                break;
            }
        }
        // 将文件放进文件夹
        File newFile = new File(fileName, index, property, path);

        // 未联动可视化
    }




}

