package res;

import core.FileSys;
import ui.windows.Main;
import util.Tree;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GlobalResources {
    public static Icon FILE_ICON;
    public static Icon FOLDER_ICON;
    public static Icon FOLDER_ICON_ORIGINAL;
    public static final Tree TREE;
    public static final FileSys FILESYS;
    static {
        TREE = new Tree();
        FILESYS = new FileSys();
        FILE_ICON = loadIconFromResource("res/provideIcon/file.png");
        FILE_ICON = getScaledIcon(FILE_ICON, 50);
        FOLDER_ICON_ORIGINAL = loadIconFromResource("res/provideIcon/folder.png");
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

    private static Icon loadIconFromResource(String path) {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream != null) {
                // 使用 ImageIO 读取图像
                Image img = ImageIO.read(inputStream);
                return new ImageIcon(img);  // 返回图标
            } else {
                System.out.println("Resource not found: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;  // 如果加载失败，则返回 null
    }

}
