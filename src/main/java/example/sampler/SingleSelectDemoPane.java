package example.sampler;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SingleSelectDemoPane extends VerticalLayout implements View {
    public SingleSelectDemoPane() {
        setMargin(false);
        setId(getClass().getSimpleName());
        addComponent(newComboBoxDemo());
        addComponent(newNativeSelectDemo());
        addComponent(newRadioButtonGroupDemo());
    }

    @NotNull
    private Component newComboBoxDemo() {
        return new ComponentDemo<>(new ComboBox<>("", Arrays.asList("1", "2", "3")))
                .controlBool("Placeholder", (cb, c) -> cb.setPlaceholder(c ? "placeholder" : ""))
                .controlBool("Text input allowed", ComboBox::setTextInputAllowed)
                .controlBool("Empty selection allowed", true, ComboBox::setEmptySelectionAllowed)
                .controlBool("Empty selection caption", (cb, c) -> cb.setEmptySelectionCaption(c ? "empty selection" : ""))
                .controlBool("Popup width", (cb, c) -> cb.setPopupWidth(c ? "400px" : "100%"))
                .controlBool("Scroll to selected item", (cb, c) -> cb.setScrollToSelectedItem(c))
                .controlBool("Item Captions", (cb, c) -> cb.setItemCaptionGenerator(item -> c ? "Value is <b>" + item + "</b>" : "" + item))
                .controlBool("Item Style", (cb, c) -> cb.setStyleGenerator(item -> c ? "myitemstyle" : null))
                .controlBool("Item Icons", (cb, c) -> cb.setItemIconGenerator(item -> c ? VaadinIcons.ABACUS : null))
                .controlButton("Set Items (should preserve value)", cb -> cb.setItems(Arrays.asList("1", "2", "3")))
                .controlStyles(ValoTheme.COMBOBOX_TINY, ValoTheme.COMBOBOX_SMALL, ValoTheme.COMBOBOX_LARGE, ValoTheme.COMBOBOX_HUGE, ValoTheme.COMBOBOX_BORDERLESS, ValoTheme.COMBOBOX_ALIGN_RIGHT, ValoTheme.COMBOBOX_ALIGN_CENTER)
                ;

        // @todo new item handler
    }

    @NotNull
    private Component newNativeSelectDemo() {
        return new ComponentDemo<>(new NativeSelect<>("", Arrays.asList("1", "2", "3")))
                .controlBool("Empty selection allowed", true, NativeSelect::setEmptySelectionAllowed)
                .controlBool("Empty selection caption", (cb, c) -> cb.setEmptySelectionCaption(c ? "empty selection" : ""))
                .controlBool("Item Captions", (cb, c) -> cb.setItemCaptionGenerator(item -> c ? "Value is <b>" + item + "</b>" : "" + item))
                .controlBool("3 Visible Item Count", (cb, c) -> cb.setVisibleItemCount(c ? 3 : 1))
                .controlButton("Set Items (should preserve value)", cb -> cb.setItems(Arrays.asList("1", "2", "3")))
                ;
    }

    @NotNull
    private Component newRadioButtonGroupDemo() {
        return new ComponentDemo<>(new RadioButtonGroup<>("", Arrays.asList("1", "2", "3")))
                .controlBool("HTML Content Allowed", (c, b) -> c.setHtmlContentAllowed(b))
                .controlBool("Item Captions", (cb, c) -> cb.setItemCaptionGenerator(item -> c ? "Value is <b>" + item + "</b>" : "" + item))
                .controlBool("Item Icons", (cb, c) -> cb.setItemIconGenerator(item -> c ? VaadinIcons.ABACUS : null))
                .controlBool("Items Enabled", true, (cb, c) -> cb.setItemEnabledProvider(item -> c))
                .controlBool("Item Description", (cb, c) -> cb.setItemDescriptionGenerator(item -> c ? "Tooltip " + item : ""))
                .controlButton("Set Items (should clear value)", cb -> cb.setItems(Arrays.asList("1", "2", "3")))
                .controlStyles(ValoTheme.OPTIONGROUP_SMALL, ValoTheme.OPTIONGROUP_LARGE, ValoTheme.OPTIONGROUP_HORIZONTAL)
                ;
    }
}
