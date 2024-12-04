package example;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.mvysny.kaributesting.v8.LocatorJ.*;

public class MyVaadin8UITest extends AbstractAppTest {
    @Test
    public void smoke() {
        // navigate to all views
        final List<Button> navButtons = _find(_get(NavMenu.class), Button.class);
        for (Button button : navButtons) {
            _click(button);
        }
    }
}
