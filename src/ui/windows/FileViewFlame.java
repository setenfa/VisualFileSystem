package ui.windows;

import util.FAT;
import util.File;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static res.GlobalResources.FILESYS;

public class FileViewFlame extends JFrame {
    private JTextArea textArea;
    private JMenu operateMenu;
    private Boolean isSaved;
    private Boolean isModified;
    private final FAT fat;
    private String flag;

    public FileViewFlame(FAT fat, String flag) {
        super(((File)fat.getObject()).getFileName());
        isSaved = true;
        isModified = false;
        this.fat = fat;
        this.flag = flag;
        initMenu();
        initMenuBar();
        initTextArea();
        this.add(textArea);
        initMainWindow();
    }

    private void initMenu() {
        operateMenu = new JMenu("操作");
        JMenuItem saveItem = new JMenuItem("保存");
        JMenuItem closeItem = new JMenuItem("关闭");
        JMenuItem writeItem = new JMenuItem("可写");
        saveItem.addActionListener(e -> {
            if (isModified) {
                ((File)fat.getObject()).setContent(textArea.getText());
                FILESYS.modifyAfterSave(fat, FILESYS.getNumOfFile(fat));
                isModified = false;
            }
        });
        closeItem.addActionListener(e -> this.dispose());
        writeItem.addActionListener(e -> {
            ((File)fat.getObject()).setProperty("1");
            flag = "1";
            updateTextAreaEditable();
        });
        operateMenu.add(saveItem);
        operateMenu.add(closeItem);
        operateMenu.add(writeItem);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(operateMenu);
        this.add(menuBar, BorderLayout.NORTH);
    }

    private void initTextArea() {
        File file = (File) fat.getObject();
        textArea = new JTextArea();
        textArea.setText(file.getContent());
        textArea.setEditable(flag.equals("1"));
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isModified = true;
                isSaved = false;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isModified = true;
                isSaved = false;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    private void initMainWindow() {
        this.setSize(800, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 正在关闭时，如果没按保存，提示用户是否保存
                if (isModified) {
                    int result = JOptionPane.showConfirmDialog(null, "文件已修改，是否保存文件？", "提示", JOptionPane.YES_NO_OPTION);
                    isSaved = result == JOptionPane.YES_OPTION;
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (isSaved) {
                    File file = (File)fat.getObject();
                    file.setContent(textArea.getText());
                    FILESYS.modifyAfterSave(fat, FILESYS.getNumOfFile(fat));
                }
                FILESYS.closeFile(fat);
            }
        });
    }

    private void updateTextAreaEditable() {
        textArea.setEditable(flag.equals("1"));
    }
}
