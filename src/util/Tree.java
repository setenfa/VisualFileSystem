package util;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Tree {
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode currentNode;
    public Tree() {
        initialTree();
    }

    private void initialTree() {
        Folder root = new Folder("C", 0, "C:");
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root);
        this.treeModel = new DefaultTreeModel(rootNode);
        this.currentNode = rootNode;
    }

    // 创建文件夹时调用，更新树
    public void addNode(DefaultMutableTreeNode root, String nodeName) {
        Folder folder = (Folder) root.getUserObject();
        Folder newFolder = new Folder(nodeName);
        newFolder.setPath(folder.getPath() + "\\" + nodeName);
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newFolder);
        root.add(newNode);
        treeModel.reload(root);
    }

    // 删除文件夹时调用，更新树
    public void deleteNode(DefaultMutableTreeNode currentNode, String path) {
        DefaultMutableTreeNode searchNode = getChildNodeByPathRecursive(currentNode, path);
        if (searchNode == null) {
            JOptionPane.showMessageDialog(null, "文件夹不存在");
        } else {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) searchNode.getParent();
            parent.remove(searchNode);
            treeModel.reload(parent);
        }
    }
    // 重命名文件夹时调用，更新树
    public void renameNode(DefaultMutableTreeNode currentNode, String path, String newName) {
        DefaultMutableTreeNode searchNode = getChildNodeByPathRecursive(currentNode, path);
        if (searchNode == null) {
            JOptionPane.showMessageDialog(null, "文件夹不存在");
        } else {
            Folder folder = (Folder) searchNode.getUserObject();
            folder.setFolderName(newName);
            folder.setPath(folder.getPath().substring(0, folder.getPath().lastIndexOf("\\") + 1) + newName);
            treeModel.reload(searchNode);
        }
    }

    public DefaultMutableTreeNode getChildNodeByPathRecursive(DefaultMutableTreeNode root, String path) {
        if (root.getChildCount() == 0) {
            return null;
        }
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
            Folder folder = (Folder) child.getUserObject();
            if (folder.getPath().equals(path)) {
                return child;
            }
        }
        return null;
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
