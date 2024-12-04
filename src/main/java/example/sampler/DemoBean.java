package example.sampler;

import com.vaadin.v7.data.Container;
import com.vaadin.v7.data.Item;
import com.vaadin.v7.data.util.BeanContainer;
import com.vaadin.v7.data.util.HierarchicalContainer;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("deprecation")
public final class DemoBean implements Serializable {
    private long id;
    private String name;
    private String surname;
    private int age;
    private Date birthday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemoBean)) return false;
        DemoBean demoBean = (DemoBean) o;
        return id == demoBean.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DemoBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }

    public DemoBean() {
    }

    public DemoBean(long id, String name, String surname, int age, Date birthday) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.birthday = birthday;
    }

    @SuppressWarnings("unchecked")
    public void fill(@NotNull Item item) {
        item.getItemProperty("id").setValue(id);
        item.getItemProperty("name").setValue(name);
        item.getItemProperty("surname").setValue(surname);
        item.getItemProperty("age").setValue(age);
        item.getItemProperty("birthday").setValue(birthday);
    }

    @NotNull
    public static List<DemoBean> generate(int count) {
        return generate(0, count);
    }

    @NotNull
    public static List<DemoBean> generate(int start, int count) {
        return IntStream.range(start, start + count).mapToObj(it -> new DemoBean(it, "Name " + it, "Surname " + it, it, new Date(System.currentTimeMillis() + it * 24L * 60 * 60 * 1000))).collect(Collectors.toList());
    }

    /**
     * Creates a demo container containing a bunch of {@link DemoBean}s.
     * The itemIDs will be Long values starting from 0.
     * @param count the number of beans to generate.
     * @return the container.
     */
    @NotNull
    public static Container.Indexed generateContainer(int count) {
        final List<DemoBean> beans = generate(count);
        beans.add(0, new DemoBean(beans.stream().map(DemoBean::getId).max(Long::compareTo).get() + 1L, null, null, 0, null));
        final BeanContainer<Long, DemoBean> container = new BeanContainer<>(DemoBean.class);
        container.setBeanIdResolver(DemoBean::getId);
        container.addAll(beans);
        return container;
    }

    @NotNull
    public static Container.Hierarchical generateHierarchicalContainer(int count) {
        final List<DemoBean> beans = generate(count);
        final HierarchicalContainer c = new HierarchicalContainer();
        c.addContainerProperty("id", Long.class, null);
        c.addContainerProperty("name", String.class, null);
        c.addContainerProperty("surname", String.class, null);
        c.addContainerProperty("age", Integer.class, null);
        c.addContainerProperty("birthday", Date.class, null);
        for (DemoBean bean : beans) {
            bean.fill(c.addItem(bean.id));
            c.setChildrenAllowed(bean.id, true);
            final List<DemoBean> children = generate(count * ((int) bean.id + 1), count);
            for (DemoBean child : children) {
                final Item item = c.addItem(child.id);
                Objects.requireNonNull(item, "item " + child.id + " already exists");
                child.fill(item);
                c.setParent(child.id, bean.id);
            }
        }
        return c;
    }
}
