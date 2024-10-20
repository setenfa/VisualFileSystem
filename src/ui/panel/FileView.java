package ui.panel;

import ui.windows.FileViewFlame;
import util.FAT;
import util.File;
import util.Folder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static res.GlobalResources.FILESYS;
import static res.GlobalResources.TREE;

public class FileView {
    private JPanel fileViewPane;
    private JPopupMenu jPopupMenu;
    private JPopupMenu panelMenu;

    public FileView(JPopupMenu jPopupMenu) {
        this.jPopupMenu = jPopupMenu;
        initFileViewPane();
        initPanelMenu();

    }

    public void initFileViewPane() {
        fileViewPane = new JPanel();

        fileViewPane.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        fileViewPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    panelMenu.show(fileViewPane, e.getX(), e.getY());
                }
            }
        });

    }

    public JPanel addFileOnPanel(List<FAT> files) {
        JPanel panel = new JPanel();
        JLabel label;
        for (FAT fat : files) {
            if (fat != null) {
                if (fat.getObject() instanceof File file) {
                    label = new JLabel(file.getFileName());
                } else {
                    Folder folder = (Folder) fat.getObject();
                    label = new JLabel(folder.getFolderName());
                }
                JLabel finalLabel = label;
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            if (fat.getObject() instanceof Folder folder) {
                                String path = folder.getPath() + "\\" + folder.getFolderName();
                                fileViewPane.removeAll();
                                addFileOnPanel(FILESYS.getFilesAndFolders(path));
                                fileViewPane.updateUI();
                                // 更新ui
                            } else {
                                if (FILESYS.getOpenFiles().getFiles().size() < 5) {
                                    if (FILESYS.isFileOpened(fat)) {
                                        JOptionPane.showMessageDialog(null, "文件已被打开");
                                    } else {
                                        FILESYS.openFile(fat, 1);
                                        // 文件的frame还未实现
                                        new FileViewFlame((File) fat.getObject(), 0);
                                    }
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            JMenuItem delete = getDeleteItem(fat);
                            JMenuItem rename = getRenameItem(fat);
                            jPopupMenu.add(delete);
                            jPopupMenu.add(rename);
                            jPopupMenu.show(finalLabel, e.getX(), e.getY());
                        }
                    }
                });
                this.fileViewPane.add(label);
            }
        }
        return panel;
    }

    private JMenuItem getDeleteItem(FAT fat) {
        JMenuItem delete = new JMenuItem("删除");
        delete.addActionListener(e1 -> {
            if (fat.getObject() instanceof File) {
                FILESYS.delete(fat);
            } else {
                if (!FILESYS.getFilesAndFolders(((Folder) fat.getObject()).getPath()).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "文件夹不为空，无法删除");
                    return;
                } else {
                    FILESYS.delete(fat);
                }
            }
            fileViewPane.removeAll();
            addFileOnPanel(TREE.getCurrentNode().getUserObject() instanceof Folder folder ? FILESYS.getFilesAndFolders(folder.getPath()) : FILESYS.getFilesAndFolders(""));
            fileViewPane.updateUI();
        });
        return delete;
    }

    private JMenuItem getRenameItem(FAT fat) {
        JMenuItem rename = new JMenuItem("重命名");
        rename.addActionListener(e -> {
            if (fat.getObject() instanceof File) {
                String fileName = JOptionPane.showInputDialog("请输入新的文件名");
                if (fileName != null) {
                    ((File) fat.getObject()).setFileName(fileName);
                }
            } else {
                String folderName = JOptionPane.showInputDialog("请输入新的文件夹名");
                if (folderName != null) {
                    ((Folder) fat.getObject()).setFolderName(folderName);
                }
            }
            fileViewPane.removeAll();
            addFileOnPanel(TREE.getCurrentNode().getUserObject() instanceof Folder folder ? FILESYS.getFilesAndFolders(folder.getPath()) : FILESYS.getFilesAndFolders(""));
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
                FILESYS.createFile(fileName, "1");
                fileViewPane.removeAll();
                addFileOnPanel(TREE.getCurrentNode().getUserObject() instanceof Folder folder ? FILESYS.getFilesAndFolders(folder.getPath()) : FILESYS.getFilesAndFolders(""));
                fileViewPane.updateUI();
            }
        });
        newFolder.addActionListener(e -> {
            String folderName = JOptionPane.showInputDialog("请输入文件夹名");
            if (folderName != null) {
                FILESYS.createFolder(folderName);
                fileViewPane.removeAll();
                addFileOnPanel(TREE.getCurrentNode().getUserObject() instanceof Folder folder ? FILESYS.getFilesAndFolders(folder.getPath()) : FILESYS.getFilesAndFolders(""));
                fileViewPane.updateUI();
            }
        });
        panelMenu.add(newFile);
        panelMenu.add(newFolder);
    }
}
