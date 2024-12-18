package res;

import core.FileSys;
import ui.windows.Main;
import util.Tree;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GlobalResources {
    public static final File FILE1;
    public static final File FILE2;
    public static Icon FILE_ICON;
    public static Icon FOLDER_ICON;
    public static Icon FOLDER_ICON_ORIGINAL;
    public static final FileSystemView FILE_SYSTEM_VIEW = FileSystemView.getFileSystemView();
    public static final Tree TREE;
    public static final FileSys FILESYS;
    static {
        FILE1 = new File(Main.class.getClassLoader().getResource("res/provideIcon/provideIcon.txt").getFile());
        FILE2 = new File(Main.class.getClassLoader().getResource("res/provideIcon").getFile());
        TREE = new Tree();
        FILESYS = new FileSys();
        FILE_ICON = FILE_SYSTEM_VIEW.getSystemIcon(FILE1);
        FILE_ICON = getScaledIcon(FILE_ICON, 50);
        FOLDER_ICON_ORIGINAL = FILE_SYSTEM_VIEW.getSystemIcon(FILE2);
        FOLDER_ICON = getScaledIcon(FOLDER_ICON_ORIGINAL, 50);
    }

    private static Icon getScaledIcon(Icon icon, int scale) {
        if (icon instanceof ImageIcon) {
            Image img = ((ImageIcon) icon).getImage();
            BufferedImage bufferedImage = new BufferedImage(scale, scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(img, 0, 0, scale, scale, null);
            g2d.dispose();
            return new ImageIcon(bufferedImage);
        }
        return icon;
    }


}
