package example.sampler;

import com.vaadin.data.HasValue;
import com.vaadin.event.FieldEvents;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontIcon;
import com.vaadin.server.Scrollable;
import com.vaadin.server.SerializableConsumer;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.*;
import com.vaadin.v7.data.Property;
import com.vaadin.v7.data.util.ObjectProperty;
import com.vaadin.v7.ui.AbstractLegacyComponent;
import com.vaadin.v7.ui.AbstractSelect;
import com.vaadin.v7.ui.Tree;
import com.vaadin.v7.ui.TreeTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * A component demo pane, demoes one component.
 *
 * <ul>
 *     <li>For V7 AbstractSelect, it's populated with Container produced via {@link DemoBean#generateContainer(int)} with 100 items</li>
 * </ul>
 *
 * @param <C> the component to demo, not null. Always a {@link Component}, but we can't set this lower bound since sometimes this will be {@link HasValue}.
 */
@SuppressWarnings("deprecation")
class ComponentDemo<C> extends DemoPanel {

    private static final Logger log = LoggerFactory.getLogger(ComponentDemo.class);
    @NotNull
    private final VerticalLayout controls;
    @NotNull
    private final C component;
    @NotNull
    private final HorizontalLayout content = new HorizontalLayout();

    public ComponentDemo(@NotNull C component) {
        this(component, true);
    }

    @NotNull
    public <C2> ComponentDemo<C2> as() {
        return (ComponentDemo) this;
    }

    @NotNull
    public ComponentDemo<HasValue<?>> asHasValue() {
        return as();
    }

    /**
     * @param component the component, not null.
     * @param nestComponent if true (the default), nest component within this layout. Set to false for e.g. Window/Dialog/Popup kind of demo.
     */
    public ComponentDemo(@NotNull C component, boolean nestComponent) {
        super(component.getClass().getSimpleName());
        AbstractComponent.class.cast(component);
        this.component = Objects.requireNonNull(component);

        content.setWidthFull();
        if (nestComponent) {
            content.addComponent(((AbstractComponent) component));
            content.setExpandRatio(((AbstractComponent) component), 1);
        }
        controls = new VerticalLayout();
        controls.setSpacing(false);
        controls.setMargin(false);
        controls.setWidthUndefined();
        content.addComponent(controls);
        setContent(content);

        addStandardControls();

        if (component instanceof HasValue) {
            final ComponentDemo<HasValue<?>> self = asHasValue();
            self.component.addValueChangeListener(e -> Notification.show(component + ": new value: " + e.getValue() + ", old value: " + e.getOldValue()));
            self.controlButton("Clear", HasValue::clear);
        }
        if (component instanceof FieldEvents.FocusNotifier) {
            ((FieldEvents.FocusNotifier) component).addFocusListener(e -> System.out.println(component + ": focused"));
        }
        if (component instanceof com.vaadin.v7.event.FieldEvents.FocusNotifier) {
            ((com.vaadin.v7.event.FieldEvents.FocusNotifier) component).addFocusListener(e -> System.out.println(component + ": focused"));
        }
        if (component instanceof FieldEvents.BlurNotifier) {
            ((FieldEvents.BlurNotifier) component).addBlurListener(e -> System.out.println(component + ": blurred"));
        }
        if (component instanceof com.vaadin.v7.event.FieldEvents.BlurNotifier) {
            ((com.vaadin.v7.event.FieldEvents.BlurNotifier) component).addBlurListener(e -> System.out.println(component + ": blurred"));
        }
        final IsSingleAndMultiSelect<Object> sms = IsSingleAndMultiSelect.of(((AbstractComponent) component));
        if (sms != null) {
            controlSelectEnum("Selection Mode", Grid.SelectionMode.SINGLE, Grid.SelectionMode.class, (components1, selectionMode) -> {
                if (selectionMode != null) {
                    sms.setSelectionMode(selectionMode);
                    if (selectionMode != Grid.SelectionMode.NONE) {
                        sms.addSelectionListener(e -> Notification.show("selected: " + e.getAllSelectedItems()));
                        sms.getHasValue().addValueChangeListener(e -> Notification.show("value changed: " + e.getValue()));
                    }
                }
            });
            controlBool("Read Only", (c, b) -> sms.getHasValue().setReadOnly(b));
            controlButton("Clear Selection", c -> sms.getHasValue().clear());
        }
        if (component instanceof TabSheet) { // also Accordion
            final ComponentDemo<TabSheet> self = as();
            self.controlBool("Tabs visible", true, TabSheet::setTabsVisible);
            self.controlButton("Select first tab", c -> c.setSelectedTab(c.getTab(0)));
            self.controlBool("Tab captions as HTML", false, TabSheet::setTabCaptionsAsHtml);
            self.component.setCloseHandler((ts, tc) -> Notification.show("Tab closed: " + tc));
            self.component.addSelectedTabChangeListener(e -> Notification.show("Tab changed: " + e.getTabSheet().getSelectedTab().getCaption()));
            self.controlBool("Tab #1: Icon", false, (c, b) -> c.getTab(0).setIcon(b ? VaadinIcons.ABACUS : null));
            self.controlBool("Tab #1: Icon Alt Text", false, (c, b) -> c.getTab(0).setIconAlternateText(b ? "Demo Alt text" : null));
            self.controlBool("Tab #1: Enabled", true, (c, b) -> c.getTab(0).setEnabled(b));
            self.controlBool("Tab #1: Visible", true, (c, b) -> c.getTab(0).setVisible(b));
            self.controlBool("Tab #1: Closable", false, (c, b) -> c.getTab(0).setClosable(b));
            self.controlBool("Tab #1: Description", false, (c, b) -> c.getTab(0).setDescription(b ? "Demo desc" : null));
            self.controlBool("Tab #1: Component Error", false, (c, b) -> c.getTab(0).setComponentError(b ? new UserError("Demo error") : null));
        }
        if (component instanceof Scrollable) {
            final ComponentDemo<Scrollable> self = as();
            self.controlBool("Scroll top to 20px", false, (c, b) -> c.setScrollTop(b ? 20 : 0));
            self.controlBool("Scroll left to 20px", false, (c, b) -> c.setScrollLeft(b ? 20 : 0));
        }
        if (component instanceof Property.ValueChangeNotifier) {
            ((Property.ValueChangeNotifier) component).addValueChangeListener(e -> {
                log.info(component + ": new value: " + e.getProperty().getValue());
                Notification.show(component + ": new value: " + e.getProperty().getValue());
            });
        }
    }

    public enum DemoSizes {
        UNDEFINED(null),
        FULL("100%"),
        HALF("50%"),
        SIZE_100PX("100px"),
        SIZE_400PX("400px"),
        SIZE_500PX("500px"),
        SIZE_30EM("30em");
        @Nullable
        public final String size;

        DemoSizes(@Nullable String size) {
            this.size = size;
        }
    }

    private void addStandardControls() {
        {
            final ComponentDemo<AbstractComponent> self = as();
            self.controlBool("Caption", true, true, (cb, c) -> cb.setCaption(c ? "Hello<b>world</b>!" : null));
            self.controlBool("Caption as HTML", AbstractComponent::setCaptionAsHtml);
            self.controlBool("Icon", (cb, c) -> cb.setIcon(c ? VaadinIcons.ABACUS : null));
            self.controlBool("Description", (cb, c) -> cb.setDescription(c ? "look ma, a tooltip!" : null));
            self.controlSelectEnum("Width", null, DemoSizes.class, (c, s) -> c.setWidth(s == null ? null : s.size));
            self.controlSelectEnum("Height", null, DemoSizes.class, (c, s) -> c.setHeight(s == null ? null : s.size));
            self.controlStyle("blabla");
            self.controlBool("Component Error", false, (c, b) -> c.setComponentError(b ? new UserError("Demo error") : null));
        }
        if (component instanceof AbstractField) {
            final ComponentDemo<AbstractField<?>> self = as();
            self.controlBool("Required indicator visible", AbstractField::setRequiredIndicatorVisible);
            self.controlBool("Read Only", AbstractField::setReadOnly);
        }
        if (component instanceof AbstractTextField) {
            final ComponentDemo<AbstractTextField> self = as();
            self.controlBool("Max Length (10)", (cb, c) -> cb.setMaxLength(c ? 10 : -1));
            self.controlBool("Placeholder", (cb, c) -> cb.setPlaceholder(c ? "placeholder" : ""));
            self.controlButton("selectAll()", AbstractTextField::selectAll);
            self.controlSelectEnum("Value change mode", ValueChangeMode.LAZY, ValueChangeMode.class, AbstractTextField::setValueChangeMode);
            self.controlSelect("Value change timeout", 400, Arrays.asList(0, 400, 4000), AbstractTextField::setValueChangeTimeout);
        }
        if (component instanceof AbstractMultiSelect) {
            final ComponentDemo<AbstractMultiSelect<?>> self = as();
            self.controlBool("Read Only", AbstractMultiSelect::setReadOnly);
            self.controlBool("Required Indicator Visible", AbstractMultiSelect::setRequiredIndicatorVisible);
            self.controlBool("Item Captions", (cb, c) -> cb.setItemCaptionGenerator(item -> c ? "Value is <b>" + item + "</b>" : "" + item));
        }
        if (component instanceof AbstractSingleSelect) {
            final ComponentDemo<AbstractSingleSelect<?>> self = as();
            self.controlBool("Read Only", AbstractSingleSelect::setReadOnly);
            self.controlBool("Required Indicator Visible", AbstractSingleSelect::setRequiredIndicatorVisible);
        }

        // date
        if (component instanceof AbstractDateField) {
            final ComponentDemo<AbstractDateField<?, ?>> self = as();
            self.controlBool("Has DateOutOfRangeMessage", (df, b) -> df.setDateOutOfRangeMessage(b ? "Date out of range" : null));
            self.controlBool("Custom Date Format", (df, b) -> df.setDateFormat(b ? "yyyy-MM-dd" : null));
            self.controlBool("UTC Zone", (df, b) -> df.setZoneId(b ? ZoneId.of("UTC") : ZoneId.systemDefault()));
            self.controlBool("Lenient", AbstractDateField::setLenient);
            self.controlBool("ShowISOWeekNumbers", AbstractDateField::setShowISOWeekNumbers);
            self.controlBool("Custom ParseErrorMessage", (df, b) -> df.setParseErrorMessage(b ? "Look Ma, parse error!" : null));
            // @todo setDateStyle()
            // @todo setAssistiveLabel()
            self.controlBool("Prevent Invalid Input", AbstractDateField::setPreventInvalidInput);
        }
        if (component instanceof AbstractLocalDateField) {
            final ComponentDemo<AbstractLocalDateField> self = as();
            self.controlBool("Range start", (df, b) -> df.setRangeStart(b ? LocalDate.now().minusMonths(1) : null));
            self.controlBool("Range end", (df, b) -> df.setRangeStart(b ? LocalDate.now().plusMonths(1) : null));
            self.controlSelectEnum("Resolution", DateResolution.DAY, DateResolution.class, AbstractDateField::setResolution);
        }
        if (component instanceof AbstractLocalDateTimeField) {
            final ComponentDemo<AbstractLocalDateTimeField> self = as();
            self.controlBool("Range start", (df, b) -> df.setRangeStart(b ? LocalDateTime.now().minusMonths(1) : null));
            self.controlBool("Range end", (df, b) -> df.setRangeStart(b ? LocalDateTime.now().plusMonths(1) : null));
            self.controlSelectEnum("Resolution", DateTimeResolution.MINUTE, DateTimeResolution.class, AbstractDateField::setResolution);
        }
        if (component instanceof AbstractOrderedLayout) {
            final ComponentDemo<AbstractOrderedLayout> self = as();
            self.controlBool("Spacing", ((AbstractOrderedLayout) component).isSpacing(), AbstractOrderedLayout::setSpacing);
            self.controlBool("Margin", ((AbstractOrderedLayout) component).getMargin().hasAll(), AbstractOrderedLayout::setMargin);
        }

        // Vaadin 7
        if (component instanceof AbstractLegacyComponent) {
            final ComponentDemo<com.vaadin.v7.ui.AbstractLegacyComponent> self = as();
            self.controlBool("Immediate", AbstractLegacyComponent::setImmediate);
            self.controlBool("Read Only", AbstractLegacyComponent::setReadOnly);
        }
        if (component instanceof com.vaadin.v7.ui.AbstractField) {
            final ComponentDemo<com.vaadin.v7.ui.AbstractField<?>> self = as();
            self.controlBool("Invalid Committed", com.vaadin.v7.ui.AbstractField::setInvalidCommitted);
            self.controlBool("Buffered", com.vaadin.v7.ui.AbstractField::setBuffered);
            self.controlButton("Commit", com.vaadin.v7.ui.AbstractField::commit);
            self.controlButton("discard()", com.vaadin.v7.ui.AbstractField::discard);

            self.controlBool("Property Data Source (null initial value)", (cb, c) -> {
                if (!c) {
                    cb.setPropertyDataSource(null);
                } else {
                    final ObjectProperty<?> prop = new ObjectProperty<>(null, cb.getType());
                    prop.addValueChangeListener(e -> Notification.show("ValueChange from ObjectProperty: " + e.getProperty().getValue(), Notification.Type.ERROR_MESSAGE));
                    cb.setPropertyDataSource(prop);
                }
            });
            self.controlBool("Invalid allowed", com.vaadin.v7.ui.AbstractField::setInvalidAllowed);
            self.controlBool("Required", com.vaadin.v7.ui.AbstractField::setRequired);
            self.controlBool("Required Error", (cb, c) -> cb.setRequiredError(c ? "Look ma, I'm required" : ""));
            self.controlBool("Conversion Error", (cb, c) -> cb.setConversionError(c ? "Look ma, I failed to convert" : ""));
            self.controlBool("Failing Converter", false, (f, b) -> f.setConverter(b ? new FailingConverter<>((Class)f.getType()) : null));
            self.controlButton("Validate", com.vaadin.v7.ui.AbstractField::validate);

            self.controlButton("getValue()", f -> Notification.show("value=" + f.getValue(), Notification.Type.ERROR_MESSAGE));
            self.controlButton("clear()", com.vaadin.v7.ui.AbstractField::clear);
            self.controlBool("Validation visible", com.vaadin.v7.ui.AbstractField::setValidationVisible);
        }
        if (component instanceof AbstractSelect) {
            final ComponentDemo<AbstractSelect> self = as();
            for (Object itemId : self.component.getItemIds()) {
                self.component.setItemCaption(itemId, "Explicit caption for bean #" + itemId);
                self.component.setItemIcon(itemId, VaadinIcons.ABACUS);
            }
            self.component.setItemCaptionPropertyId("name");
            self.controlSelectEnum("ItemCaptionMode", self.component.getItemCaptionMode(), AbstractSelect.ItemCaptionMode.class, AbstractSelect::setItemCaptionMode);
            self.controlBool("Null selection allowed", AbstractSelect::setNullSelectionAllowed);
            self.controlBool("Has Null selection ItemID", (s, b) -> self.component.setNullSelectionItemId(b ? self.component.getItemIds().iterator().next() : null));
            self.controlBool("MultiSelect", self.component.isMultiSelect(), AbstractSelect::setMultiSelect);
            self.controlBool("New Items Allowed", self.component.isNewItemsAllowed(), AbstractSelect::setNewItemsAllowed);
        }
    }

    @NotNull
    public ComponentDemo<C> controlBool(@NotNull String what, @NotNull BiConsumer<C, Boolean> controller) {
        return controlBool(what, false, controller);
    }

    @NotNull
    public ComponentDemo<C> controlButton(@NotNull String what, @NotNull SerializableConsumer<C> controller) {
        controls.addComponent(new Button(what, e -> controller.accept(component)));
        return this;
    }

    @NotNull
    public ComponentDemo<C> controlBool(@NotNull String what, boolean defaultValue, @NotNull BiConsumer<C, Boolean> controller) {
        final CheckBox cb = new CheckBox(what);
        // let's not control the component at this point: this will give us an opportunity to test the default values.
        cb.setValue(defaultValue);
        cb.addValueChangeListener(e -> controller.accept(component, e.getValue()));
        controls.addComponent(cb);
        return this;
    }

    @NotNull
    public ComponentDemo<C> controlBool(@NotNull String what, boolean defaultValue, boolean applyDefaultValue, @NotNull BiConsumer<C, Boolean> controller) {
        return control(new CheckBox(what, defaultValue), applyDefaultValue, controller);
    }

    @NotNull
    public <V> ComponentDemo<C> control(@NotNull HasValue<V> control, boolean applyDefaultValue, @NotNull BiConsumer<C, V> controller) {
        // let's not control the component at this point: this will give us an opportunity to test the default values.
        control.addValueChangeListener(e -> controller.accept(component, e.getValue()));
        if (applyDefaultValue) {
            // it's good to apply the default value for some controls though, for example caption
            controller.accept(component, control.getValue());
        }
        controls.addComponent(((Component) control));
        return this;
    }

    @NotNull
    public <T> ComponentDemo<C> controlSelect(@NotNull String what, @Nullable T defaultValue, @NotNull List<T> options, @NotNull BiConsumer<C, T> controller) {
        final NativeSelect<T> cb = new NativeSelect<>(what, options);
        cb.setValue(defaultValue);
        return control(cb, false, controller);
    }

    @NotNull
    public <E extends Enum<E>> ComponentDemo<C> controlSelectEnum(@NotNull String what, @Nullable E defaultValue, @NotNull Class<E> clazz, @NotNull BiConsumer<C, E> controller) {
        return controlSelect(what, defaultValue, Arrays.asList(clazz.getEnumConstants()), controller);
    }

    /**
     * Controls (adds/removes) given style via {@link AbstractComponent#addStyleName(String)}.
     * @param style the style to remove, one of {@link com.vaadin.ui.themes.ValoTheme} constants.
     * @return this
     */
    @NotNull
    public ComponentDemo<C> controlStyle(@NotNull String style) {
        return controlBool("Style: " + style, hasStyle(style), (c, b) -> setStyle(style, b));
    }

    /**
     * Controls (adds/removes) given style via {@link AbstractComponent#addStyleName(String)}.
     * @param style the style to remove, one of {@link com.vaadin.ui.themes.ValoTheme} constants.
     * @return this
     */
    @NotNull
    public ComponentDemo<C> controlStyles(@NotNull String... style) {
        for (String s : style) {
            controlStyle(s);
        }
        return this;
    }

    @NotNull
    private static Set<String> getStyles(@NotNull Component component) {
        final String style = component.getStyleName();
        if (style.isEmpty()) {
            return Collections.emptySet();
        }
        final HashSet<String> styles = new HashSet<>();
        StringTokenizer tokenizer = new StringTokenizer(style, " ");
        while (tokenizer.hasMoreTokens()) {
            styles.add(tokenizer.nextToken());
        }
        return styles;
    }

    private static boolean hasStyle(@NotNull Component component, @NotNull String style) {
        return getStyles(component).contains(style);
    }

    private boolean hasStyle(@NotNull String style) {
        return hasStyle(((Component) component), style);
    }

    private void setStyle(@NotNull String style, boolean add) {
        if (add) {
            ((Component) component).addStyleName(style);
        } else {
            ((Component) component).removeStyleName(style);
        }
    }

    @Override
    public void setHeight(float height, Unit unit) {
        super.setHeight(height, unit);
        content.setHeight(height < 0 ? null : "100%");
    }
}
