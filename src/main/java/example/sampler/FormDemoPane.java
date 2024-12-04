package example.sampler;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SuppressWarnings("deprecation")
public class FormDemoPane extends VerticalLayout implements View {

    /**
     * A demo bean which we'll edit.
     */
    public static class Person implements Serializable {
        @NotNull
        @Size(min = 3)
        private String name;

        @NotNull
        @Min(1900)
        private Integer yearOfBirth;

        public Person(String name, int yearOfBirth) {
            this.name = name;
            this.yearOfBirth = yearOfBirth;
        }

        public Person() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getYearOfBirth() {
            return yearOfBirth;
        }

        public void setYearOfBirth(Integer yearOfBirth) {
            this.yearOfBirth = yearOfBirth;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", yearOfBirth=" + yearOfBirth +
                    '}';
        }
    }

    public static class PersonForm extends VerticalLayout {
        public final Binder<Person> binder = new BeanValidationBinder<>(Person.class);

        public PersonForm() {
            setId(getClass().getSimpleName());
            setMargin(false);

            final FormLayout formLayout = new FormLayout();
            addComponent(formLayout);
            final TextField nameField = new TextField("Name");
            nameField.setId("nameField");
            binder.forField(nameField)
                    .withNullRepresentation("")
                    .bind("name");
            formLayout.addComponent(nameField);

            final TextField yearOfBirthField = new TextField("Year of birth");
            yearOfBirthField.setId("yearOfBirthField");
            binder.forField(yearOfBirthField)
                    .withNullRepresentation("")
                    .withConverter(new StringToIntegerConverter("not a number"))
                    .bind("yearOfBirth");
            formLayout.addComponent(yearOfBirthField);

            addComponent(new Button("Save", e -> save()));
            addComponent(new Button("Reset", e -> binder.readBean(new Person())));
        }

        private void save() {
            final Person person = new Person();
            if (binder.validate().isOk() && binder.writeBeanIfValid(person)) {
                Notification.show("Person created: " + person);
            } else {
                Notification.show("There are errors in the form", Notification.Type.ERROR_MESSAGE);
            }
        }
    }

    public FormDemoPane() {
        setId(getClass().getSimpleName());
        setMargin(false);

        addComponent(new DemoPanel("Person form", new PersonForm()));
    }
}
