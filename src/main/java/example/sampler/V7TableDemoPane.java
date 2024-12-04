package example.sampler;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.util.converter.StringToDateConverter;
import com.vaadin.v7.data.util.converter.StringToIntegerConverter;
import com.vaadin.v7.ui.AbstractSelect;
import com.vaadin.v7.ui.Table;
import com.vaadin.v7.ui.TreeTable;
import com.vaadin.v7.ui.VerticalLayout;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class V7TableDemoPane extends VerticalLayout implements View {
    public V7TableDemoPane() {
        setId(getClass().getSimpleName());
        setMargin(false);

        tableDemo(new Table("", DemoBean.generateContainer(100)));
        treeTableDemo();
    }

    private void treeTableDemo() {
        final TreeTable table = new TreeTable("", DemoBean.generateHierarchicalContainer(100));
        final ComponentDemo<Table> c = tableDemo(table);
        c.<TreeTable>as().controlBool("Column 1: Hierarchical", (t, b) -> t.setHierarchyColumn(b ? "age" : null));
    }

    @NotNull
    private ComponentDemo<Table> tableDemo(@NotNull Table table) {
        table.setColumnHeader("name", "Name aaa");
        table.setConverter("age", new StringToIntegerConverter());
        table.setColumnAlignment("age", Table.Align.RIGHT);
        table.setColumnFooter("age", "Age Total");
        table.setColumnWidth("age", 200);
        table.setConverter("birthday", new StringToDateConverter());
        table.addGeneratedColumn("details", (Table.ColumnGenerator) (source, itemId, columnId) -> new Button("Edit " + itemId));
        table.addGeneratedColumn("generated", (Table.ColumnGenerator) (source, itemId, columnId) -> "Generated Column: String for " + itemId);
        table.setColumnHeader("details", "Edit");
        table.addItemClickListener(e -> {
            Notification.show("clicked: " + e.getItem());
        });
        table.setItemDescriptionGenerator((AbstractSelect.ItemDescriptionGenerator) (source, itemId, propertyId) -> propertyId == null ? itemId.toString() : itemId.toString() + ": " + propertyId);
        table.addContextClickListener(e -> Notification.show("context-clicked: " + e.getMouseEventDetails()));
        table.setVisibleColumns("name", "age", "birthday", "details", "generated");
        final ComponentDemo<Table> c = new ComponentDemo<>(table)
                .controlBool("Selectable", true, Table::setSelectable)
                .controlBool("Column collapsing allowed", false, Table::setColumnCollapsingAllowed)
                .controlBool("Column reordering allowed", false, Table::setColumnReorderingAllowed)
                .controlSelectEnum("Column header mode", Table.ColumnHeaderMode.EXPLICIT_DEFAULTS_ID, Table.ColumnHeaderMode.class, Table::setColumnHeaderMode)
                .controlSelectEnum("Row header mode", Table.RowHeaderMode.HIDDEN, Table.RowHeaderMode.class, Table::setRowHeaderMode)
                .controlBool("Editable", false, Table::setEditable)
                .controlBool("Sort Ascending", true, Table::setSortAscending)
                .controlBool("Sort Enabled", true, Table::setSortEnabled)
                .controlSelectEnum("Drag Mode", Table.TableDragMode.NONE, Table.TableDragMode.class, Table::setDragMode)
                .controlSelectEnum("Multi Select Mode", MultiSelectMode.DEFAULT, MultiSelectMode.class, Table::setMultiSelectMode)
                .controlBool("MultiSelect Touch Detection Enabled", true, Table::setMultiSelectTouchDetectionEnabled)
                .controlBool("Footer visible", false, Table::setFooterVisible)

                .controlBool("Column 0: icon", false, (t, b) -> t.setColumnIcon("name", b ? VaadinIcons.ABACUS : null))
                .controlBool("Column 0: header", true, (g, b) -> g.setColumnHeader("name", b ? "Name aaa" : null))
                .controlBool("Column 1: footer", true, (g, b) -> g.setColumnFooter("age", b ? "Age Total" : null))
                .controlBool("Column 0: collapsed", false, (g, b) -> g.setColumnCollapsed("name", b))

                .controlStyles(ValoTheme.TABLE_NO_STRIPES, ValoTheme.TABLE_NO_VERTICAL_LINES, ValoTheme. TABLE_NO_HORIZONTAL_LINES, ValoTheme.TABLE_NO_HEADER, ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_COMPACT, ValoTheme.TABLE_SMALL)

                ;
        addComponent(c);
        return c;
    }
}
