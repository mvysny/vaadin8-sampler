package example.sampler;

import com.vaadin.v7.data.util.converter.Converter;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class FailingConverter<T> implements Converter<T, T> {
    @NotNull
    private final Class<T> clazz;

    public FailingConverter(@NotNull Class<T> clazz) {
        this.clazz = Objects.requireNonNull(clazz);
    }

    @Override
    public T convertToModel(T value, Class<? extends T> targetType, Locale locale) throws ConversionException {
        throw new ConversionException("FailingConverter: simulated conversion error");
    }

    @Override
    public T convertToPresentation(T value, Class<? extends T> targetType, Locale locale) throws ConversionException {
        return value; // this way succeeds
    }

    @Override
    public Class<T> getModelType() {
        return clazz;
    }

    @Override
    public Class<T> getPresentationType() {
        return clazz;
    }
}
