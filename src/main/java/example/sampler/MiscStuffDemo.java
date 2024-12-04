package example.sampler;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

public class MiscStuffDemo extends VerticalLayout implements View {
    public MiscStuffDemo() {
        setMargin(false);
        setId(getClass().getSimpleName());
        addComponent(newLabelDemo());
        addComponent(newLinkDemo());
        addComponent(newUploadDemo());
        addComponent(new ComponentDemo<>(new ProgressBar())
                .controlBool("Progress 0.5", (pb, b) -> pb.setValue(b ? 0.5f : 0f))
                .controlBool("Indeterminate", ProgressBar::setIndeterminate)
                .controlStyle(ValoTheme.PROGRESSBAR_POINT)
        );
        addComponent(newMenuBarDemo());
        addComponent(newLoginViewDemo());
        addComponent(new ComponentDemo<>(new MyCustomComponent()));
        addComponent(new ComponentDemo<>(new Flash())); // demo unsupported component.
    }

    private static class MyCustomComponent extends CustomComponent {
        public MyCustomComponent() {
            final Label label = new Label("Custom!");
            label.setCaption("Custom Label"); // not shown! See CustomComponent for more details.
            setCompositionRoot(label);
        }
    }

    @NotNull
    private Component newLoginViewDemo() {
        return new ComponentDemo<>(new OpenLoginFormButton())
                .controlBool("Custom username caption", (lf, b) -> lf.customUsername = b)
                .controlBool("Custom password caption", (lf, b) -> lf.customPassword = b)
                .controlBool("Custom login button caption", (lf, b) -> lf.customLogin = b)
                ;
    }

    private static class OpenLoginFormButton extends Button {
        public boolean customUsername, customPassword, customLogin;

        public OpenLoginFormButton() {
            super("Open Window with Login form");
            addClickListener(e -> openLoginWindow());
        }

        private void openLoginWindow() {
            final Window loginWindow = new Window("Login Form");
            final LoginForm form = new LoginForm();
            if (customUsername) {
                form.setUsernameCaption("Custom username");
            }
            if (customPassword) {
                form.setPasswordCaption("Custom password");
            }
            if (customLogin) {
                form.setLoginButtonCaption("Custom login button");
            }
            form.addLoginListener(e -> Notification.show("Logged in: " + e.getLoginParameter("username") + ": " + e.getLoginParameter("password")));
            loginWindow.setContent(new ComponentDemo<>(form));
            UI.getCurrent().addWindow(loginWindow);
        }
    }

    @NotNull
    private Component newMenuBarDemo() {
        final MenuBar menu = new MenuBar();
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
        return new ComponentDemo<>(menu)
                .controlBool("Auto Open", MenuBar::setAutoOpen)
                .controlBool("HTML Content Allowed", MenuBar::setHtmlContentAllowed)
                .controlBool("Delay 4 seconds", (mb, b) -> mb.setDelayMs(b ? 4000 : 500))
                .controlStyles(ValoTheme.MENUBAR_SMALL, ValoTheme.MENUBAR_BORDERLESS)
                ;
    }

    @NotNull
    private Component newLabelDemo() {
        return new ComponentDemo<>(new Label("simple <b>testing</b>\nvalue"))
                .controlSelectEnum("Content Mode", ContentMode.TEXT, ContentMode.class, Label::setContentMode)
                .controlStyles(ValoTheme.LABEL_H1, ValoTheme.LABEL_H2, ValoTheme.LABEL_H3, ValoTheme.LABEL_H4, ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_TINY, ValoTheme.LABEL_SMALL, ValoTheme.LABEL_LARGE, ValoTheme.LABEL_HUGE, ValoTheme.LABEL_LIGHT, ValoTheme.LABEL_BOLD, ValoTheme.LABEL_COLORED, ValoTheme.LABEL_SUCCESS, ValoTheme.LABEL_FAILURE, ValoTheme.LABEL_SPINNER)
                ;
    }

    @NotNull
    private Component newLinkDemo() {
        return new ComponentDemo<>(new Link())
                .controlSelectEnum("Resource", ResourceTypeEnum.NONE, ResourceTypeEnum.class, (l, e) -> l.setResource(e.get()))
                .controlStyles(ValoTheme.LINK_SMALL, ValoTheme.LINK_LARGE)
                ;
    }

    @NotNull
    private Component newUploadDemo() {
        final Upload upload = new Upload("Upload component", (Upload.Receiver) (filename, mimeType) -> {
            System.out.println("Upload: received upload(" + filename + ", " + mimeType + ")");
            return new ByteArrayOutputStream();
        });
        upload.addChangeListener(e -> System.out.println("Upload: Changed: " + e.getFilename()));
        upload.addFailedListener(e -> {
            System.out.println("Upload: Failed: " + e.getReason());
            if (e.getReason() != null) {
                e.getReason().printStackTrace();
            }
        });
        upload.addFinishedListener(e -> System.out.println("Upload: Finished " + e.getClass() + ": " + e.getFilename() + ", " + e.getMIMEType() + ", length=" + e.getLength()));
        upload.addStartedListener(e -> System.out.println("Upload: Started: " + e.getFilename() + ", " + e.getMIMEType() + ", content_length=" + e.getContentLength()));
        upload.addProgressListener((readBytes, contentLength) -> System.out.println("Upload: Progress: " + readBytes + " of " + contentLength));
        upload.addSucceededListener(e -> System.out.println("Upload: Succeeded: " + e.getFilename() + ", " + e.getMIMEType() + ", length=" + e.getLength()));
        return new ComponentDemo<>(upload)
                .controlBool("Accept image/*", (u, b) -> u.setAcceptMimeTypes(b ? "image/*" : null))
                .controlBool("Button caption", true, (u, b) -> u.setButtonCaption(b ? "My Upload <b>bold</b>" : null))
                .controlBool("Button caption as HTML", Upload::setButtonCaptionAsHtml)
                .controlBool("Immediate", true, Upload::setImmediateMode)
                .controlButton("Submit Upload", Upload::submitUpload)
                ;
    }
}
