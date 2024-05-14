package co.bitshifted.snapfx.error;

public class ProcessExecutionException extends Exception {

  public ProcessExecutionException(Throwable cause) {
    super(cause);
  }

  public ProcessExecutionException(String message) {
    super(message);
  }
}
