package ui.panel;


import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

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
