package ui.panel;

import event.PathChangeEvent;
import event.PathChangeListener;
import ui.windows.FileViewFlame;
import ui.windows.PropertyShow;
import util.FAT;
import util.File;
import util.Folder;
import util.ui.CustomFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static res.GlobalResources.FILESYS;
import static res.GlobalResources.TREE;
import static res.GlobalResources.FILE_ICON;
import static res.GlobalResources.FOLDER_ICON;

public class FileView {
    private JPanel fileViewPane;
    private final JPopupMenu jPopupMenu;
    private JPopupMenu panelMenu;
    private JScrollPane scrollPane;
    private final List<PathChangeListener> listeners;

    public FileView(JPopupMenu jPopupMenu) {
        this.jPopupMenu = jPopupMenu;
        initFileViewPane();
        initPanelMenu();
        listeners = new ArrayList<>();
    }

    public void initFileViewPane() {
        fileViewPane = new JPanel(new CustomFlowLayout(FlowLayout.LEFT, 17, 10)) {
            @Override
            public Insets getInsets() {
                return new Insets(10, 0, 10, 0);
            }
        };
        fileViewPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    panelMenu.show(fileViewPane, e.getX(), e.getY());
                }
            }
        });
        scrollPane = new JScrollPane(fileViewPane);
        scrollPane.setPreferredSize(new Dimension(800, 600));
    }

    public void addFileOrFolderOnPanel(List<FAT> files) {
        JLabel label;
        for (FAT fat : files) {
            if (fat != null) {
                if (fat.getObject() instanceof File file) {
                    label = new JLabel(file.getFileName());
                    if (FILE_ICON != null) {
                        label.setIcon(FILE_ICON);
                    } else {
                        System.out.println("FILE_ICON is null");
                    }
                } else {
                    Folder folder = (Folder) fat.getObject();
                    label = new JLabel(folder.getFolderName());
                    label.setIcon(FOLDER_ICON);
                }
                label.setVerticalTextPosition(SwingConstants.BOTTOM);
                label.setHorizontalTextPosition(SwingConstants.CENTER);
                JLabel finalLabel = label;
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            if (fat.getObject() instanceof Folder folder) {
                                String newPath = folder.getPath() + "\\" + folder.getFolderName();
                                TREE.setCurrentNode(TREE.getChildNodeByPathRecursive(TREE.getCurrentNode(), newPath));
                                // 更新路径栏
                                firePathChangeEvent(newPath);
                                fileViewPane.removeAll();
                                addFileOrFolderOnPanel(FILESYS.getFilesAndFolders(newPath));
                                fileViewPane.updateUI();
                                // 更新ui
                            } else {
                                if (FILESYS.getOpenFiles().getFiles().size() < 5) {
                                    if (FILESYS.isFileOpened(fat)) {
                                        JOptionPane.showMessageDialog(null, "文件已被打开");
                                    } else {
                                        FILESYS.openFile(fat, 1);
                                        // 文件的frame还未实现
                                        File file = (File) fat.getObject();
                                        new FileViewFlame(fat, file.getProperty());
                                    }
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            jPopupMenu.removeAll();
                            JMenuItem delete = getDeleteItem(fat);
                            JMenuItem rename = getRenameItem(fat);
                            jPopupMenu.add(delete);
                            jPopupMenu.add(rename);
                            if (fat.getObject() instanceof File) {
                                JMenuItem check = new JMenuItem("属性");
                                check.addActionListener(e1 -> new PropertyShow(fileViewPane, fat));
                                jPopupMenu.add(check);
                            }
                            jPopupMenu.show(finalLabel, e.getX(), e.getY());
                        }
                    }
                });
                this.fileViewPane.add(label);
            }
        }
    }

    private JMenuItem getDeleteItem(FAT fat) {
        JMenuItem delete = new JMenuItem("删除");
        delete.addActionListener(e1 -> {
            if (fat.getObject() instanceof File) {
                FILESYS.delete(fat);
            } else {
                if (!FILESYS.getFilesAndFolders(((Folder) fat.getObject()).getPath() + "\\" + ((Folder) fat.getObject()).getFolderName()).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "文件夹不为空，无法删除");
                    return;
                } else {
                    Folder folder = (Folder) fat.getObject();
                    String path = folder.getPath() + "\\" + folder.getFolderName();
                    TREE.deleteNode(TREE.getCurrentNode(), path);
                    FILESYS.delete(fat);
                }
            }
            fileViewPane.removeAll();
            Folder folder = (Folder) TREE.getCurrentNode().getUserObject();
            addFileOrFolderOnPanel(FILESYS.getFilesAndFolders(folder.getPath()));
            fileViewPane.updateUI();
        });
        return delete;
    }

    private JMenuItem getRenameItem(FAT fat) {
        JMenuItem rename = new JMenuItem("重命名");
        rename.addActionListener(e -> {
            if (fat.getObject() instanceof File file) {
                String fileName = JOptionPane.showInputDialog("请输入新的文件名");
                if (fileName != null) {
                    if (FILESYS.isFileOrFolderExist(file.getPath(), fileName)) {
                        JOptionPane.showMessageDialog(null, "文件已存在");
                        return;
                    } else {
                        file.setFileName(fileName);
                    }
                }
            } else {
                String folderName = JOptionPane.showInputDialog("请输入新的文件夹名");
                if (folderName != null) {
                    if (FILESYS.isFileOrFolderExist(((Folder) fat.getObject()).getPath(), folderName)) {
                        JOptionPane.showMessageDialog(null, "文件夹已存在");
                        return;
                    } else {
                        ((Folder) fat.getObject()).setFolderName(folderName);
                    }
                }
            }
            fileViewPane.removeAll();
            Folder folder = (Folder) TREE.getCurrentNode().getUserObject();
            addFileOrFolderOnPanel(FILESYS.getFilesAndFolders(folder.getPath()));
            fileViewPane.updateUI();
        });
        return rename;
    }

    private void initPanelMenu() {
        panelMenu = new JPopupMenu();
        JMenuItem newFile = new JMenuItem("新建文件");
        JMenuItem newFolder = new JMenuItem("新建文件夹");
        newFile.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog("请输入文件名");
            if (fileName != null) {
                if (!FILESYS.createFile(fileName, "1")) {
                    JOptionPane.showMessageDialog(null, "磁盘已满或文件已存在");
                } else {
                    fileViewPane.removeAll();
                    Folder folder = (Folder) TREE.getCurrentNode().getUserObject();
                    addFileOrFolderOnPanel(FILESYS.getFilesAndFolders(folder.getPath()));
                    fileViewPane.updateUI();
                }
            }
        });
        newFolder.addActionListener(e -> {
            String folderName = JOptionPane.showInputDialog("请输入文件夹名");
            if (folderName != null) {
                if (!FILESYS.createFolder(folderName)) {
                    JOptionPane.showMessageDialog(null, "磁盘已满或文件夹已存在");
                } else {
                    fileViewPane.removeAll();
                    Folder folder = (Folder) TREE.getCurrentNode().getUserObject();
                    addFileOrFolderOnPanel(FILESYS.getFilesAndFolders(folder.getPath()));
                    fileViewPane.updateUI();
                }
            }
        });
        panelMenu.add(newFile);
        panelMenu.add(newFolder);
    }

    public JPanel getFileViewPane() {
        return fileViewPane;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void addPathChangeListener(PathChangeListener listener) {
        listeners.add(listener);
    }

    private void firePathChangeEvent(String newPath) {
        PathChangeEvent event = new PathChangeEvent(this, newPath);
        for (PathChangeListener listener : listeners) {
            listener.pathChanged(event);
        }
    }
}
