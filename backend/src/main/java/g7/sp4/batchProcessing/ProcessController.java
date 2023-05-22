package g7.sp4.batchProcessing;

import g7.sp4.batchProcessing.Phases.PhaseUpdateResult;
import g7.sp4.common.models.Batch;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.AssmConnectionService;
import g7.sp4.protocolHandling.WHConnectionService;
import g7.sp4.repositories.BatchRepository;
import g7.sp4.repositories.PartRepository;
import g7.sp4.services.IEventLoggingService;
import g7.sp4.services.IIngestService;
import g7.sp4.services.IRecipeService;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProcessController implements Runnable {

    private static ProcessController activeInstance;
    private final Thread controlThread;

    private final AGVConnectionService agvService;
    private final AssmConnectionService assmService;
    private final WHConnectionService whService;
    private final IIngestService ingest;
    private final IEventLoggingService loggingService;
    private final IRecipeService recipeService;
    private final PartRepository partRepo;
    private final DeviceResetService resetService;
    private final BatchRepository batchRepo;

    private final int pollingFrequency = 1000 / 10; //how many times a second the current chain should be updated
    private volatile AtomicBoolean shouldRun = new AtomicBoolean(true);
    private ProcessChain currentProcess;
    private boolean statesHaveReset = false;

    public ProcessController(AGVConnectionService agvService,
                             AssmConnectionService assmService,
                             WHConnectionService whService,
                             IIngestService ingestService,
                             IEventLoggingService loggingService,
                             IRecipeService recipeService,
                             PartRepository partRepo,
                             BatchRepository batchRepo
    ) {
        this.agvService = Objects.requireNonNull(agvService);
        this.assmService = Objects.requireNonNull(assmService);
        this.whService = Objects.requireNonNull(whService);
        this.ingest = Objects.requireNonNull(ingestService);
        this.loggingService = Objects.requireNonNull(loggingService);
        this.recipeService = Objects.requireNonNull(recipeService);
        this.partRepo = Objects.requireNonNull(partRepo);
        this.batchRepo = Objects.requireNonNull(batchRepo);

        this.resetService = new DeviceResetService();
        resetService.setEventService(loggingService);
        resetService.setAgvConnector(agvService);
        resetService.setAssmConnector(assmService);
        resetService.setWhConnector(whService);

        activeInstance = this;
        controlThread = new Thread(this);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> shouldRun.set(false)));
    }

    public static void onNewBatchInIngest() {
        synchronized (activeInstance) {
            System.out.println("ProcessController was notified about a new batch in ingest.");
            activeInstance.notify();
        }
    }

    @Override
    public void run() {
        System.out.println("ProcessController started");

        while (shouldRun.get()) {
            if (currentProcess == null || (currentProcess.hasFinished() && statesHaveReset)) {
                getNextProcess();
            } else if (currentProcess != null && currentProcess.hasFinished()) {
                resetDevices();
            } else if (currentProcess != null) {
                currentProcess.update();
                // processchain aborted
            }

            try {
                synchronized (this) {
                    if (currentProcess == null) {
                        System.out.println("ProcessController waiting indefinetly");
                        wait();
                    } else {
                        wait(pollingFrequency);
                    }
                }
            } catch (InterruptedException ignored) {
            }//Interrupts happens when the "onNewBatchInIngest" is called.
        }

        if (currentProcess != null && !currentProcess.hasFinished()) {
            loggingService.createNewEvent(
                    currentProcess.getBatch(),
                    "Unplanned System Shutdown",
                    true,
                    "The System has experienced an unintentional shutdown and the batch is lost"
            );
        }

        System.out.println("ProcessController shutdown");
    }

    private void resetDevices() {
        PhaseUpdateResult result = resetService.update();
        if(result.fatalError()){
            shouldRun.set(false);
            System.out.println("DeviceResetService has failed. Terminating automation.");
        }
        statesHaveReset = result.hasFinished();
    }

    private void getNextProcess() {
        currentProcess = ingest.recieveNext();
        statesHaveReset = false;

        if (currentProcess != null) {
            loggingService.createNewEvent(
                    currentProcess.getBatch(),
                    "Batch Pulled From Ingest",
                    false,
                    0f,
                    "The ProcessController has pulled this batch from the ingest and will now process its ProcessChain"
            );

            currentProcess.setLoggingService(loggingService);
            currentProcess.setAgvConnector(agvService);
            currentProcess.setAssmConnector(assmService);
            currentProcess.setWhConnector(whService);
            currentProcess.setRecipeService(recipeService);
            currentProcess.setPartRepo(partRepo);
            currentProcess.setBatchRepo(batchRepo);

            currentProcess.updateServices();
        }
    }

    public synchronized void start() {
        controlThread.start();
    }
}
