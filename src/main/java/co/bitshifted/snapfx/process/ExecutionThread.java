package co.bitshifted.snapfx.process;

import java.util.concurrent.TimeUnit;

public class ExecutionThread extends Thread {

    private final Process process;

    public ExecutionThread(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
       while (process.isAlive()) {
           if(isInterrupted()) {
               process.destroy();
               return;
           }
           try {
               process.waitFor(10, TimeUnit.SECONDS);
           } catch(InterruptedException ex) {
               throw new RuntimeException(ex);
           }

       }
    }
}
