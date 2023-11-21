package co.bitshifted.snapfx.process;

import co.bitshifted.snapfx.error.ProcessExecutionException;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
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
    public Future<Process> executeAndWait(List<String> cmdLine, File workingDirectory, Map<String, String> environment) {
        return executorService.submit(() -> executeProcessAndWait(cmdLine, workingDirectory, environment));
    }

    @Override
    public void launchDefaultApplication(Path filePath) throws ProcessExecutionException {
        launchDefaultApplication(filePath.toFile().getAbsolutePath());
    }

    @Override
    public void launchDefaultApplication(URI uri) throws ProcessExecutionException {
        launchDefaultApplication(uri.toString());
    }

    private void launchDefaultApplication(String target) throws ProcessExecutionException {
        var cmdLine = switch (SystemUtils.operatingSystem()) {
            case LINUX -> List.of("xdg-open", target);
            case MAC -> List.of("open", target);
            case WINDOWS -> List.of("start", target);
        };
        var result = executeExternalProcess(cmdLine, new File(System.getProperty("user.dir")), Map.of());
        try {
            if(result.get().exitCode() != DEFAULT_SUCCESS_EXIT_CODE) {
                throw new ProcessExecutionException(result.get().stdErr());
            }
        } catch(ExecutionException | InterruptedException ex) {
            throw new ProcessExecutionException(ex);
        }
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
