package example.sampler;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.v7.shared.ui.combobox.FilteringMode;
import com.vaadin.v7.ui.*;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class V7SelectsDemoPane extends VerticalLayout implements View {
    public V7SelectsDemoPane() {
        setId(getClass().getSimpleName());
        setMargin(false);
        addComponent(new ComponentDemo<>(new NativeSelect("", DemoBean.generateContainer(100))));
        addComponent(newComboBoxDemo());
        addComponent(newSelect());
        addComponent(new ComponentDemo<>(new ListSelect("", DemoBean.generateContainer(100))));
        addComponent(newOptionGroupDemo());
        addComponent(newTwinColSelectDemo());
        addComponent(newTreeDemo());
    }

    @NotNull
    private Component newTreeDemo() {
        final Tree tree = new Tree("", DemoBean.generateHierarchicalContainer(100));
        tree.addValueChangeListener(e -> Notification.show("selected: " + e.getProperty().getValue()));
        tree.addItemClickListener(e -> Notification.show("clicked: " + e.getItem()));
        tree.addContextClickListener(e -> Notification.show("context-clicked: " + e.getMouseEventDetails()));
        tree.setHeight("400px");
        return new ComponentDemo<>(tree)
                .controlSelectEnum("MultiSelect Mode", MultiSelectMode.DEFAULT, MultiSelectMode.class, Tree::setMultiselectMode)
                .controlSelectEnum("Drag Mode", Tree.TreeDragMode.NONE, Tree.TreeDragMode.class, Tree::setDragMode)
                .controlBool("HTML Content Allowed", false, Tree::setHtmlContentAllowed)
        ;
    }

    private static @NotNull ComponentDemo<TwinColSelect> newTwinColSelectDemo() {
        return new ComponentDemo<>(new TwinColSelect("", DemoBean.generateContainer(100)))
                .controlBool("Columns=20", false, (s, b) -> s.setColumns(b ? 20 : 0))
                .controlBool("Rows=20", false, (s, b) -> s.setRows(b ? 20 : 0))
                .controlBool("left column caption", false, (s, b) -> s.setLeftColumnCaption(b ? "Look!" : ""))
                .controlBool("right column caption", false, (s, b) -> s.setRightColumnCaption(b ? "Look Right!" : ""))
                ;
    }

    private static @NotNull ComponentDemo<OptionGroup> newOptionGroupDemo() {
        return new ComponentDemo<>(new OptionGroup("", DemoBean.generateContainer(100)))
                .controlBool("HTML Content allowed", false, OptionGroup::setHtmlContentAllowed)
                .controlBool("First item enabled", true, (cb, b) -> cb.setItemEnabled(0L, b))
                ;
    }

    private static @NotNull ComponentDemo<ComboBox> newComboBoxDemo() {
        return new ComponentDemo<>(new ComboBox("", DemoBean.generateContainer(100)))
                .controlBool("Input Prompt", false, (cb, b) -> cb.setInputPrompt(b ? "Demo input prompt" : null))
                .controlBool("Text Input Allowed", true, ComboBox::setTextInputAllowed)
                .controlSelectEnum("Filtering Mode", FilteringMode.STARTSWITH, FilteringMode.class, ComboBox::setFilteringMode)
                .controlBool("Scroll to selected item", true, ComboBox::setScrollToSelectedItem)
                .controlBool("Popup width 200px", false, (cb, c) -> cb.setPopupWidth(c ? "200px" : null))
                ;
    }
    private static @NotNull ComponentDemo<Select> newSelect() {
        return new ComponentDemo<>(new Select("", DemoBean.generateContainer(100)))
                .controlBool("Input Prompt", false, (cb, b) -> cb.setInputPrompt(b ? "Demo input prompt" : null))
                .controlBool("Text Input Allowed", true, ComboBox::setTextInputAllowed)
                .controlSelectEnum("Filtering Mode", FilteringMode.STARTSWITH, FilteringMode.class, ComboBox::setFilteringMode)
                .controlBool("Scroll to selected item", true, ComboBox::setScrollToSelectedItem)
                .controlBool("Popup width 200px", false, (cb, c) -> cb.setPopupWidth(c ? "200px" : null))
                ;
    }
}
