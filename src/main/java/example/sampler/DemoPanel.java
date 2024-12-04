package example.sampler;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

/**
 * A simple panel demoing a component.
 */
public class DemoPanel extends Panel {
    public DemoPanel(String caption) {
        this(caption, null);
    }
    public DemoPanel(String caption, Component content) {
        super(caption, content);
        setId(getClass().getSimpleName());
    }
}
