package ui.windows;

import util.File;

import javax.swing.*;

public class FileViewFlame extends JFrame {
    public FileViewFlame(File file, int flag) {
        super(file.getFileName());
        JTextArea textArea = new JTextArea();
        textArea.setText(file.getContent());
        if (flag == 0) {
            textArea.setEditable(false);
        } else {
            textArea.setEditable(true);
        }
        this.add(textArea);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
