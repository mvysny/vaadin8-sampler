package example.sampler;

import com.vaadin.data.HasValue;
import com.vaadin.data.SelectionModel;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.Registration;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.components.grid.SingleSelectionModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adapts Grid and Tree to the same API. Not intended to be used with Vaadin 7 AbstractSelect!
 * @param <T> the item type
 */
public abstract class IsSingleAndMultiSelect<T> {
    public abstract void setSelectionMode(@NotNull Grid.SelectionMode selectionMode);
    @NotNull
    public abstract Grid.SelectionMode getSelectionMode();
    @NotNull
    public abstract Registration addSelectionListener(@NotNull SelectionListener<T> listener);
    @NotNull
    public abstract SingleSelect<T> asSingleSelect();
    @NotNull
    public abstract MultiSelect<T> asMultiSelect();
    @NotNull
    public HasValue<?> getHasValue() {
        switch (getSelectionMode()) {
            case SINGLE: return asSingleSelect();
            case MULTI: return asMultiSelect();
            default: return new HasValue<Object>() {
                @Override
                public void setValue(Object value) {
                    Notification.show("SelectionMode is NONE, can not setValue(" + value + ")", Notification.Type.ERROR_MESSAGE);
                }

                @Override
                public Object getValue() {
                    return null;
                }

                @Override
                public Registration addValueChangeListener(ValueChangeListener<Object> listener) {
                    return () -> {};
                }

                @Override
                public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
                }

                @Override
                public boolean isRequiredIndicatorVisible() {
                    return false;
                }

                @Override
                public void setReadOnly(boolean readOnly) {
                    Notification.show("SelectionMode is NONE, can not setReadOnly(" + readOnly + ")", Notification.Type.ERROR_MESSAGE);
                }

                @Override
                public boolean isReadOnly() {
                    return false;
                }
            };
        }
    }

    @NotNull
    public static Grid.SelectionMode getSelectionMode(@NotNull SelectionModel<?> model) {
        return model instanceof SingleSelectionModel ? Grid.SelectionMode.SINGLE :
                model instanceof MultiSelectionModel ? Grid.SelectionMode.MULTI : Grid.SelectionMode.NONE;
    }

    @Nullable
    public static <T> IsSingleAndMultiSelect<T> of(@NotNull Component component) {
        if (component instanceof Grid<?>) {
            final Grid<T> grid = (Grid<T>) component;
            return new IsSingleAndMultiSelect<T>() {
                @Override
                public void setSelectionMode(Grid.@NotNull SelectionMode selectionMode) {
                    grid.setSelectionMode(selectionMode);
                }

                @Override
                public Grid.@NotNull SelectionMode getSelectionMode() {
                    return getSelectionMode(grid.getSelectionModel());
                }

                @Override
                public @NotNull Registration addSelectionListener(@NotNull SelectionListener<T> listener) {
                    return grid.addSelectionListener(listener);
                }

                @Override
                public @NotNull SingleSelect<T> asSingleSelect() {
                    return grid.asSingleSelect();
                }

                @Override
                public @NotNull MultiSelect<T> asMultiSelect() {
                    return grid.asMultiSelect();
                }
            };
        }
        if (component instanceof Tree<?>) {
            final Tree<T> tree = (Tree<T>) component;
            return new IsSingleAndMultiSelect<T>() {
                @Override
                public void setSelectionMode(Grid.@NotNull SelectionMode selectionMode) {
                    tree.setSelectionMode(selectionMode);
                }

                @Override
                public Grid.@NotNull SelectionMode getSelectionMode() {
                    return getSelectionMode(tree.getSelectionModel());
                }

                @Override
                public @NotNull Registration addSelectionListener(@NotNull SelectionListener<T> listener) {
                    return tree.addSelectionListener(listener);
                }

                @Override
                public @NotNull SingleSelect<T> asSingleSelect() {
                    return tree.asSingleSelect();
                }

                @Override
                public @NotNull MultiSelect<T> asMultiSelect() {
                    return tree.asMultiSelect();
                }
            };
        }
        return null;
    }
}
