package example.sampler;

import com.vaadin.contextmenu.ContextMenu;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import example.Utils;
import org.jetbrains.annotations.NotNull;

public class PopupDemoPane extends VerticalLayout implements View {
    public PopupDemoPane() {
        setMargin(false);
        setId(getClass().getSimpleName());
        addComponent(newWindowDemo());
        addComponent(newNotificationDemo());
        addComponent(newPopupViewDemo());
        addComponent(newPanelDemo());
        addComponent(newContextMenuDemo());
    }

    private Component newPanelDemo() {
        final Panel panel = new Panel("New Panel");
        panel.setHeight("300px");
        final VerticalLayout content = new VerticalLayout();
        panel.setContent(content);
        for (int i = 0; i < 40; i++) {
            content.addComponent(new Label("Row " + i));
        }
        panel.addClickListener(e -> Notification.show("Panel clicked!"));
        return new ComponentDemo<>(panel)
                .controlStyles(ValoTheme.PANEL_BORDERLESS, ValoTheme.PANEL_SCROLL_INDICATOR, ValoTheme.PANEL_WELL)
                ;
    }

    private static @NotNull ComponentDemo<PopupView> newPopupViewDemo() {
        final PopupView popup = new PopupView("Hello <i>World</i>", new Label("Contents!"));
        popup.addPopupVisibilityListener(e -> Notification.show("Popup visible: " + e.isPopupVisible()));
        return new ComponentDemo<>(popup)
                .controlBool("Popup opened", PopupView::setPopupVisible)
                .controlBool("Hide on mouse out", PopupView::setHideOnMouseOut)
                ;
    }

    @NotNull
    private Component newContextMenuDemo() {
        final Button openMenuButton = new Button("Right-click to open context menu");
        final ContextMenu menu = new ContextMenu(openMenuButton, true);
        final MenuBar.MenuItem fileMenu = menu.addItem("File");
        fileMenu.addItem("Save", VaadinIcons.DISC, (MenuBar.Command) selectedItem -> Notification.show("Save"));
        fileMenu.addItem("Save as...", VaadinIcons.DISC, (MenuBar.Command) selectedItem -> Notification.show("Save as"));
        fileMenu.addSeparator();
        fileMenu.addItem("Quit").setDescription("Quits the application");
        fileMenu.addItem("Disabled", (MenuBar.Command) selectedItem -> Notification.show("Shouldn't be called!", Notification.Type.ERROR_MESSAGE)).setEnabled(false);
        fileMenu.addItem("Hidden", (MenuBar.Command) selectedItem -> Notification.show("Shouldn't be called!", Notification.Type.ERROR_MESSAGE)).setVisible(false);
        final MenuBar.MenuItem viewMenu = menu.addItem("View");
        final MenuBar.MenuItem toolbarMenu = viewMenu.addItem("Toolbars");
        final MenuBar.MenuItem menuBarMenu = toolbarMenu.addItem("Menu Bar");
        menuBarMenu.setCheckable(true);
        menuBarMenu.setChecked(true);
        final MenuBar.MenuItem fullScreen = viewMenu.addItem("Full screen");
        fullScreen.setCheckable(true);
        return new DemoPanel("Context Menu", openMenuButton);
    }

    @NotNull
    private Component newWindowDemo() {
        final Button openWindowButton = new Button("Open window", e -> UI.getCurrent().addWindow(new WindowDemo()));
        return new DemoPanel("Window", openWindowButton);
    }

    public static class WindowDemo extends Window {
        public WindowDemo() {
            final VerticalLayout content = new VerticalLayout();
            content.setMargin(false);
            content.addComponent(new ComponentDemo<>(this, false)
                    .controlBool("Modal", Window::setModal)
                    .controlBool("Resizable", true, Window::setResizable)
                    .controlBool("Closable", true, Window::setClosable)
                    .controlBool("Draggable", Window::setDraggable)
                    .controlButton("close", Window::close)
                    .controlButton("center", Window::center)
            );
            final Label resizeLabel = new Label("Resize listener");
            content.addComponent(resizeLabel);
            addResizeListener(e -> resizeLabel.setValue("Resize listener: " + e.getWindow().getPositionX() + "," + e.getWindow().getPositionY() + " " + e.getWindow().getWidth() + "x" + e.getWindow().getHeight()));
            setContent(content);
        }
    }

    @NotNull
    private Component newNotificationDemo() {
        final CheckBox hasIcon = new CheckBox("Icon");
        final TextField caption = new TextField("Caption");
        caption.setValue("Hello <b>world</b>!");
        final TextField description = new TextField("Description");
        description.setValue("");
        final CheckBox htmlContentAllowed = new CheckBox("HTML Content Allowed");
        final NativeSelect<Notification.Type> typeSelect = new NativeSelect<>("Type");
        typeSelect.setItems(Notification.Type.values());
        typeSelect.setEmptySelectionAllowed(false);
        typeSelect.setValue(Notification.Type.HUMANIZED_MESSAGE);
        final NativeSelect<Position> position = new NativeSelect<>("Position");
        position.setItems(Position.values());
        final Button button = new Button("Show", e -> {
            final Notification n = Notification.show(caption.getValue(), Utils.isNullOrBlank(description.getValue()) ? null : description.getValue(), typeSelect.getValue());
            n.setHtmlContentAllowed(htmlContentAllowed.getValue());
            if (position.getValue() != null) {
                n.setPosition(position.getValue());
            }
            n.setIcon(hasIcon.getValue() ? VaadinIcons.ABACUS : null);
        });
        return new DemoPanel("Notification", new HorizontalLayout(hasIcon, caption, description, htmlContentAllowed, typeSelect, position, button));
    }
}
