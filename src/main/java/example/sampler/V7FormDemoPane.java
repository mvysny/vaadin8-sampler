package example.sampler;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.v7.data.fieldgroup.FieldGroup;
import com.vaadin.v7.data.util.BeanItem;
import com.vaadin.v7.data.validator.BeanValidator;
import com.vaadin.v7.ui.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

@SuppressWarnings("deprecation")
public class V7FormDemoPane extends VerticalLayout implements View {
    public V7FormDemoPane() {
        setId(getClass().getSimpleName());
        setMargin(false);

        addComponent(new DemoPanel("Form", new PersonForm()));
        addComponent(new DemoPanel("FieldGroup", new PersonFieldGroup()));
    }

    /**
     * Demoes the ancient approach using {@link Form}.
     */
    public static class PersonForm extends VerticalLayout {

        @NotNull
        private final BeanItem<FormDemoPane.Person> item = new BeanItem<>(new FormDemoPane.Person());
        @NotNull
        @VisibleForTesting
        public final Form form = new Form();

        public PersonForm() {
            setId(getClass().getSimpleName());

            addComponent(form);
            form.setItemDataSource(item);
            form.getField("name").addValidator(new BeanValidator(FormDemoPane.Person.class, "name"));
            ((AbstractTextField) form.getField("name")).setNullRepresentation("");
            form.getField("yearOfBirth").addValidator(new BeanValidator(FormDemoPane.Person.class, "yearOfBirth"));
            ((AbstractTextField) form.getField("yearOfBirth")).setNullRepresentation("");

            addComponent(new Button("Save", e -> save()));
            addComponent(new Button("Reset", e -> form.clear()));
        }

        private void save() {
            if (form.isValid()) {
                Notification.show("Person created: " + item.getBean());
            } else {
                Notification.show("There are errors in the form", Notification.Type.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Demoes the old approach of using {@link FieldGroup}.
     */
    public static class PersonFieldGroup extends VerticalLayout {
        @NotNull
        private final BeanItem<FormDemoPane.Person> item = new BeanItem<>(new FormDemoPane.Person());
        @NotNull
        @VisibleForTesting
        public final FieldGroup group = new FieldGroup(item);

        public PersonFieldGroup() {
            setId(getClass().getSimpleName());
            group.setBuffered(false);

            final TextField nameField = (TextField) group.buildAndBind("Name", "name");
            nameField.setNullRepresentation("");
            nameField.addValidator(new BeanValidator(FormDemoPane.Person.class, "name"));
            addComponent(nameField);

            final TextField yearOfBirthField = (TextField) group.buildAndBind("Year Of Birth", "yearOfBirth");
            yearOfBirthField.setNullRepresentation("");
            yearOfBirthField.addValidator(new BeanValidator(FormDemoPane.Person.class, "yearOfBirth"));
            addComponent(yearOfBirthField);


            addComponent(new Button("Save", e -> save()));
            addComponent(new Button("Reset", e -> group.clear()));
        }

        private void save() {
            if (group.isValid()) {
                Notification.show("Person created: " + item.getBean());
            } else {
                Notification.show("There are errors in the form", Notification.Type.ERROR_MESSAGE);
            }
        }
    }
}
