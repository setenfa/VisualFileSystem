package ui.windows;

import util.FAT;
import util.File;
import util.Folder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static res.GlobalResources.FILESYS;

public class PropertyShow extends JDialog {
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JCheckBox[] checkBoxes;

    public PropertyShow(Component c,FAT fat) {

        initButtonPanel(fat);
        initMainPanelOfFile(fat);
        init(c);
    }

    private void initMainPanelOfFile(FAT fat) {
        File file = (File) fat.getObject();
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 10, 5);

        JLabel[] labels = new JLabel[6];
        JTextField[] textFields = new JTextField[5];

        // 填空上部分，以免间距过大
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        mainPanel.add(Box.createVerticalStrut(5), gbc);

        labels[0] = new JLabel("文件名:");
        textFields[0] = new JTextField(file.getFileName());
        textFields[0].setPreferredSize(new Dimension(200, 30));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0;
        mainPanel.add(labels[0], gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        mainPanel.add(textFields[0], gbc);

        labels[1] = new JLabel("文件类型:");
        textFields[1] = new JTextField(file.getType());
        textFields[1].setPreferredSize(new Dimension(200, 30));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.weighty = 0;
        mainPanel.add(labels[1], gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        mainPanel.add(textFields[1], gbc);

        labels[2] = new JLabel("位置:");
        textFields[2] = new JTextField(file.getPath());
        textFields[2].setPreferredSize(new Dimension(200, 30));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        gbc.weighty = 0;
        mainPanel.add(labels[2], gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        mainPanel.add(textFields[2], gbc);

        labels[3] = new JLabel("大小:");
        textFields[3] = new JTextField(file.getLength() + "B");
        textFields[3].setPreferredSize(new Dimension(200, 30));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        gbc.weighty = 0;
        mainPanel.add(labels[3], gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        mainPanel.add(textFields[3], gbc);

        labels[4] = new JLabel("占用空间:");
        int occupySize = FILESYS.getNumOfFile(fat) * 64;
        textFields[4] = new JTextField(occupySize + "B");
        textFields[4].setPreferredSize(new Dimension(200, 30));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.2;
        gbc.weighty = 0;
        mainPanel.add(labels[4], gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        mainPanel.add(textFields[4], gbc);

        labels[5] = new JLabel("属性:");
        JPanel checkBoxPanel = new JPanel();
        checkBoxes = new JCheckBox[2];
        checkBoxes[0] = new JCheckBox("只读");
        checkBoxes[1] = new JCheckBox("可写");
        if (file.getProperty().equals("0")) {
            checkBoxes[0].setSelected(true);
        } else {
            checkBoxes[1].setSelected(true);
        }
        ActionListener listener = e -> {
            if (e.getSource() == checkBoxes[0] && checkBoxes[1].isSelected()) {
                checkBoxes[1].setSelected(false);
            } else {
                checkBoxes[0].setSelected(false);
            }
        };
        checkBoxes[0].addActionListener(listener);
        checkBoxes[1].addActionListener(listener);
        checkBoxPanel.add(checkBoxes[0]);
        checkBoxPanel.add(checkBoxes[1]);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.2;
        gbc.weighty = 0;
        mainPanel.add(labels[5], gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        mainPanel.add(checkBoxPanel, gbc);

        // 填空下部分，以免间距过大
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        mainPanel.add(Box.createVerticalStrut(10), gbc);

        for (JTextField textField : textFields) {
            textField.setEditable(false);
        }
    }

    private void initButtonPanel(FAT fat) {
        JButton[] buttons = new JButton[3];
        buttons[0] = new JButton("确定");
        buttons[1] = new JButton("取消");
        buttons[2] = new JButton("应用");
        buttons[0].addActionListener(e -> {
            addListenerToButton(fat);
            this.dispose();
        });
        buttons[1].addActionListener(e -> this.dispose());
        buttons[2].addActionListener(e -> addListenerToButton(fat));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
    }

    private void addListenerToButton(FAT fat) {
        if (fat.getObject() instanceof File file) {
            if (checkBoxes[0].isSelected()) {
                file.setProperty("0");
            } else {
                file.setProperty("1");
            }
        } else {
            Folder folder = (Folder) fat.getObject();
            if (checkBoxes[0].isSelected()) {
                folder.setProperty("0");
            } else {
                folder.setProperty("1");
            }
        }
    }

    private void init(Component component) {
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setSize(300, 400);
        this.setBackground(Color.WHITE);
        this.setTitle("属性");
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(component);
        this.setVisible(true);
    }
}
