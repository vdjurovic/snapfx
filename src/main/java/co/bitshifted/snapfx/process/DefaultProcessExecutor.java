package co.bitshifted.snapfx.process;

import co.bitshifted.snapfx.error.ProcessExecutionException;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class DefaultProcessExecutor implements ProcessExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProcessExecutor.class);

    private final ExecutorService executorService;

    @Inject
    public DefaultProcessExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Future<ProcessExecutionResult> executeExternalProcess(List<String> cmdLine, File workingDirectory, Map<String, String> environment) {
        return executorService.submit(() -> executeProcess(cmdLine, workingDirectory, environment));
    }

    @Override
    public Future<?> executeAndWait(List<String> cmdLine, File workingDirectory, Map<String, String> environment) throws ProcessExecutionException {
        var process = executeProcessAndWait(cmdLine, workingDirectory, environment);
        return executorService.submit(new ExecutionThread(process));
    }

    private ProcessExecutionResult executeProcess(List<String> cmdLine, File workingDirectory, Map<String, String> environment) {
        var pb = new ProcessBuilder(cmdLine);
        LOGGER.info("Running command {} in working directory {}", cmdLine, workingDirectory.getAbsolutePath());
        pb.directory(workingDirectory);
        pb.environment().clear();
        pb.environment().putAll(System.getenv());
        pb.environment().putAll(environment);
        String stdout = "";
        var stderr = "";
        int exitCode;
        try {
            var process = pb.start();
            exitCode = process.waitFor();
            if(exitCode == DEFAULT_SUCCESS_EXIT_CODE) {
                LOGGER.info("Command completed successfully");
            } else {
                LOGGER.error("Failed to run command");
            }
            stdout = readOutput(process.inputReader());
            stderr = readOutput(process.errorReader());
        } catch(IOException | InterruptedException ex) {
            LOGGER.error("Failed to run process", ex);
            exitCode = DEFAULT_FAILURE_EXIT_CODE;
        }
        return new ProcessExecutionResult(exitCode, stdout, stderr);
    }

    private Process executeProcessAndWait(List<String> cmdLine, File workingDirectory, Map<String, String> environment) throws ProcessExecutionException {
        var pb = new ProcessBuilder(cmdLine);
        LOGGER.info("Running command {} in working directory {}", cmdLine, workingDirectory.getAbsolutePath());
        pb.directory(workingDirectory);
        pb.environment().clear();
        pb.environment().putAll(System.getenv());
        pb.environment().putAll(environment);
        try {
            return pb.start();
        } catch(IOException ex) {
            LOGGER.error("Failed to execute process", ex);
            throw new ProcessExecutionException(ex);
        }

    }

    private String readOutput(BufferedReader reader) throws IOException {
        var sb = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
}
