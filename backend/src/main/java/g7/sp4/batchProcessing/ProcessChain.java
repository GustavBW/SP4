package g7.sp4.batchProcessing;

import g7.sp4.common.models.Batch;

public class ProcessChain {
    private Batch batch;

    public ProcessChain(Batch batch){
        this.batch = batch;
    }

    public boolean hasFinished()
    {
        return false;
    }


    public void update(){

    }
    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }
}
