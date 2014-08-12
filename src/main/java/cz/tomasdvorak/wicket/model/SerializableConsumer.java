package cz.tomasdvorak.wicket.model;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Same interface as {@link java.util.function.Consumer}, but implementing {@link java.io.Serializable}
 */
public interface SerializableConsumer<T> extends Consumer<T>, Serializable {
}
