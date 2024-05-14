package co.bitshifted.snapfx.prefs;

import co.bitshifted.snapfx.di.ApplicationConfig;
import jakarta.inject.Inject;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

public class DefaultPreferenceManager implements PreferenceManager {

  private final ApplicationConfig applicationConfig;
  private final Map<String, Object> preferenceCache;

  @Inject
  public DefaultPreferenceManager(ApplicationConfig applicationConfig) {
    this.applicationConfig = applicationConfig;
    this.preferenceCache = new HashMap<>();
  }

  @Override
  public IntegerPreferenceEntry getIntegerPreference(
      String root, String name, Integer defaultValue) {
    var key = root + "-" + name;
    var cached = preferenceCache.get(key);
    if (cached != null) {
      return (IntegerPreferenceEntry) cached;
    }
    var node = Preferences.userRoot().node(root);
    var value = node.getInt(name, defaultValue);
    var pref = new IntegerPreferenceEntry(root, name, node.getInt(name, value));
    preferenceCache.put(key, pref);
    return pref;
  }

  @Override
  public IntegerPreferenceEntry getIntegerPreference(String name, Integer defaultValue) {
    return getIntegerPreference(applicationConfig.defaultPreferenceRootNode(), name, defaultValue);
  }

  @Override
  public LongPreferenceEntry getLongPreference(String root, String name, Long defaultValue) {
    var node = Preferences.userRoot().node(root);
    var value = node.getLong(name, defaultValue);
    return new LongPreferenceEntry(root, name, node.getLong(name, value));
  }

  @Override
  public FloatPreferenceEntry getFloatPreference(String root, String name, Float defaultValue) {
    var node = Preferences.userRoot().node(root);
    var value = node.getFloat(name, defaultValue);
    return new FloatPreferenceEntry(root, name, node.getFloat(name, value));
  }

  @Override
  public DoublePreferenceEntry getDoublePreferenceEntry(
      String root, String name, Double defaultValue) {
    var key = root + "-" + name;
    var cached = preferenceCache.get(key);
    if (cached != null) {
      return (DoublePreferenceEntry) cached;
    }
    var node = Preferences.userRoot().node(root);
    var value = node.getDouble(name, defaultValue);
    var pref = new DoublePreferenceEntry(root, name, node.getDouble(name, value));
    preferenceCache.put(key, pref);
    return pref;
  }

  @Override
  public DoublePreferenceEntry getDoublePreferenceEntry(String name, Double defaultValue) {
    return getDoublePreferenceEntry(
        applicationConfig.defaultPreferenceRootNode(), name, defaultValue);
  }

  @Override
  public StringPreferenceEntry getStringPreferenceEntry(
      String root, String name, String defaultValue) {
    var key = root + "-" + name;
    var cached = preferenceCache.get(key);
    if (cached != null) {
      return (StringPreferenceEntry) cached;
    }
    var node = Preferences.userRoot().node(root);
    var value = node.get(name, defaultValue);
    var pref = new StringPreferenceEntry(root, name, node.get(name, value));
    preferenceCache.put(key, pref);
    return pref;
  }

  @Override
  public StringPreferenceEntry getStringPreferenceEntry(String name, String defaultValue) {
    return getStringPreferenceEntry(
        applicationConfig.defaultPreferenceRootNode(), name, defaultValue);
  }

  @Override
  public BooleanPreferenceEntry getBooleanPreferenceEntry(
      String root, String name, Boolean defaultValue) {
    var key = root + "-" + name;
    var cached = preferenceCache.get(key);
    if (cached != null) {
      return (BooleanPreferenceEntry) cached;
    }
    var node = Preferences.userRoot().node(root);
    var value = node.getBoolean(name, defaultValue);
    var pref = new BooleanPreferenceEntry(root, name, node.getBoolean(name, value));
    preferenceCache.put(key, pref);
    return pref;
  }

  @Override
  public BooleanPreferenceEntry getBooleanPreferenceEntry(String name, Boolean defaultValue) {
    return getBooleanPreferenceEntry(
        applicationConfig.defaultPreferenceRootNode(), name, defaultValue);
  }

  @Override
  public <T extends Serializable> MultiValuePreferenceEntry<T> getMultiValuePreferenceEntry(
      String root, String name, Collection<T> defaultValue) {
    var key = root + "-" + name;
    var cached = preferenceCache.get(key);
    if (cached != null) {
      return (MultiValuePreferenceEntry<T>) cached;
    }

    var node = Preferences.userRoot().node(root);
    var value = node.getByteArray(name, getObjectBytes(defaultValue));
    try (var ois = new ObjectInputStream(new ByteArrayInputStream(value))) {
      var pref = new MultiValuePreferenceEntry<>(root, name, (Collection<T>) ois.readObject());
      preferenceCache.put(key, pref);
      return pref;
    } catch (IOException | ClassNotFoundException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public <T extends Serializable> MultiValuePreferenceEntry<T> getMultiValuePreferenceEntry(
      String name, Collection<T> defaultValue) {
    return getMultiValuePreferenceEntry(
        applicationConfig.defaultPreferenceRootNode(), name, defaultValue);
  }

  private byte[] getObjectBytes(Collection object) {
    var bytes = new ByteArrayOutputStream();
    try (var oos = new ObjectOutputStream(bytes)) {
      oos.writeObject(object);
      return bytes.toByteArray();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}
