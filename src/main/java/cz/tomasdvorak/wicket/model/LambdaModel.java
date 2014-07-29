package cz.tomasdvorak.wicket.model;

import org.apache.wicket.model.IModel;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaModel<T, U> implements IModel<T> {

    private final Supplier<T> getter;
    private final Consumer<T> setter;

    protected LambdaModel(Supplier<T> getter, Consumer<T> setter) {
        this.setter = setter;
        this.getter = getter;
    }

    @Override
    public T getObject() {
        return getter.get();
    }

    @Override
    public void setObject(T value) {
        setter.accept(value);
    }

    @Override
    public void detach() {
    }
}