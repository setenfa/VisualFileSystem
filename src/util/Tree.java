package util;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Tree {
    private JTree tree;
    private DefaultMutableTreeNode currentNode;

    public Tree() {
        initialTree();
    }

    private void initialTree() {
        Folder root = new Folder("C:");
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root);
        tree = new JTree(rootNode);
        tree.setRootVisible(true);
        currentNode = rootNode;
    }

    // 创建文件夹时调用，更新树
    public void addNode(Folder folder, String nodeName) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) this.tree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }
        Folder newFolder = new Folder(nodeName);
        newFolder.setPath(folder.getPath() + "/" + nodeName);
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newFolder);
        selectedNode.add(newNode);
        DefaultTreeModel model = (DefaultTreeModel) this.tree.getModel();
        model.reload(selectedNode);
    }

    public DefaultMutableTreeNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(DefaultMutableTreeNode currentNode) {
        this.currentNode = currentNode;
    }


}
