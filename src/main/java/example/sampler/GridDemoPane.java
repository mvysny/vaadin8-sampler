package example.sampler;

import com.vaadin.contextmenu.GridContextMenu;
import com.vaadin.contextmenu.TreeContextMenu;
import com.vaadin.data.provider.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.data.provider.HierarchicalDataProvider;
import com.vaadin.data.provider.HierarchicalQuery;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GridDemoPane extends VerticalLayout implements View {
    private int footerCounter = 0;
    private int headerCounter = 0;

    public GridDemoPane() {
        setId(getClass().getSimpleName());
        setMargin(false);

        gridDemo();

        treeDemo();
    }

    private void treeDemo() {
        final Tree<Person> tree = new Tree<>("Tree", newDataProvider());
        tree.addSelectionListener(e -> Notification.show("selected: " + e.getAllSelectedItems()));
        tree.addItemClickListener(e -> Notification.show("clicked: " + e.getItem()));
//        tree.addContextClickListener(e -> Notification.show("context-clicked: " + e.getMouseEventDetails()));
        tree.setItemCaptionGenerator(item -> item.getName() + ", " + item.getAge());
        tree.setHeight("400px");
        addComponent(new ComponentDemo<>(tree)
                .controlSelectEnum("Selection Mode", Grid.SelectionMode.SINGLE, Grid.SelectionMode.class, Tree::setSelectionMode)
                .controlSelectEnum("Content Mode", ContentMode.TEXT, ContentMode.class, Tree::setContentMode)
        );

        final TreeContextMenu<Person> menu = new TreeContextMenu<>(tree);
        menu.addTreeContextMenuListener((TreeContextMenu.TreeContextMenuOpenListener<Person>) event -> Notification.show("Menu open: " + event.getItem()));
        menu.addItem("Edit");
    }

    private void gridDemo() {
        final Grid<Person> grid = new Grid<>(Person.class);
        grid.removeAllColumns();
        grid.setItems(generateData(false));
        grid.addColumn("name");
        grid.addColumn("age", new NumberRenderer(Locale.ENGLISH));
        grid.addColumn("birthday", new LocalDateRenderer("dd.MM.yyyy", Locale.ENGLISH));
        grid.addColumn(p -> p.name != null ? "Edit" : null, new ButtonRenderer<>(e -> Notification.show("edit: " + e.getItem()), null));
        grid.addColumn(p -> "Details", new ButtonRenderer<>(e -> grid.setDetailsVisible(e.getItem(), !grid.isDetailsVisible(e.getItem()))));
        grid.setDetailsGenerator(p -> new Label("Person: " + p));
        grid.addSelectionListener(e -> Notification.show("selected: " + e.getAllSelectedItems()));
        grid.addItemClickListener(e -> Notification.show("clicked: " + e.getItem()));
//        grid.addContextClickListener(e -> Notification.show("context-clicked: " + e.getMouseEventDetails()));

        final GridContextMenu<Person> menu = new GridContextMenu<>(grid);
        menu.addGridBodyContextMenuListener((GridContextMenu.GridContextMenuOpenListener<Person>) event -> Notification.show("Grid body: Menu open: " + event.getItem()));
        menu.addGridHeaderContextMenuListener((GridContextMenu.GridContextMenuOpenListener<Person>) event -> Notification.show("Grid header: Menu open: " + event.getItem()));
        menu.addGridFooterContextMenuListener((GridContextMenu.GridContextMenuOpenListener<Person>) event -> Notification.show("Grid footer: Menu open: " + event.getItem()));
        menu.addItem("Edit", item -> Notification.show("Editing"));

        addComponent(new ComponentDemo<>(grid)
                .controlSelectEnum("Selection Mode", Grid.SelectionMode.SINGLE, Grid.SelectionMode.class, Grid::setSelectionMode)
                .controlBool("Header visible", true, Grid::setHeaderVisible)
                .controlButton("Add Header", g -> {
                    final HeaderRow headerRow = g.appendHeaderRow();
                    headerRow.getCell("age").setHtml("<b>Age</b>" + (headerCounter++));
                    headerRow.getCell("name").setComponent(new Button("X", e -> g.removeHeaderRow(headerRow)));
                })
                .controlBool("Footer visible", false, Grid::setFooterVisible)
                .controlButton("Add Footer", g -> {
                    final FooterRow footerRow = g.appendFooterRow();
                    footerRow.getCell("age").setHtml("<b>Age</b>" + (footerCounter++));
                    footerRow.getCell("name").setComponent(new Button("X", e -> g.removeFooterRow(footerRow)));
                })
                .controlBool("Frozen Columns", false, (g, b) -> g.setFrozenColumnCount(b ? 2 : 0))
                .controlBool("Columns 0-2: resizable", true, (g, b) -> g.getColumns().subList(0, 3).forEach(c -> c.setResizable(b)))
                .controlBool("Columns 0-2: sortable", true, (g, b) -> g.getColumns().subList(0, 3).forEach(c -> c.setSortable(b)))
                .controlBool("Columns 0-2: editable", false, (g, b) -> g.getColumns().subList(0, 3).forEach(c -> c.setEditable(b)))
                .controlBool("Columns: hidable", false, (g, b) -> g.getColumns().forEach(c -> c.setHidable(b)))
                .controlBool("Column 0: caption", true, (g, b) -> g.getColumns().get(0).setCaption(b ? "Name aaa" : ""))
                .controlBool("Column 0: hidden", false, (g, b) -> g.getColumns().get(0).setHidden(b))
                .controlBool("Column 0: expand", true, (g, b) -> g.getColumns().get(0).setExpandRatio(b ? 1 : 0))
                .controlBool("Column 0: fixed width", false, (g, b) -> {
                    final Grid.Column<Person, ?> col1 = g.getColumns().get(0);
                    if (b) {
                        col1.setWidth(100);
                    } else {
                        col1.setWidthUndefined();
                    }
                })
                .controlBool("Column 0: style generator", false, (g, b) -> g.getColumns().get(0).setStyleGenerator(e -> b?"look_ma_style" : null))
        );
    }


    public static final class Person implements Serializable {
        private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
        private final long id = ID_GENERATOR.incrementAndGet();
        private String name;
        private int age;
        private LocalDate birthday;
        private boolean hasChildren;
        public Person(String name, int age, LocalDate birthday, boolean hasChildren) {
            this.name = name;
            this.age = age;
            this.birthday = birthday;
            this.hasChildren = hasChildren;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        public void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }

        @Override
        public String toString() {
            return "Person{#" +id +
                    " name='" + name + '\'' +
                    ", age=" + age +
                    ", birthday=" + birthday +
                    '}';
        }

        public boolean isHasChildren() {
            return hasChildren;
        }

        public void setHasChildren(boolean hasChildren) {
            this.hasChildren = hasChildren;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person person = (Person) o;
            return id == person.id;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

    @NotNull
    private static Collection<Person> generateData(boolean hasChildren) {
        final List<Person> list = new ArrayList<>(IntStream.range(1, 100).mapToObj(it -> new Person("person " + it, it, LocalDate.now().plusDays(it), hasChildren)).collect(Collectors.toList()));
        list.add(0, new Person(null, 0, null, false));
        return list;
    }

    @NotNull
    private static HierarchicalDataProvider<Person, Void> newDataProvider() {
        return new AbstractBackEndHierarchicalDataProvider<Person, Void>() {
            @Override
            protected Stream<Person> fetchChildrenFromBackEnd(HierarchicalQuery<Person, Void> query) {
                if (!hasChildren(query.getParent())) {
                    return Stream.empty();
                }
                return generateData(query.getParent() == null).stream()
                        .skip(query.getOffset())
                        .limit(query.getLimit());
            }

            @Override
            public int getChildCount(HierarchicalQuery<Person, Void> query) {
                return ((int) fetchChildrenFromBackEnd(query).count());
            }

            @Override
            public boolean hasChildren(Person item) {
                return item == null || item.isHasChildren();
            }
        };
    }
}
