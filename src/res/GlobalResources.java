package res;

import core.FileSys;
import util.Tree;

public class GlobalResources {
    public static final Tree TREE;
    public static final FileSys FILESYS;
    static {
        TREE = new Tree();
        FILESYS = new FileSys();
    }
}
