package cz.tomasdvorak.wicket.model;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Same interface as {@link java.util.function.Supplier}, but implementing {@link java.io.Serializable}
 */
@FunctionalInterface
public interface SerializableSupplier<T> extends Supplier<T>, Serializable {
}
