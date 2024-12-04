package example.sampler;

import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class LayoutsDemoPane extends VerticalLayout implements View {
    public LayoutsDemoPane() {
        setId(getClass().getSimpleName());
        setMargin(false);

        addComponent(new DemoPanel("Vertical Layout", new Button("Demo", e -> UI.getCurrent().addWindow(new VerticalLayoutDemoWindow()))));
        addComponent(new DemoPanel("Horizontal Layout", new Button("Demo", e -> UI.getCurrent().addWindow(new HorizontalLayoutDemoWindow()))));
        addComponent(new DemoPanel("Form Layout", new Button("Demo", e -> UI.getCurrent().addWindow(new FormLayoutDemoWindow()))));
        addComponent(new DemoPanel("Grid Layout", new Button("Demo", e -> UI.getCurrent().addWindow(new GridLayoutDemoWindow()))));
        addComponent(new DemoPanel("Absolute Layout", new Button("Demo", e -> UI.getCurrent().addWindow(new AbsoluteLayoutDemoWindow()))));
        addComponent(newTabSheetDemo());
        addComponent(newAccordionDemo());
        addComponents(newHorizontalSplitPanelDemo());
        addComponents(newVerticalSplitPanelDemo());
    }

    @NotNull
    private Component newHorizontalSplitPanelDemo() {
        final HorizontalSplitPanel panel = new HorizontalSplitPanel(new Label("Content 1"), new Label("Content 2"));
        return new ComponentDemo<>(panel)
                .controlStyle(ValoTheme.SPLITPANEL_LARGE)
                ;
    }

    @NotNull
    private Component newVerticalSplitPanelDemo() {
        final VerticalSplitPanel panel = new VerticalSplitPanel(new Label("Content 1"), new Label("Content 2"));
        panel.setHeight("400px");
        return new ComponentDemo<>(panel)
                .controlStyle(ValoTheme.SPLITPANEL_LARGE)
                ;
    }

    @NotNull
    private Component newAccordionDemo() {
        final Accordion accordion = new Accordion();
        accordion.addTab(new Button("Button 1"), "Tab 1");
        accordion.addTab(new Button("Button 2"), "Tab 2");
        accordion.addTab(new Button("Button 3"), "Tab 3");
        return new ComponentDemo<>(accordion)
                .controlStyle(ValoTheme.ACCORDION_BORDERLESS)
                ;
    }

    @NotNull
    private Component newTabSheetDemo() {
        final TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(new Button("Button 1"), "Tab 1");
        tabSheet.addTab(new Button("Button 2"), "Tab 2");
        tabSheet.addTab(new Button("Button 3"), "Tab 3");
        return new ComponentDemo<>(tabSheet)
                .controlStyles(ValoTheme.TABSHEET_FRAMED, ValoTheme.TABSHEET_CENTERED_TABS, ValoTheme.TABSHEET_EQUAL_WIDTH_TABS, ValoTheme.TABSHEET_PADDED_TABBAR, ValoTheme.TABSHEET_COMPACT_TABBAR, ValoTheme.TABSHEET_ICONS_ON_TOP, ValoTheme.TABSHEET_ONLY_SELECTED_TAB_IS_CLOSABLE)
                ;
    }

    private static final class AlignmentSelect extends NativeSelect<Alignment> {
        private static final List<Alignment> ALIGNMENTS = Arrays.asList(Alignment.TOP_LEFT, Alignment.TOP_CENTER, Alignment.TOP_RIGHT, Alignment.MIDDLE_LEFT, Alignment.MIDDLE_CENTER, Alignment.MIDDLE_RIGHT, Alignment.BOTTOM_LEFT, Alignment.BOTTOM_CENTER, Alignment.BOTTOM_RIGHT);

        public AlignmentSelect(@NotNull String label, @NotNull Alignment value) {
            super(label);
            setItems(ALIGNMENTS);
            setItemCaptionGenerator(e -> {
                String s = "";
                if (e.isTop()) {
                    s += "Top";
                } else if (e.isMiddle()) {
                    s += "Middle";
                } else {
                    s += "Bottom";
                }
                s += " ";
                if (e.isLeft()) {
                    s += "Left";
                } else if (e.isRight()) {
                    s += "Right";
                } else {
                    s += "Center";
                }
                return s;
            });
            setValue(value);
        }
    }

    private static class AbstractOrderedLayoutDemoWindow<C extends AbstractOrderedLayout> extends Window {
        public AbstractOrderedLayoutDemoWindow(@NotNull C layout) {
            super(layout.getClass().getSimpleName());
            setResizable(true);

            final Button button2 = new Button("Button 2");
            layout.addComponents(new Button("Button 1"), button2, new Button("Button 3"));

            final ComponentDemo<C> demoPanel = new ComponentDemo<>(layout)
                    .control(new AlignmentSelect("Default Alignment", layout.getDefaultComponentAlignment()), false, AbstractOrderedLayout::setDefaultComponentAlignment)
                    .control(new AlignmentSelect("Button 2 alignment", layout.getDefaultComponentAlignment()), false, (c, b) -> c.setComponentAlignment(button2, b));

            if (layout instanceof FormLayout) {
                demoPanel.controlStyle(ValoTheme.FORMLAYOUT_LIGHT);
            } else {
                demoPanel.controlStyles(ValoTheme.LAYOUT_CARD, ValoTheme.LAYOUT_WELL, ValoTheme.LAYOUT_HORIZONTAL_WRAPPING, ValoTheme.LAYOUT_COMPONENT_GROUP);
            }

            // FormLayout doesn't support expand
            if (!(layout instanceof FormLayout)) {
                demoPanel.controlBool("Expand Button 2", (c, b) -> c.setExpandRatio(button2, b ? 1 : 0));
                // @todo test FormLayout + alignments
            }
            demoPanel.setSizeFull();
            setContent(demoPanel);
        }
    }

    private static class VerticalLayoutDemoWindow extends AbstractOrderedLayoutDemoWindow<VerticalLayout> {
        public VerticalLayoutDemoWindow() {
            super(new VerticalLayout());
        }
    }

    private static class HorizontalLayoutDemoWindow extends AbstractOrderedLayoutDemoWindow<HorizontalLayout> {
        public HorizontalLayoutDemoWindow() {
            super(new HorizontalLayout());
        }
    }

    private static class FormLayoutDemoWindow extends AbstractOrderedLayoutDemoWindow<FormLayout> {
        public FormLayoutDemoWindow() {
            super(new FormLayout(new TextField("Field 1"), new CheckBox("Checkbox demo")));
        }
    }

    private static class GridLayoutDemoWindow extends Window {
        public GridLayoutDemoWindow() {
            super("Grid Layout Demo");
            setResizable(true);
            final GridLayout layout = new GridLayout(3, 3);

            final Button button2 = new Button("Button 2");
            layout.addComponents(new Button("Button 1"), button2, new Button("Button 3"));
            for (int i = 4; i <= 9; i++) {
                layout.addComponent(new Button("Button " + i));
            }

            final ComponentDemo<GridLayout> demoPanel = new ComponentDemo<>(layout)
                    .control(new AlignmentSelect("Default Alignment", layout.getDefaultComponentAlignment()), false, GridLayout::setDefaultComponentAlignment)
                    .control(new AlignmentSelect("Button 2 alignment", layout.getDefaultComponentAlignment()), false, (c, b) -> c.setComponentAlignment(button2, b))
                    .controlBool("Expand column #1", (c, b) -> c.setColumnExpandRatio(1, b ? 1 : 0))
                    .controlBool("Expand row #0", (c, b) -> c.setRowExpandRatio(0, b ? 1 : 0))
                    ;

            demoPanel.setSizeFull();
            setContent(demoPanel);
        }
    }

    private static class AbsoluteLayoutDemoWindow extends Window {
        public AbsoluteLayoutDemoWindow() {
            super("Absolute Layout Demo");
            setResizable(true);
            final AbsoluteLayout layout = new AbsoluteLayout();
            layout.addComponent(new Button("Button 1"));

            final Button button2 = new Button("Button 2");
            layout.addComponents(button2);

            final ComponentDemo<AbsoluteLayout> demoPanel = new ComponentDemo<>(layout)
                    .controlBool("Left 20x", (l, b) -> {
                        final AbsoluteLayout.ComponentPosition p = layout.getPosition(button2);
                        p.setLeft(b ? 20f : null, Unit.PIXELS);
                        layout.setPosition(button2, p);
                    })
                    .controlBool("Right 20x", (l, b) -> {
                        final AbsoluteLayout.ComponentPosition p = layout.getPosition(button2);
                        p.setRight(b ? 20f : null, Unit.PIXELS);
                        layout.setPosition(button2, p);
                    })
                    .controlBool("Top 20x", (l, b) -> {
                        final AbsoluteLayout.ComponentPosition p = layout.getPosition(button2);
                        p.setTop(b ? 20f : null, Unit.PIXELS);
                        layout.setPosition(button2, p);
                    })
                    .controlBool("Bottom 20x", (l, b) -> {
                        final AbsoluteLayout.ComponentPosition p = layout.getPosition(button2);
                        p.setBottom(b ? 20f : null, Unit.PIXELS);
                        layout.setPosition(button2, p);
                    })
                    ;

            demoPanel.setSizeFull();
            setContent(demoPanel);
        }
    }
}
