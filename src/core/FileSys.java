package core;

import util.*;

import java.util.Objects;

import static res.GlobalResources.*;

/**
 * 实现文件系统的一些功能
 */
public class FileSys {
    private FAT[] fats;
    private OpenFiles openFiles;

    public FileSys() {
        fats = new FAT[128];
        openFiles = new OpenFiles();
        fats[0] = new FAT(0, "File", null);
        fats[1] = new FAT(1, "Folder", new Folder("C:"));
    }

    // 返回空闲磁盘块的序列号
    public int getFreeBlock() {
        for (int i = 0; i < fats.length; i++) {
            if (fats != null) {
                if (fats[i].getIndex() == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void createFile(String fileName, String property) {
        int diskNum = getFreeBlock();
        boolean canCreate = true;
        String path = ((Folder)TREE.getCurrentNode().getUserObject()).getPath() + fileName;
        int index = 1;
        if (diskNum == -1) {
            System.out.println("磁盘已满");
            return;
        }
        while (canCreate) {
            for (int i = 0; i < fats.length; i++) {
                if (fats[i] != null) {
                    if (Objects.equals(fats[i].getType(), "File")) {
                        File file = (File) fats[i].getObject();
                        if (file.getFileName().equals(fileName)) {
                            // 还未实现文件已存在的问题
                            System.out.println("文件已存在");
                            canCreate = false;
                            break;
                        }
                    }
                }
            }
        }
        if (canCreate) {
            fats[diskNum] = new FAT(diskNum, "File", new File(fileName, diskNum, property, path));
        }
    }
}

