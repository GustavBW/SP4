public class ProcessController implements Runnable{

    private static final ProcessController instance = new ProcessController();
    private static final Thread controlThread = new Thread(instance);
    private static volatile boolean shouldRun = true;
    static{
        controlThread.start();
    }

    public static void onNewBatchInIngest()
    {
        synchronized (instance) {
            instance.notify();
        }
    }


    @Override
    public void run() {
        System.out.println("ProcessController startup");

        while(shouldRun)
        {

        }

        System.out.println("ProcessController shutdown");
    }
}
