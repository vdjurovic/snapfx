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

    @Test
    void testStringBindingUnidirectional() {
        var foo = new FooTest();
        foo.setStringValue("some string");

        var textProperty = new SimpleStringProperty(foo.getStringValue());
        var stringBackedProperty = FieldBackedPropertyGenerator.stringFieldProperty(() -> foo.getStringValue(), value -> foo.setStringValue(value));
        stringBackedProperty.bind(textProperty);
        assertEquals("some string", foo.getStringValue());
        assertEquals("some string", stringBackedProperty.get());
        textProperty.setValue("new string");
        assertEquals("new string", foo.getStringValue());
        assertEquals("new string", stringBackedProperty.get());
        textProperty.setValue("another string");
        assertEquals("another string", foo.getStringValue());
        assertEquals("another string", stringBackedProperty.get());

        // inverted bindings
        stringBackedProperty.unbind();
        textProperty = new SimpleStringProperty("");
        textProperty.bind(stringBackedProperty);
        assertEquals("another string", textProperty.get());
        stringBackedProperty.set("string 1");
        assertEquals("string 1", textProperty.get());
    }

    @Test
    void testBooleanBindingUnidirectional() {
        var foo = new FooTest();
        foo.setBoolValue(true);

        var boolProperty = new SimpleBooleanProperty(foo.isBoolValue());
        var boolBackedProperty = FieldBackedPropertyGenerator.booleanFieldProperty(() -> foo.isBoolValue(), value -> foo.setBoolValue(value));

        boolBackedProperty.bind(boolProperty);
        assertTrue(foo.isBoolValue());
        assertTrue(boolBackedProperty.get());
        boolProperty.set(false);
        assertFalse(boolBackedProperty.get());
        assertFalse(foo.isBoolValue());
        // inverted bindings
        boolBackedProperty.unbind();;
        boolProperty = new SimpleBooleanProperty();
        boolProperty.bind(boolBackedProperty);
        assertFalse(boolProperty.get());
        boolBackedProperty.set(true);
        assertTrue(boolProperty.get());
    }

    @Test
    void testIntegerBindingUnidirectional() {
        var foo = new FooTest();
        foo.setIntValue(5);

        var intProperty = new SimpleIntegerProperty(foo.getIntValue());
        var intBackedProperty = FieldBackedPropertyGenerator.integerFieldProperty(() -> foo.getIntValue(), value -> foo.setIntValue(value));
        intBackedProperty.bind(intProperty);
        assertEquals(5, foo.getIntValue());
        assertEquals(5, intBackedProperty.get());
        intProperty.setValue(6);
        assertEquals(6, foo.getIntValue());
        assertEquals(6, intBackedProperty.get());
        intProperty.setValue(7);
        assertEquals(7, foo.getIntValue());
        assertEquals(7, intBackedProperty.get());

        // inverted bindings
        intBackedProperty.unbind();
        intProperty = new SimpleIntegerProperty();
        intProperty.bind(intBackedProperty);
        assertEquals(7, intProperty.get());
        intBackedProperty.set(8);
        assertEquals(8, intProperty.get());
    }

    @Test
    void testFloatBindingUnidirectional() {
        var foo = new FooTest();
        foo.setFloatValue(1.2f);

        var floatProperty = new SimpleFloatProperty(foo.getFloatValue());
        var floatBackedProperty = FieldBackedPropertyGenerator.floatFieldProperty(() -> foo.getFloatValue(), value -> foo.setFloatValue(value));
        floatBackedProperty.bind(floatProperty);
        assertEquals(1.2, foo.getFloatValue(), 0.001);
        assertEquals(1.2, floatBackedProperty.get(), 0.001);
        floatProperty.setValue(2.3f);
        assertEquals(2.3, foo.getFloatValue(), 0.001);
        assertEquals(2.3f, floatBackedProperty.get(), 0.001);
        floatProperty.setValue(3.4f);
        assertEquals(3.4f, foo.getFloatValue());
        assertEquals(3.4f, floatBackedProperty.get());

        // inverted bindings
        floatBackedProperty.unbind();
        floatProperty = new SimpleFloatProperty();
        floatProperty.bind(floatBackedProperty);
        assertEquals(3.4f, floatProperty.get());
        floatBackedProperty.set(5.6f);
        assertEquals(5.6f, floatProperty.get());
    }

    @Test
    void testDoubleBindingUnidirectional() {
        var foo = new FooTest();
        foo.setDoubleValue(1.2);

        var doubleProperty = new SimpleDoubleProperty(foo.getDoubleValue());
        var doubleBackedProperty = FieldBackedPropertyGenerator.doubleFieldProperty(() -> foo.getDoubleValue(), value -> foo.setDoubleValue(value));
        doubleBackedProperty.bind(doubleProperty);
        assertEquals(1.2, foo.getDoubleValue(), 0.001);
        assertEquals(1.2, doubleBackedProperty.get(), 0.001);
        doubleProperty.setValue(2.3);
        assertEquals(2.3, foo.getDoubleValue(), 0.001);
        assertEquals(2.3, doubleBackedProperty.get(), 0.001);
        doubleProperty.setValue(3.4);
        assertEquals(3.4, foo.getDoubleValue(), 0.001);
        assertEquals(3.4, doubleBackedProperty.get(), 0.001);

        // inverted bindings
        doubleBackedProperty.unbind();
        doubleProperty = new SimpleDoubleProperty();
        doubleProperty.bind(doubleBackedProperty);
        assertEquals(3.4, doubleProperty.get(),0.001);
        doubleBackedProperty.set(5.6);
        assertEquals(5.6f, doubleProperty.get(), 0.001);
    }

    @Test
    void testObjectBindingUnidirectional() {
        var foo = new FooTest();
        var bar = new BarTest();
        bar.setBar("bar");
        foo.setBarTest(bar);

        var objProperty = new SimpleObjectProperty<>(foo.getBarTest());
        var objBackedProperty = FieldBackedPropertyGenerator.objectFieldProperty(() -> foo.getBarTest(), value -> foo.setBarTest(value));
        objBackedProperty.bind(objProperty);
        assertEquals("bar", foo.getBarTest().getBar());
        assertEquals("bar", objBackedProperty.get().getBar());
        var baz = new BarTest("baz");
        objProperty.setValue(baz);
        assertEquals("baz", foo.getBarTest().getBar());
        assertEquals("baz", objBackedProperty.get().getBar());
        var baz1 = new BarTest("baz1");
        objProperty.setValue(baz1);
        assertEquals("baz1", foo.getBarTest().getBar());
        assertEquals("baz1", objBackedProperty.get().getBar());

        // inverted bindings
        objBackedProperty.unbind();
        objProperty = new SimpleObjectProperty<>();
        objProperty.bind(objBackedProperty);
        assertEquals("baz1", objProperty.get().getBar());
        var baz2 = new BarTest("baz2");
        objBackedProperty.set(baz2);
        assertEquals("baz2", objProperty.get().getBar());
    }
}
