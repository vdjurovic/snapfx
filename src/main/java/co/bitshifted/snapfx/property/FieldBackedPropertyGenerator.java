package co.bitshifted.snapfx.property;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class FieldBackedPropertyGenerator {

    private FieldBackedPropertyGenerator() {

    }

    public static StringFieldProperty stringFieldProperty(Supplier<String> supplier, Consumer<String> consumer) {
        return new StringFieldProperty(supplier, consumer);
    }

    public static IntegerFieldProperty integerFieldProperty(Supplier<Integer> supplier, Consumer<Integer> consumer) {
        return new IntegerFieldProperty(supplier, fromIntegerConsumer(consumer));
    }

    public static BooleanFieldProperty booleanFieldProperty(Supplier<Boolean> supplier, Consumer<Boolean> consumer) {
        return new BooleanFieldProperty(supplier, consumer);
    }

    public static DoubleFieldProperty doubleFieldProperty(Supplier<Double> supplier, Consumer<Double> consumer) {
        return new DoubleFieldProperty(supplier, fromDoubleConsumer(consumer));
    }

    public static FloatFieldProperty floatFieldProperty(Supplier<Float> supplier, Consumer<Float> consumer) {
        return new FloatFieldProperty(supplier, fromFloatConsumer(consumer));
    }

     public static <T> ObjectFieldProperty<T>  objectFieldProperty(Supplier<T> supplier, Consumer<T> consumer) {
        return new ObjectFieldProperty<T>(supplier, consumer);
    }

    private static Consumer<Number> fromIntegerConsumer(Consumer<Integer> c) {
        return number -> c.accept(number.intValue());
    }

    private static Consumer<Number> fromFloatConsumer(Consumer<Float> c) {
        return number -> c.accept(number.floatValue());
    }

    private static Consumer<Number> fromDoubleConsumer(Consumer<Double> c) {
        return number -> c.accept(number.doubleValue());
    }
}
