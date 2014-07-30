package cz.tomasdvorak.wicket.model;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaModel<T> implements IModel<T> {

    private final Supplier<T> getter;
    private final Consumer<T> setter;

    protected LambdaModel(Supplier<T> getter, Consumer<T> setter) {
        this.setter = setter;
        this.getter = getter;
    }

    @Override
    public T getObject() {
        try {
            return getter.get();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public void setObject(T value) {
        try {
            setter.accept(value);
        } catch (NullPointerException e) {
            throw new WicketRuntimeException("Attempted to set property value on a null object.", e);
        }
    }

    @Override
    public void detach() {
    }
}
