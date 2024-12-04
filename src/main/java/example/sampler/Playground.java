package example.sampler;

import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;

public class Playground extends VerticalLayout implements View {
    public Playground() {
        setMargin(false);
        setId(getClass().getSimpleName());
    }
}
