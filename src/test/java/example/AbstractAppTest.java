package example;

import com.github.mvysny.kaributesting.v8.MockVaadin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class AbstractAppTest {
    @BeforeEach
    public void fakeVaadin() {
        MockVaadin.setup(MyVaadin8UI::new);
    }
    @AfterEach
    public void tearDownVaadin() {
        MockVaadin.tearDown();
    }
}
