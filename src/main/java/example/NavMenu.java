package example;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.jetbrains.annotations.NotNull;

public class NavMenu extends Panel {
    private final VerticalLayout content = new VerticalLayout();
    public NavMenu() {
        setWidthUndefined();
        setHeightFull();
        setCaption("Vaadin 8 Sampler; v" + Utils.getVaadinVersion());
        setContent(content);
        content.setWidthUndefined();
    }
    /**
     * @param title the title of the view
     * @param path the path of the view, e.g. <code>""</code> or <code>"grid"</code>.
     * @param view the view class.
     */
    public void addView(@NotNull String title, @NotNull String path, @NotNull Class<? extends View> view) {
        UI.getCurrent().getNavigator().addView(path, view);
        final Button link = new Button(title);
        link.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(path));
        content.addComponent(link);
    }
}
