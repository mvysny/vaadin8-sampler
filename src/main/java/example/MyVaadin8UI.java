package example;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import example.sampler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;

// use the AppWidgetset which includes the ContextMenu
//@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class MyVaadin8UI extends UI {
    private static final Logger log = LoggerFactory.getLogger(MyVaadin8UI.class);
    @Override
    protected void init(VaadinRequest request) {
        VaadinSession.getCurrent().setErrorHandler((ErrorHandler) event -> {
            log.error("Internal error", event.getThrowable());
            if (UI.getCurrent() != null) {
                Notification.show(Utils.getRootCause(event.getThrowable()).toString(), Notification.Type.ERROR_MESSAGE);
            }
        });

        final Panel viewDisplay = new Panel();
        viewDisplay.setSizeFull();
        setNavigator(new Navigator(this, viewDisplay));
        final NavMenu navMenu = new NavMenu();
        navMenu.addView("Misc", "", MiscStuffDemo.class);
        navMenu.addView("Buttons", "buttons", ButtonDemoPane.class);
        navMenu.addView("Fields", "fields", FieldsDemoPane.class);
        navMenu.addView("Forms", "forms", FormDemoPane.class);
        navMenu.addView("Single-selects", "singleselects", SingleSelectDemoPane.class);
        navMenu.addView("Multi-selects", "multiselects", MultiSelectDemoPane.class);
        navMenu.addView("Layouts", "layouts", LayoutsDemoPane.class);
        navMenu.addView("Popup/Window/Panel", "popup", PopupDemoPane.class);
        navMenu.addView("Grid/Tree", "grid", GridDemoPane.class);
        navMenu.addView("Page", "page", PageDemoPane.class);
        navMenu.addView("Playground", "playground", Playground.class);
        navMenu.addView("V7: Fields", "v7fields", V7FieldsDemoPane.class);
        navMenu.addView("V7: Forms", "v7forms", V7FormDemoPane.class);
        navMenu.addView("V7: Selects", "v7selects", V7SelectsDemoPane.class);
        navMenu.addView("V7: Grid", "v7grid", V7GridDemoPane.class);
        navMenu.addView("V7: Table", "v7table", V7TableDemoPane.class);
        navMenu.addView("V7: Misc", "v7misc", V7MiscStuffDemo.class);
        final HorizontalLayout content = new HorizontalLayout(navMenu);
        content.addComponentsAndExpand(viewDisplay);
        content.setSizeFull();
        setContent(content);
    }

    @VaadinServletConfiguration(ui = MyVaadin8UI.class, productionMode = false)
    @WebServlet(urlPatterns = {"/*"})
    public static class MyServlet extends VaadinServlet {}
}
