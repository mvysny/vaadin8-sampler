package example.sampler;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public enum ResourceTypeEnum implements Supplier<Resource> {
    NONE {
        @Override
        public Resource get() {
            return null;
        }
    },
    EXTERNAL_RESOURCE {
        @Override
        public Resource get() {
            return new ExternalResource("https://vaadin.com");
        }
    },
    STREAM_RESOURCE {
        @Override
        public Resource get() {
            final StreamResource res = new StreamResource(() -> new ByteArrayInputStream("hello!".getBytes(StandardCharsets.UTF_8)), "hello.txt");
            res.setMIMEType("text/plain");
            return res;
        }
    },
    ICON_RESOURCE {
        @Override
        public Resource get() {
            return VaadinIcons.ABACUS;
        }
    };
}
