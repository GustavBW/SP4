package g7.sp4.batchProcessing;

import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.AssmConnectionService;
import g7.sp4.protocolHandling.WHConnectionService;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProcessController implements Runnable{

    private final Thread controlThread;
    private static ProcessController activeInstance;
    private volatile AtomicBoolean shouldRun = new AtomicBoolean(true);

    private final AGVConnectionService agvService;
    private final AssmConnectionService assmService;
    private final WHConnectionService whService;

    public static void onNewBatchInIngest()
    {
        synchronized (activeInstance) {
            activeInstance.notify();
        }
    }

    public ProcessController(AGVConnectionService agvService, AssmConnectionService assmService, WHConnectionService whService)
    {
        this.agvService = Objects.requireNonNull(agvService);
        this.assmService = Objects.requireNonNull(assmService);
        this.whService = Objects.requireNonNull(whService);

        activeInstance = this;
        controlThread = new Thread(this);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> shouldRun.set(false)));
    }


    @Override
    public void run() {
        System.out.println("ProcessController startup");

        while(shouldRun.get())
        {


            try{
                wait(10); //Interrupts happens when the "onNewBatchInIngest" is called.
            }catch (InterruptedException ignored){}
        }

        System.out.println("ProcessController shutdown");
    }
}
