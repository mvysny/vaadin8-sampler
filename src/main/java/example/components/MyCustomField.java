package example.components;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import org.jetbrains.annotations.NotNull;

/**
 * Demoes {@link CustomField}
 */
public class MyCustomField extends CustomField<String> {
    @NotNull
    private final CheckBox yesNo = new CheckBox("Yes/No");

    @NotNull
    private String value = "No";

    public MyCustomField() {
        yesNo.addValueChangeListener(e -> setValue(yesNo.getValue() ? "Yes" : "No", true));
    }

    @Override
    @NotNull
    protected Component initContent() {
        return yesNo;
    }

    @Override
    protected void doSetValue(@NotNull String value) {
        this.value = value;
        yesNo.setValue("Yes".equals(value));
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getEmptyValue() {
        return "No";
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        yesNo.setReadOnly(readOnly);
    }
}
