package example.sampler;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.v7.ui.Upload;
import com.vaadin.v7.ui.VerticalLayout;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

@SuppressWarnings("deprecation")
public class V7MiscStuffDemo extends VerticalLayout implements View {
    public V7MiscStuffDemo() {
        setMargin(false);
        setId(getClass().getSimpleName());

        addComponent(newUploadDemo());
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
                .controlBool("Button caption", true, (u, b) -> u.setButtonCaption(b ? "My Upload" : "Upload"))
                .controlButton("Submit Upload", Upload::submitUpload)
                ;
    }
}
