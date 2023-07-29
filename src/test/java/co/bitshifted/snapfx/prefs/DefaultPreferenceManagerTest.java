package co.bitshifted.snapfx.prefs;

import co.bitshifted.snapfx.di.DefaultApplicationConfig;
import co.bitshifted.snapfx.prefs.DefaultPreferenceManager;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultPreferenceManagerTest {

    private String rootNode;

    @BeforeEach
    void setup() {
        rootNode = UUID.randomUUID().toString();
    }

    @AfterEach
    void cleanup() throws Exception {
        var node = Preferences.userRoot().node(rootNode);
        node.removeNode();
    }


    @Test
    void getIntegerPreferenceSuccess() {
        var manager = new DefaultPreferenceManager(new DefaultApplicationConfig());
        var entry = manager.getIntegerPreference("my/root", "pref-name", 5);
        assertNotNull(entry);
        assertEquals(5, entry.getValue());
    }

    @Test
    void saveIntegerPreferenceSuccess() {
        var manager = new DefaultPreferenceManager(new DefaultApplicationConfig());
        var entry = manager.getIntegerPreference(rootNode, "test-entry", 12);
        entry.save();
        var node = Preferences.userRoot().node(rootNode);
        var savedValue = node.getInt("test-entry", 0);
        assertEquals(12, savedValue);
        // update value
        entry.save(15);
        savedValue = node.getInt("test-entry", 0);
        assertEquals(15, savedValue);
    }

    @Test
    void updatePreferenceTriggersListener() throws Exception {
        var manager = new DefaultPreferenceManager(new DefaultApplicationConfig());
        var entry = manager.getIntegerPreference(rootNode, "test-entry", 12);
        var listener = new TestListener();
        entry.getProperty().addListener(listener);
        entry.save(20);
        var node = Preferences.userRoot().node(rootNode);
        var savedValue = node.getInt("test-entry", 0);
        assertEquals(20, savedValue);
        assertEquals(1, listener.getCount());
    }

    public class TestListener implements ChangeListener<Number> {

        private int count = 0;

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number integer, Number t1) {
            count++;
        }

        public int getCount() {
            return count;
        }
    }
}
