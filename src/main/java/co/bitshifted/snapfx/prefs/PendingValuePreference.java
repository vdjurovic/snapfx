package co.bitshifted.snapfx.prefs;

public interface PendingValuePreference<T> {

  void setPendingValue(T value);

  boolean hasPendingValue();

  void clearPendingValue();

  void save();
}
