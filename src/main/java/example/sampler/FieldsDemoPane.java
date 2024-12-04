package example.sampler;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import example.components.MyCustomField;

public class FieldsDemoPane extends VerticalLayout implements View {
    public FieldsDemoPane() {
        setId(getClass().getSimpleName());
        setMargin(false);
        addComponent(new ComponentDemo<>(new TextField())
                .controlStyles(ValoTheme.TEXTFIELD_TINY, ValoTheme.TEXTFIELD_SMALL, ValoTheme.TEXTFIELD_LARGE, ValoTheme.TEXTFIELD_HUGE, ValoTheme.TEXTFIELD_BORDERLESS, ValoTheme.TEXTFIELD_ALIGN_RIGHT, ValoTheme.TEXTFIELD_ALIGN_CENTER, ValoTheme.TEXTFIELD_INLINE_ICON)
        );
        addComponent(new ComponentDemo<>(new PasswordField())
                .controlStyles(ValoTheme.TEXTFIELD_TINY, ValoTheme.TEXTFIELD_SMALL, ValoTheme.TEXTFIELD_LARGE, ValoTheme.TEXTFIELD_HUGE, ValoTheme.TEXTFIELD_BORDERLESS, ValoTheme.TEXTFIELD_ALIGN_RIGHT, ValoTheme.TEXTFIELD_ALIGN_CENTER, ValoTheme.TEXTFIELD_INLINE_ICON)
        );
        addComponent(new ComponentDemo<>(new TextArea())
                .controlBool("Word Wrap", TextArea::setWordWrap)
                .controlBool("10 Rows tall", (ta, b) -> ta.setRows(b ? 10 : 5))
                .controlStyles(ValoTheme.TEXTAREA_TINY, ValoTheme.TEXTAREA_SMALL, ValoTheme.TEXTAREA_LARGE, ValoTheme.TEXTAREA_HUGE, ValoTheme.TEXTAREA_BORDERLESS, ValoTheme.TEXTAREA_ALIGN_RIGHT, ValoTheme.TEXTAREA_ALIGN_CENTER)
        );
        addComponent(new ComponentDemo<>(new RichTextArea())
                .controlButton("selectAll()", RichTextArea::selectAll)
        );
        addComponent(new ComponentDemo<>(new CheckBox())
                .controlStyles(ValoTheme.CHECKBOX_SMALL, ValoTheme.CHECKBOX_LARGE)
        );
        addComponent(new ComponentDemo<>(new DateField())
                .controlStyles(ValoTheme.DATEFIELD_TINY, ValoTheme.DATEFIELD_SMALL, ValoTheme.DATEFIELD_LARGE, ValoTheme.DATEFIELD_HUGE, ValoTheme.DATEFIELD_BORDERLESS, ValoTheme.DATEFIELD_ALIGN_RIGHT, ValoTheme.DATEFIELD_ALIGN_CENTER)
        );
        addComponent(new ComponentDemo<>(new InlineDateField()));
        addComponent(new ComponentDemo<>(new DateTimeField()));
        addComponent(new ComponentDemo<>(new InlineDateTimeField()));
        addComponent(new ComponentDemo<>(new Slider(0, 100, 0))
                .controlBool("Resolution = 1", (c, b) -> c.setResolution(b ? 1 : 0))
                .controlSelectEnum("Orientation", SliderOrientation.HORIZONTAL, SliderOrientation.class, Slider::setOrientation)
                .controlStyle(ValoTheme.SLIDER_NO_INDICATOR)
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
                .controlBool("Popup: Modal", false, AbstractColorPicker::setModal)
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
                .controlBool("Popup: Modal", false, AbstractColorPicker::setModal)
        );
        addComponent(new ComponentDemo<>(new MyCustomField()));
    }
}
