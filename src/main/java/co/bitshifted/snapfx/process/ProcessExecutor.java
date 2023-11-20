package co.bitshifted.snapfx.process;

import co.bitshifted.snapfx.error.ProcessExecutionException;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public interface ProcessExecutor {

    int DEFAULT_SUCCESS_EXIT_CODE = 0;
    int DEFAULT_FAILURE_EXIT_CODE = 1;

    Future<ProcessExecutionResult> executeExternalProcess(List<String> cmdLine, File workingDirectory, Map<String, String> environment);

    Future<Process> executeAndWait(List<String> cmdLine, File workingDirectory, Map<String, String> environment) throws ProcessExecutionException;
}
