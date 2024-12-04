package example.sampler;

import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.jetbrains.annotations.NotNull;

public class ButtonDemoPane extends VerticalLayout implements View {
    public ButtonDemoPane() {
        setMargin(false);
        setId(getClass().getSimpleName());
        addComponent(newButtonDemo());
        addComponent(newNativeButtonDemo());
    }

    @NotNull
    private Component newButtonDemo() {
        final Button btn = new Button();
        btn.addClickListener(e -> Notification.show("Clicked!"));
        btn.addContextClickListener(e -> Notification.show("context-Clicked!"));
        return new ComponentDemo<>(btn)
                .controlBool("Disable on click", Button::setDisableOnClick)
                .controlBool("Icon alternate text", (button, b) -> button.setIconAlternateText(b ? "Alternate text" : ""))
                .controlStyles(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_FRIENDLY, ValoTheme.BUTTON_DANGER, ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_LINK, ValoTheme.BUTTON_TINY, ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_HUGE, ValoTheme.BUTTON_ICON_ALIGN_RIGHT, ValoTheme.BUTTON_ICON_ALIGN_TOP, ValoTheme.BUTTON_ICON_ONLY)
        ;
    }

    @NotNull
    private Component newNativeButtonDemo() {
        final NativeButton btn = new NativeButton();
        btn.addClickListener(e -> Notification.show("Clicked!"));
        return new ComponentDemo<>(btn)
                .controlBool("Disable on click", Button::setDisableOnClick)
                .controlBool("Icon alternate text", (button, b) -> button.setIconAlternateText(b ? "Alternate text" : ""))
                ;
    }
}
