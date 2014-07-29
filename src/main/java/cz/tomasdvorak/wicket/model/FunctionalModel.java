package cz.tomasdvorak.wicket.model;

import org.apache.wicket.model.IModel;

import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionalModel<T, U> implements IModel<T> {

    private final U object;
    private final Function<U, T> getter;
    private final Consumer<T> setter;

    protected FunctionalModel(Function<U, T> getter, Consumer<T> setter, U object) {
        this.setter = setter;
        this.getter = getter;
        this.object = object;
    }

    @Override
    public T getObject() {
        return getter.apply(object);
    }

    @Override
    public void setObject(T t) {

        setter.accept(t);
    }

    @Override
    public void detach() {
    }
}