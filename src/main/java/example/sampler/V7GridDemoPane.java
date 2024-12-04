package example.sampler;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.v7.contextmenu.GridContextMenu;
import com.vaadin.v7.ui.Grid;
import com.vaadin.v7.ui.Label;
import com.vaadin.v7.ui.VerticalLayout;
import com.vaadin.v7.ui.renderers.ButtonRenderer;
import com.vaadin.v7.ui.renderers.DateRenderer;
import com.vaadin.v7.ui.renderers.NumberRenderer;

import java.text.DateFormat;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class V7GridDemoPane extends VerticalLayout implements View {
    private int footerCounter = 0;
    private int headerCounter = 0;
    public V7GridDemoPane() {
        setId(getClass().getSimpleName());
        setMargin(false);

        final Grid grid = new Grid(DemoBean.generateContainer(100));
        grid.removeAllColumns();
        grid.addColumn("name");
        grid.addColumn("age").setRenderer(new NumberRenderer(Locale.ENGLISH));
        grid.addColumn("birthday").setRenderer(new DateRenderer(DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH)));
        grid.addColumn("surname")
                .setRenderer(new ButtonRenderer(e -> grid.setDetailsVisible(e.getItemId(), !grid.isDetailsVisible(e.getItemId()))))
                .setHeaderCaption("Show Details");
        grid.setDetailsGenerator(p -> new Label("Person: " + p.getItem()));
        grid.addSelectionListener(e -> Notification.show("selected: " + e.getSelected()));
        grid.addItemClickListener(e -> {
            Notification.show("clicked: " + e.getItem());
            grid.setDetailsVisible(e.getItemId(), !grid.isDetailsVisible(e.getItemId()));
        });
//        grid.addContextClickListener(e -> Notification.show("context-clicked: " + e.getMouseEventDetails()));
        addComponent(new ComponentDemo<>(grid)
                .controlSelectEnum("Selection Mode", Grid.SelectionMode.SINGLE, Grid.SelectionMode.class, Grid::setSelectionMode)
                .controlBool("Header visible", true, Grid::setHeaderVisible)
                .controlButton("Add Header", g -> {
                    final Grid.HeaderRow headerRow = g.appendHeaderRow();
                    headerRow.getCell("age").setHtml("<b>Age</b>" + (headerCounter++));
                    headerRow.getCell("name").setComponent(new Button("X", e -> g.removeHeaderRow(headerRow)));
                })
                .controlBool("Footer visible", false, Grid::setFooterVisible)
                .controlButton("Add Footer", g -> {
                    final Grid.FooterRow footerRow = g.appendFooterRow();
                    footerRow.getCell("age").setHtml("<b>Age</b>" + (footerCounter++));
                    footerRow.getCell("name").setComponent(new Button("X", e -> g.removeFooterRow(footerRow)));
                })
                .controlBool("Frozen Columns", false, (g, b) -> g.setFrozenColumnCount(b ? 2 : 0))
                .controlBool("Columns 0-2: resizable", true, (g, b) -> g.getColumns().subList(0, 3).forEach(c -> c.setResizable(b)))
                .controlBool("Columns 0-2: sortable", true, (g, b) -> g.getColumns().subList(0, 3).forEach(c -> c.setSortable(b)))
                .controlBool("Columns 0-2: editable", false, (g, b) -> g.getColumns().subList(0, 3).forEach(c -> c.setEditable(b)))
                .controlBool("Columns: hidable", false, (g, b) -> g.getColumns().forEach(c -> c.setHidable(b)))
                .controlBool("Column 0: caption", true, (g, b) -> g.getColumns().get(0).setHeaderCaption(b ? "Name aaa" : ""))
                .controlBool("Column 0: hidden", false, (g, b) -> g.getColumns().get(0).setHidden(b))
                .controlBool("Column 0: expand", true, (g, b) -> g.getColumns().get(0).setExpandRatio(b ? 1 : 0))
                .controlBool("Column 0: fixed width", false, (g, b) -> {
                    final Grid.Column col1 = g.getColumns().get(0);
                    if (b) {
                        col1.setWidth(100);
                    } else {
                        col1.setWidthUndefined();
                    }
                })
        );

        final GridContextMenu menu = new GridContextMenu(grid);
        menu.addGridBodyContextMenuListener((GridContextMenu.GridContextMenuOpenListener) event -> Notification.show("Grid body '" + event.getPropertyId() + "': Menu open: " + event.getItemId()));
        menu.addGridHeaderContextMenuListener((GridContextMenu.GridContextMenuOpenListener) event -> Notification.show("Grid header '" + event.getPropertyId() + "': Menu open: " + event.getItemId()));
        menu.addGridFooterContextMenuListener((GridContextMenu.GridContextMenuOpenListener) event -> Notification.show("Grid footer '" + event.getPropertyId() + "': Menu open: " + event.getItemId()));
        menu.addItem("Edit", item -> Notification.show("Editing"));
    }
}
