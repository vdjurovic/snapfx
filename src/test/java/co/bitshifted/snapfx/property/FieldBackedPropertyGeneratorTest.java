package co.bitshifted.snapfx.property;

import javafx.beans.property.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldBackedPropertyGeneratorTest {

    @Test
    void testStringBinding() {
        var foo = new FooTest();
        foo.setStringValue("some string");

        var textProperty = new SimpleStringProperty();
        var stringBackedProperty = FieldBackedPropertyGenerator.stringFieldProperty(() -> foo.getStringValue(), value -> foo.setStringValue(value));

        textProperty.bindBidirectional(stringBackedProperty);
        assertEquals("some string", textProperty.get());
        textProperty.set("new string");
        assertEquals("new string", stringBackedProperty.get());
        stringBackedProperty.set("another string");
        assertEquals("another string", textProperty.get());
    }

    @Test
    void testIntBinding() {
        var foo = new FooTest();
        foo.setIntValue(100);

        var intProperty = new SimpleIntegerProperty();
        var intBackedProperty = FieldBackedPropertyGenerator.integerFieldProperty(() -> foo.getIntValue(), value -> foo.setIntValue(value));

        intProperty.bindBidirectional(intBackedProperty);
        assertEquals(100, intProperty.get());
        intProperty.set(200);
        assertEquals(200, intBackedProperty.get());
        intBackedProperty.set(250);
        assertEquals(250, intProperty.get());
    }

    @Test
    void testBooleanBinding() {
        var foo = new FooTest();
        foo.setBoolValue(true);

        var boolProperty = new SimpleBooleanProperty();
        var boolBackedProperty = FieldBackedPropertyGenerator.booleanFieldProperty(() -> foo.isBoolValue(), value -> foo.setBoolValue(value));

        boolProperty.bindBidirectional(boolBackedProperty);
        assertTrue( boolProperty.get());
        boolProperty.set(false);
        assertFalse( boolBackedProperty.get());
        boolBackedProperty.set(true);
        assertTrue( boolProperty.get());
    }

    @Test
    void testDoubleBinding() {
        var foo = new FooTest();
        foo.setDoubleValue(1.234);

        var doubleProperty = new SimpleDoubleProperty();
        var doubleBackedProperty = FieldBackedPropertyGenerator.doubleFieldProperty(() -> foo.getDoubleValue(), value -> foo.setDoubleValue(value));

        doubleProperty.bindBidirectional(doubleBackedProperty);
        assertEquals(1.234, doubleProperty.get());
        doubleProperty.set(2.345);
        assertEquals(2.345, doubleBackedProperty.get());
        doubleBackedProperty.set(3.567);
        assertEquals( 3.567, doubleProperty.get());
    }

    @Test
    void testFloatBinding() {
        var foo = new FooTest();
        foo.setFloatValue(1.234f);

        var floatProperty = new SimpleFloatProperty();
        var floatBackedProperty = FieldBackedPropertyGenerator.floatFieldProperty(() -> foo.getFloatValue(), value -> foo.setFloatValue(value));

        floatProperty.bindBidirectional(floatBackedProperty);
        assertEquals(1.234, floatProperty.get(), 0.01);
        floatProperty.set(2.345f);
        assertEquals(2.345, floatBackedProperty.get(), 0.01);
        floatBackedProperty.set(3.567f);
        assertEquals( 3.567, floatProperty.get(), 0.01);
    }

    @Test
    void testObjectBinding() {
        var foo = new FooTest();
        var bar = new BarTest();
        bar.setBar("bar");
        foo.setBarTest(bar);

        var objProperty = new SimpleObjectProperty<BarTest>();
        var objBackedProperty = FieldBackedPropertyGenerator.objectFieldProperty(() -> foo.getBarTest(), value -> foo.setBarTest(value));

        objProperty.bindBidirectional(objBackedProperty);
        assertEquals("bar", objProperty.get().getBar());
        var baz = new BarTest("baz");
        objProperty.set(baz);
        assertEquals("baz", objBackedProperty.get().getBar());
        var bazz = new BarTest("bazz");
        objBackedProperty.set(bazz);
        assertEquals("bazz", objProperty.get().getBar());
    }
}
