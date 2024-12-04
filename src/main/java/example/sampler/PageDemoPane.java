package example.sampler;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SuppressWarnings("deprecation")
public class PageDemoPane extends VerticalLayout implements View {
    public PageDemoPane() {
        setMargin(false);
        setId(getClass().getSimpleName());
        addComponent(pageOpenDemo());
        addComponent(pageLegacyOpenDemo());
    }

    @NotNull
    private Component pageOpenDemo() {
        final HorizontalLayout hl1 = new HorizontalLayout();
        final TextField url = new TextField("URL", "https://vaadin.com");
        final ComboBox<String> windowName = new ComboBox<>("Window name", Arrays.asList("_self", "_blank", "_top", "_parent"));
        final CheckBox tryToOpenAsPopup = new CheckBox("Try to open as popup", true);
        final Button pageOpen = new Button("Page.open(String url)", e -> Page.getCurrent().open(url.getValue(), windowName.getValue(), tryToOpenAsPopup.getValue()));
        hl1.addComponents(url, windowName, tryToOpenAsPopup, pageOpen);
        return new DemoPanel("Page.open()", hl1);
    }
    @NotNull
    private Component pageLegacyOpenDemo() {
        final HorizontalLayout hl2 = new HorizontalLayout();
        final ComboBox<String> windowName2 = new ComboBox<>("Window name", Arrays.asList("_self", "_blank", "_top", "_parent"));
        final CheckBox tryToOpenAsPopup2 = new CheckBox("Try to open as popup", true);
        final StreamResource res = new StreamResource(() -> new ByteArrayInputStream("hello!".getBytes(StandardCharsets.UTF_8)), "hello.txt");
        res.setMIMEType("text/plain");
        final Button pageOpen2 = new Button("Page.open(Resource resource)", e -> Page.getCurrent().open(res, windowName2.getValue(), tryToOpenAsPopup2.getValue()));
        hl2.addComponents(windowName2, tryToOpenAsPopup2, pageOpen2);
        return new DemoPanel("Page.open()", hl2);
    }
}
