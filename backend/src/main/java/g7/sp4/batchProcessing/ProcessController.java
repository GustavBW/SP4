package g7.sp4.batchProcessing;

import g7.sp4.common.models.Batch;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.AssmConnectionService;
import g7.sp4.protocolHandling.WHConnectionService;
import g7.sp4.services.IIngestService;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProcessController implements Runnable{

    private final Thread controlThread;
    private static ProcessController activeInstance;
    private volatile AtomicBoolean shouldRun = new AtomicBoolean(true);

    private final AGVConnectionService agvService;
    private final AssmConnectionService assmService;
    private final WHConnectionService whService;
    private final IIngestService ingest;

    public static void onNewBatchInIngest()
    {
        synchronized (activeInstance) {
            activeInstance.notify();
        }
    }

    public ProcessController(AGVConnectionService agvService, AssmConnectionService assmService, WHConnectionService whService, IIngestService ingestService)
    {
        System.out.println("ProcessController created");
        this.agvService = Objects.requireNonNull(agvService);
        this.assmService = Objects.requireNonNull(assmService);
        this.whService = Objects.requireNonNull(whService);
        this.ingest = Objects.requireNonNull(ingestService);

        activeInstance = this;
        controlThread = new Thread(this);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> shouldRun.set(false)));
    }

    private ProcessChain currentProcess;
    private boolean statesHaveReset = false;
    private final int pollingFrequency = 1000 / 10; //how many times a second the current chain should be updated

    @Override
    public void run() {
        System.out.println("ProcessController started");

        while(shouldRun.get())
        {
            if(currentProcess == null || (currentProcess.hasFinished() && statesHaveReset)) {
                getNextProcess();
            }else if(currentProcess != null && currentProcess.hasFinished()) {
                resetDevices();
            }else if (currentProcess != null){
                currentProcess.update();
            }

            try{
                synchronized(this) {
                    if(currentProcess == null){
                        System.out.println("ProcessController waiting indefinetly");
                        wait();
                    }else{
                        wait(pollingFrequency);
                    }
                }
            }catch (InterruptedException ignored){}//Interrupts happens when the "onNewBatchInIngest" is called.
        }

        System.out.println("ProcessController shutdown");
    }


    private void resetDevices()
    {

    }

    private void getNextProcess()
    {
        currentProcess = ingest.recieveNext();
        statesHaveReset = false;
    }

    public synchronized void start(){
        controlThread.start();
    }
}
