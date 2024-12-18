package ui.panel;


import util.Folder;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

import static res.GlobalResources.FOLDER_ICON_ORIGINAL;

public class FilePane {
    private DefaultTreeModel tree;
    private JTree directoryTree;
    private JScrollPane scrollPane;

    public FilePane(DefaultTreeModel tree) {
        this.tree = tree;
        this.directoryTree = new JTree(tree);
        initFileTreePane();
    }

    public void initFileTreePane() {
        this.directoryTree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                          boolean leaf, int row, boolean hasFocus) {
                JLabel label = new JLabel(value.toString());
                return label;
            }
        });
        this.directoryTree.setShowsRootHandles(true);
        scrollPane = new JScrollPane(directoryTree);
        scrollPane.setPreferredSize(new Dimension(200, 400));
    }

    public JScrollPane getFileTreePane() {
        return scrollPane;
    }

    public JTree getDirectoryTree() {
        return directoryTree;
    }

}
