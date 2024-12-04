package example;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);
    @NotNull
    public static Throwable getRootCause(@NotNull Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    public static boolean isNullOrBlank(@Nullable String str) {
        return str == null || str.trim().isEmpty();
    }

    @NotNull
    public static String getVaadinVersion() {
        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try {
            try(InputStream is = cl.getResourceAsStream("META-INF/maven/com.vaadin/vaadin-server/pom.properties")) {
                properties.load(is);
            }
        } catch (IOException e) {
            log.error("Can't determine Vaadin version", e);
            return "?";
        }
        return properties.getProperty("version");
    }
}
