package example.sampler;

import com.vaadin.navigator.View;
import com.vaadin.v7.shared.ui.datefield.Resolution;
import com.vaadin.v7.shared.ui.slider.SliderOrientation;
import com.vaadin.v7.ui.*;
import example.components.V7MyCustomField;

import java.util.Date;

@SuppressWarnings("deprecation")
public class V7FieldsDemoPane extends VerticalLayout implements View {
    public V7FieldsDemoPane() {
        setId(getClass().getSimpleName());
        setMargin(false);
        addComponent(new ComponentDemo<>(new TextField()));
        addComponent(new ComponentDemo<>(new PasswordField()));
        addComponent(new ComponentDemo<>(new TextArea())
                .controlBool("Word Wrap", TextArea::setWordwrap)
                .controlBool("10 Rows tall", (ta, b) -> ta.setRows(b ? 3 : 5))
        );
        addComponent(new ComponentDemo<>(new RichTextArea())
                .controlButton("selectAll()", RichTextArea::selectAll)
        );
        addComponent(new ComponentDemo<>(new PopupDateField())
                .controlSelectEnum("Resolution", Resolution.DAY, Resolution.class, DateField::setResolution)
                .controlButton("Set current date", c -> c.setValue(new Date()))
                .controlBool("Input Prompt", (c, b) -> c.setInputPrompt(b ? "Ha!" : null))
                .controlBool("Text field enabled", true, PopupDateField::setTextFieldEnabled)
                .controlBool("Assistive Text", (c, b) -> c.setAssistiveText(b ? "Assistive Text Demo!" : null))
        );
        addComponent(new ComponentDemo<>(new InlineDateField())
                .controlSelectEnum("Resolution", Resolution.DAY, Resolution.class, DateField::setResolution)
                .controlButton("Set current date", c -> c.setValue(new Date()))
        );
        addComponent(new ComponentDemo<>(new Slider(0, 100, 0))
                .controlBool("Resolution = 1", (c, b) -> c.setResolution(b ? 1 : 0))
                .controlSelectEnum("Orientation", SliderOrientation.HORIZONTAL, SliderOrientation.class, Slider::setOrientation)
        );
        addComponent(new ComponentDemo<>(new ProgressBar())
                .controlBool("Indeterminate", false, ProgressBar::setIndeterminate)
                .controlButton("Set value to 0.5f", c -> c.setValue(0.5f))
        );
        addComponent(new ComponentDemo<>(new ColorPicker("Pick a color that you like"))
                .controlBool("Default caption enabled", AbstractColorPicker::setDefaultCaptionEnabled)
                .controlSelectEnum("Popup Style", AbstractColorPicker.PopupStyle.POPUP_NORMAL, AbstractColorPicker.PopupStyle.class, AbstractColorPicker::setPopupStyle)
                .controlBool("RGB Visibility", true, AbstractColorPicker::setRGBVisibility)
                .controlBool("HSV Visibility", true, AbstractColorPicker::setHSVVisibility)
                .controlBool("Swatches Visibility", true, AbstractColorPicker::setSwatchesVisibility)
                .controlBool("History Visibility", true, AbstractColorPicker::setHistoryVisibility)
                .controlBool("TextField Visibility", true, AbstractColorPicker::setTextfieldVisibility)
                .controlButton("Show Popup", AbstractColorPicker::showPopup)
                .controlButton("Hide Popup", AbstractColorPicker::hidePopup)
        );
        addComponent(new ComponentDemo<>(new ColorPickerArea("Pick a color that you like"))
                .controlBool("Default caption enabled", AbstractColorPicker::setDefaultCaptionEnabled)
                .controlSelectEnum("Popup Style", AbstractColorPicker.PopupStyle.POPUP_NORMAL, AbstractColorPicker.PopupStyle.class, AbstractColorPicker::setPopupStyle)
                .controlBool("RGB Visibility", true, AbstractColorPicker::setRGBVisibility)
                .controlBool("HSV Visibility", true, AbstractColorPicker::setHSVVisibility)
                .controlBool("Swatches Visibility", true, AbstractColorPicker::setSwatchesVisibility)
                .controlBool("History Visibility", true, AbstractColorPicker::setHistoryVisibility)
                .controlBool("TextField Visibility", true, AbstractColorPicker::setTextfieldVisibility)
                .controlButton("Show Popup", AbstractColorPicker::showPopup)
                .controlButton("Hide Popup", AbstractColorPicker::hidePopup)
        );
        addComponent(new ComponentDemo<>(new V7MyCustomField()));
    }
}
