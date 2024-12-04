package example.sampler;

import com.vaadin.ui.UI;
import example.AbstractAppTest;
import org.junit.jupiter.api.Test;

import static com.github.mvysny.kaributesting.v8.LocatorJ._assertOne;
import static org.junit.jupiter.api.Assertions.*;

class ButtonDemoPaneTest extends AbstractAppTest {
    @Test
    public void smoke() {
        UI.getCurrent().getNavigator().navigateTo("buttons");
        _assertOne(ButtonDemoPane.class);
    }
}