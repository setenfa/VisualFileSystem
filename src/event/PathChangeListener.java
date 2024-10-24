package event;

import java.util.EventListener;

public interface PathChangeListener extends EventListener {
    void pathChanged(PathChangeEvent event);
}
