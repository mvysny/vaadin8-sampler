package example;

import com.github.mvysny.vaadinboot.VaadinBoot;

public class Main {
    public static void main(String[] args) throws Exception {
        // don't open the browser automatically: Vaadin-Boot doesn't really support
        // Vaadin 8 and can't detect the production mode; it would assume dev mode and would try to
        // open the browser even when running from Docker.
        new VaadinBoot().openBrowserInDevMode(false).run();
    }
}
