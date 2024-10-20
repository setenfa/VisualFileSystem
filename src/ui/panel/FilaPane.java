package ui.panel;

import ui.windows.MainWindow;
import util.Folder;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static res.GlobalResources.FILESYS;
import static res.GlobalResources.TREE;

public class FilaPane {
    private MainWindow mainWindow;
    private DefaultTreeModel tree;
    private JTree directoryTree;
    private JPanel fileTreePane;    // 文件树面板

    public FilaPane(DefaultTreeModel tree) {
        this.tree = tree;
        this.directoryTree = new JTree(tree);
        initFileTreePane();
        mainWindow = new MainWindow();
        // 添加树的鼠标监听事件
        // 重新设置当前节点
        this.directoryTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 获取当前选中的节点,并更改当前路径
                if (e.getButton() == MouseEvent.BUTTON1) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();
                    Folder folder = (Folder) node.getUserObject();
                    if (TREE.getCurrentNode().getUserObject() == null || !TREE.getCurrentNode().getUserObject().toString().equals(folder.toString())) {
                        TREE.setCurrentNode(node);// 再更新文件列表面板（未实现）
                    }
                }
            }
        });
    }

    public void initFileTreePane() {
        fileTreePane = new JPanel();
        fileTreePane.add(new JScrollPane(directoryTree));
    }

    public JPanel getFileTreePane() {
        return fileTreePane;
    }

}
