package cz.tomasdvorak.wicket.model;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;

public class LambdaModel<T> implements IModel<T> {

    private final SerializableSupplier<T> getter;
    private final SerializableConsumer<T> setter;

    protected LambdaModel(SerializableSupplier<T> getter, SerializableConsumer<T> setter) {
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
