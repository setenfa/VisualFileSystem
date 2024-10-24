package event;

import java.util.EventObject;

public class PathChangeEvent extends EventObject {
    private String newPath;

    public PathChangeEvent(Object source, String newPath) {
        super(source);
        this.newPath = newPath;
    }

    public String getNewPath() {
        return newPath;
    }
}
