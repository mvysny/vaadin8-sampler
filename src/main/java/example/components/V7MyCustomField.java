package example.components;

import com.vaadin.ui.Component;
import com.vaadin.v7.ui.CheckBox;
import com.vaadin.v7.ui.CustomField;
import org.jetbrains.annotations.NotNull;

/**
 * Demoes {@link CustomField}
 */
public class V7MyCustomField extends CustomField<String> {
    @NotNull
    private final CheckBox yesNo = new CheckBox("Yes/No");

    public V7MyCustomField() {
        yesNo.addValueChangeListener(e -> setValue(yesNo.getValue() ? "Yes" : "No"));
    }

    @Override
    public Class<? extends String> getType() {
        return String.class;
    }

    @Override
    @NotNull
    protected Component initContent() {
        return yesNo;
    }

    @Override
    protected void setInternalValue(String newValue) {
        super.setInternalValue(newValue);
        yesNo.setValue("Yes".equals(newValue));
    }

    @Override
    public void clear() {
        setValue("No");
    }

    @Override
    public boolean isEmpty() {
        return !"Yes".equals(getInternalValue());
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        yesNo.setReadOnly(readOnly);
    }
}
