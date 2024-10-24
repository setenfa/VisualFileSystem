package ui.windows;

import event.PathChangeEvent;
import event.PathChangeListener;
import ui.panel.FileView;
import ui.panel.FilePane;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import static res.GlobalResources.FILESYS;
import static res.GlobalResources.TREE;
public class MainWindow extends JFrame implements PathChangeListener {
    private FileView fileView;
    private FilePane filePane;
    private JPanel main;
    private JPanel pathBar;
    private JTextField pathField;


    public MainWindow(int width, int height) {
        FILESYS.initFats();
        initPathBar();
        initMainPanel(width, height);
        initMainWindow(width, height);
        updatePathBar();
    }

    public void initPathBar() {
        pathBar = new JPanel();
        pathBar.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pathField = new JTextField();
        pathField.setPreferredSize(new Dimension(800, 30));
        pathField.setText("C:");
        pathBar.add(pathField);
    }

    public void initMainWindow(int width, int height) {
        this.setSize(width, height);
        this.setLayout(new BorderLayout(0, 0));
        this.add(main, BorderLayout.CENTER);
        this.add(pathBar, BorderLayout.NORTH);
        //this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public void initMainPanel(int width, int height) {
        filePane = new FilePane(TREE.getTreeModel());
        fileView = new FileView(new JPopupMenu());
        fileView.addPathChangeListener(this);
        main = new JPanel(new BorderLayout());
        main.setSize(width, height);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filePane.getFileTreePane(), fileView.getScrollPane());
        splitPane.setDividerLocation(filePane.getFileTreePane().getPreferredSize().width);
        splitPane.setResizeWeight(0.3);
        main.add(splitPane);
    }

    @Override
    public void pathChanged(PathChangeEvent event) {
        pathField.setText(event.getNewPath());
    }

    /***
     * 更新路径栏，并更新文件视图
     */
    public void updatePathBar() {
        filePane.getDirectoryTree().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    TreePath path = filePane.getDirectoryTree().getSelectionPath();
                    if (path != null) {
                        String pathStr = path.toString().replace("[", "").replace("]", "").replace(",", "\\").replace(" ", "").replaceFirst("C", "C:");
                        JTextField pathField = (JTextField) pathBar.getComponent(0);
                        pathField.setText(pathStr);
                        TREE.setCurrentNode((DefaultMutableTreeNode) path.getLastPathComponent());
                        fileView.getFileViewPane().removeAll();
                        fileView.addFileOrFolderOnPanel(FILESYS.getFilesAndFolders(pathStr));
                        fileView.getFileViewPane().updateUI();
                    }
                }
            }
        });
    }
}
