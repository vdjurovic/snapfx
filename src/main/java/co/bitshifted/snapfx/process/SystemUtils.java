package co.bitshifted.snapfx.process;

public final class SystemUtils {

    private SystemUtils() {

    }

    public static OperatingSystem operatingSystem() {
        var value = System.getProperty("os.name").toLowerCase();
       if(value.contains("win")) {
           return OperatingSystem.WINDOWS;
       } else if(value.contains("nix") || value.contains("nux") || value.contains("aix")) {
           return OperatingSystem.LINUX;
       } else if (value.contains("mac")) {
           return OperatingSystem.MAC;
       }
       throw new IllegalStateException("Unable to determine operating system from value: " + value);
    }
}
