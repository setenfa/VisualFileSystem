package core;

import util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static res.GlobalResources.*;

/**
 * 实现文件系统的一些功能
 */
public class FileSys {
    private FAT[] fats;
    private OpenFiles openFiles;

    public FileSys() {
        openFiles = new OpenFiles();
    }

    public void initFats() {
        fats = new FAT[128];
        fats[0] = new FAT(255, "Disk", null);
        fats[1] = new FAT(255, "Disk", new Disk("C"));
    }

    // 返回空闲磁盘块的序列号
    public int getFreeBlock() {
        for (int i = 0; i < fats.length; i++) {
            if (fats != null) {
                if (fats[i] == null) {
                    return i;
                }
            }
        }
        return -1;
    }

    // 创建文件
    public boolean createFile(String fileName, String property) {
        int diskNum = getFreeBlock();
        String path = ((Folder)TREE.getCurrentNode().getUserObject()).getPath();
        if (diskNum == -1) {
            return false;
        }
        for (FAT fat : fats) {
            if (fat != null) {
                if (Objects.equals(fat.getType(), "File")) {
                    File file = (File) fat.getObject();
                    if ((file.getPath() + "\\" + fileName).equals(path + "\\" + fileName)) {
                        return false;
                    }
                }
            }
        }
        fats[diskNum] = new FAT(diskNum, "File", new File(fileName, diskNum, property, path));
        System.out.println(fats[diskNum].getType());
        return true;
    }

    // 创建文件夹
    public boolean createFolder(String folderName) {
        int diskNum = getFreeBlock();
        String path = ((Folder)TREE.getCurrentNode().getUserObject()).getPath();
        if (diskNum == -1) {
            return false;
        }
        for (FAT fat : fats) {
            if (fat != null) {
                if (Objects.equals(fat.getType(), "Folder")) {
                    Folder folder = (Folder) fat.getObject();
                    if ((folder.getPath() + "\\" + folder.getFolderName()).equals(path + "\\" + folderName)) {
                        return false;
                    }
                }
            }
        }
        fats[diskNum] = new FAT(diskNum, "Folder", new Folder(folderName, diskNum, path));
        TREE.addNode(TREE.getCurrentNode(), folderName);
        return true;
    }

    // 打开文件
    public void openFile(FAT fat, int mode) {
        OpenFile openFile = new OpenFile(mode, (File) fat.getObject());
        openFiles.addFile(openFile);
    }

    // 返回某个路径下的所有文件和文件夹
    public List<FAT> getFilesAndFolders(String path) {
        List<FAT> filesAndFolders = new ArrayList<>();
        for (FAT fat : fats) {
            if (fat != null) {
                if (Objects.equals(fat.getType(), "File")) {
                    File file = (File) fat.getObject();
                    if (file.getPath().equals(path)) {
                        filesAndFolders.add(fat);
                    }
                } else if (Objects.equals(fat.getType(), "Folder")) {
                    Folder folder = (Folder) fat.getObject();
                    if (folder.getPath().equals(path)) {
                        filesAndFolders.add(fat);
                    }
                }
            }
        }
        return filesAndFolders;
    }

    // 删除文件或文件夹(牵扯到可视化)
    public void delete(FAT fat) {
        if (Objects.equals(fat.getType(), "File")) {
            File file = (File) fat.getObject();
            fats[file.getDiskNum()] = null;
        } else if (Objects.equals(fat.getType(), "Folder")) {
            Folder folder = (Folder) fat.getObject();
            fats[folder.getDiskNum()] = null;
        }
    }

    // 查看文件是否被打开
    public boolean isFileOpened(FAT fat) {
        for (int i = 0; i < openFiles.getFiles().size(); i++) {
            if (openFiles.getFiles().get(i).getFile() == fat.getObject()) {
                return true;
            }
        }
        return false;
    }

    // 关闭文件
    public void closeFile(FAT fat) {
        for (int i = 0; i < openFiles.getFiles().size(); i++) {
            if (openFiles.getFiles().get(i).getFile() == fat.getObject()) {
                openFiles.getFiles().remove(i);
                break;
            }
        }
    }

    // 读文件
    public String readFile(FAT fat) {
        File file = (File) fat.getObject();
        return file.getContent();
    }

    // 写文件
    public void writeFile(FAT fat, String content) {
        File file = (File) fat.getObject();
        file.setContent(content);
    }

    // 保存时重新计算占据磁盘块数
    public void modifyAfterSave(FAT fat, int num) {
        int start = ((File)fat.getObject()).getDiskNum();
        int index = fats[start].getIndex();
        int oldNum = 1;
        while (index != 255) {
            oldNum++;
            if (fats[index].getIndex() != 255) {
                index = fats[index].getIndex();
            } else {
                start = index;
            }
        }
        if (num > oldNum) {
            if (getFreeBlockNum() < num - oldNum) {
                System.out.println("磁盘空间不足");
                return;
            }
            for (int i = 0; i < num - oldNum; i++) {
                int freeBlock = getFreeBlock();
                fats[start].setIndex(freeBlock);
                start = freeBlock;
            }

        }
    }

    // 获取磁盘空闲块的数目
    public int getFreeBlockNum() {
        int count = 0;
        for (FAT fat : fats) {
            if (fat.getIndex() == 0) {
                count++;
            }
        }
        return count;
    }

    // 获取某个文件或文件夹所占的磁盘块数
    public int getNumOfFile (FAT fat) {
        File file = (File) fat.getObject();
        int length = file.getLength();
        if (length <= 64) {
            return 1;
        } else {
            int num;
            if (length % 64 == 0) {
                num = length / 64;
                return num;
            } else {
                num = length / 64;
                num++;
                return num;
            }
        }
    }

    public boolean isFileOrFolderExist(String path, String fileOrFolderName) {
        List<FAT> fileList = getFilesAndFolders(path);
        for (FAT fat : fileList) {
            if (Objects.equals(fat.getType(), "File")) {
                File file = (File) fat.getObject();
                if (Objects.equals(file.getFileName(), fileOrFolderName)) {
                    return true;
                }
            } else if (Objects.equals(fat.getType(), "Folder")) {
                Folder folder = (Folder) fat.getObject();
                if (Objects.equals(folder.getFolderName(), fileOrFolderName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public OpenFiles getOpenFiles() {
        return openFiles;
    }

    public FAT[] getFats() {
        return fats;
    }
}

