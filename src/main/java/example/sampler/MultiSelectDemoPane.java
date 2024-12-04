package example.sampler;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MultiSelectDemoPane extends VerticalLayout implements View {
    public MultiSelectDemoPane() {
        setMargin(false);
        setId(getClass().getSimpleName());
        addComponent(newCheckBoxGroupDemo());
        addComponent(newTwinColSelectDemo());
        addComponent(newListSelectDemo());
    }

    @NotNull
    private Component newCheckBoxGroupDemo() {
        return new ComponentDemo<CheckBoxGroup<String>>(new CheckBoxGroup<>("", Arrays.asList("1", "2", "3", "4")))
                .controlBool("Item Icons", (cb, c) -> cb.setItemIconGenerator(item -> c ? VaadinIcons.ABACUS : null))
                .controlBool("Item Captions as HTML", CheckBoxGroup::setHtmlContentAllowed)
                .controlBool("Items disabled", (cb, c) -> cb.setItemEnabledProvider(item -> !c))
                .controlBool("Items have description", (cb, c) -> cb.setItemDescriptionGenerator(item -> c ? "Tooltip " + item : null))
                .controlStyles(ValoTheme.OPTIONGROUP_SMALL, ValoTheme.OPTIONGROUP_LARGE, ValoTheme.OPTIONGROUP_HORIZONTAL)
                ;
    }

    @NotNull
    private Component newTwinColSelectDemo() {
        return new ComponentDemo<>(new TwinColSelect<>("", Arrays.asList("1", "2", "3", "4")))
                .controlBool("5 Rows", (cb, c) -> cb.setRows(c ? 5 : 0))
                .controlBool("Left Column Caption", (cb, c) -> cb.setLeftColumnCaption(c ? "left col cap" : null))
                .controlBool("Right Column Caption", (cb, c) -> cb.setLeftColumnCaption(c ? "right col cap" : null))
                ;
    }

    @NotNull
    private Component newListSelectDemo() {
        return new ComponentDemo<>(new ListSelect<>("", Arrays.asList("1", "2", "3", "4")))
                .controlBool("5 Rows", (cb, c) -> cb.setRows(c ? 5 : 0))
                ;
    }
}
