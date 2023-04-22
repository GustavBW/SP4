package g7.sp4.batchProcessing;

import g7.sp4.common.models.Batch;

import java.util.concurrent.ConcurrentLinkedQueue;

public class BatchIngest {

    private final ConcurrentLinkedQueue<ProcessChain> queue = new ConcurrentLinkedQueue<>();

    public boolean accept(ProcessChain chain)
    {
        return queue.add(chain);
    }

    public ProcessChain recieveNext()
    {
        return queue.poll();
    }

}
