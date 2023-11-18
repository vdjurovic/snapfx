package co.bitshifted.snapfx.process;

public record ProcessExecutionResult(int exitCode, String stdout, String stdErr) {
}
