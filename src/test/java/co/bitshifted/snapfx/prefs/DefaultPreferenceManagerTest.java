package co.bitshifted.snapfx.prefs;

import static org.junit.jupiter.api.Assertions.*;

import co.bitshifted.snapfx.di.DefaultApplicationConfig;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.UUID;
import java.util.prefs.Preferences;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultPreferenceManagerTest {

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

  @Test
  void saveMultiValuePreferenceSuccess() throws Exception {
    var manager = new DefaultPreferenceManager(new DefaultApplicationConfig());
    var defaultValue = List.of(new FooRecord("name", 10));
    var entry = manager.getMultiValuePreferenceEntry(rootNode, "multi-val-test", defaultValue);
    assertEquals(1, entry.getValue().size());
    var out = entry.getValue().iterator().next();
    assertEquals("name", out.name());
    assertEquals(10, out.number());
    // update value
    var newVal = List.of(new FooRecord("foo1", 10), new FooRecord("foo2", 20));
    entry.getProperty().setValue(newVal);
    entry.save();
    // verify preference state
    var prefsNode = Preferences.userRoot().node(rootNode);
    var data = prefsNode.getByteArray("multi-val-test", new byte[0]);
    try (var ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
      var prefList = (List<FooRecord>) ois.readObject();
      assertEquals(2, prefList.size());
      var foo1 = prefList.get(0);
      assertEquals("foo1", foo1.name());
      assertEquals(10, foo1.number());
    }
  }

  public class TestListener implements ChangeListener<Number> {

    private int count = 0;

    @Override
    public void changed(
        ObservableValue<? extends Number> observableValue, Number integer, Number t1) {
      count++;
    }

    public int getCount() {
      return count;
    }
  }
}
