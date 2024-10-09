package util;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import static res.GlobalResources.FILESYS;

public class Tree {
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode currentNode;
    public Tree() {
        initialTree();
    }

    private void initialTree() {
        Folder root = new Folder("C:");
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root);
        this.treeModel = new DefaultTreeModel(rootNode);
        this.currentNode = rootNode;
    }

    // 创建文件夹时调用，更新树
    public void addNode(DefaultMutableTreeNode root, String nodeName) {
        Folder folder = (Folder) root.getUserObject();
        Folder newFolder = new Folder(nodeName);
        newFolder.setPath(folder.getPath());
        newFolder.setDiskNum(FILESYS.getFreeBlock());
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newFolder);
        root.add(newNode);
        treeModel.reload(root);
    }

    public DefaultMutableTreeNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(DefaultMutableTreeNode currentNode) {
        this.currentNode = currentNode;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public void setTreeModel(DefaultTreeModel treeModel) {
        this.treeModel = treeModel;
    }
}
