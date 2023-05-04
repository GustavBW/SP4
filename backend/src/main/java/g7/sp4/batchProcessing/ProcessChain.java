package g7.sp4.batchProcessing;

import g7.sp4.batchProcessing.Phases.*;
import g7.sp4.common.models.Batch;
import g7.sp4.services.IEventLoggingService;

public class ProcessChain {
    private Phases phases = new Phases(new AGVGoChargePhase(), new AGVReturnToWHPhase(), new ComponentsAssemblePhase(), new LoadAGVAtWHPhase(), new LoadAssemblerPhase(), new LoadWHWithPartPhase(), new TransportToAssemblerPhase(), new LoadAGVFromAssemblerPhase());
    private Batch batch;
    private boolean hasFinished;
    private IEventLoggingService loggingService;
    private int currentPhaseIndex = 0;
    private int partOfBatchIndex = 0;

    public ProcessChain(Batch batch) {
        this.batch = batch;
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public boolean update() {
        // false if batch is aborted
        switch (currentPhaseIndex) {
            case 0 -> {
                evaluatePhaseResult(phases.loadAGVAtWHPhase.update(batch, batch.getParts().get(partOfBatchIndex)), "Load AGV at WH");
            }
            case 1 -> {
                // Charge AGV
                evaluatePhaseResult(phases.agvGoChargePhase.update(batch, batch.getParts().get(partOfBatchIndex)), "AGV go to charge");
            }
            case 2 -> {
                // Transport to assembler
                evaluatePhaseResult(phases.transportToAssemblerPhase.update(batch, batch.getParts().get(partOfBatchIndex)), "AGV to assembler");
            }
            case 3 -> {
                // Load assembler
                evaluatePhaseResult(phases.loadAssemblerPhase.update(batch, batch.getParts().get(partOfBatchIndex)), "loadAssembler");
            }
            case 4 -> {
                // assemble part from components
                evaluatePhaseResult(phases.componentsAssemblePhase.update(batch, batch.getParts().get(partOfBatchIndex)), "assemble part");
            }
            case 5 -> {
                // Load AGV with part from assembler
                evaluatePhaseResult(phases.loadAGVFromAssemblerPhase.update(batch, batch.getParts().get(partOfBatchIndex)), "loading agv from assembler");
            }
            case 6 -> {
                // charge AGV ?
                evaluatePhaseResult(phases.agvGoChargePhase.update(batch, batch.getParts().get(partOfBatchIndex)), "AGV go to charge2");
            }
            case 7 -> {
                // Transport part to WH
                evaluatePhaseResult(phases.agvReturnToWHPhase().update(batch, batch.getParts().get(partOfBatchIndex)), "agv return to WH");
            }
            case 8 -> {
                // Unload AGV at WH
                evaluatePhaseResult(phases.loadWHWIthPartPhase.update(batch, batch.getParts().get(partOfBatchIndex)), "load WH with part");

            }
        }
        if (currentPhaseIndex > 8 && !(partOfBatchIndex < batch.getParts().size())) {
            hasFinished = true;
        } else if (currentPhaseIndex > 8) {
            currentPhaseIndex = 0;
            partOfBatchIndex++;
            resetPhases();
        }
        return true;
    }

    private void evaluatePhaseResult(PhaseUpdateResult phaseUpdateResult, String phaseName) {
        if (phaseUpdateResult.hasFinished() && !phaseUpdateResult.fatalError()) {
            currentPhaseIndex++;
        } else if (phaseUpdateResult.fatalError()) {
            loggingService.createNewEvent(batch, "Batch aborted", true, "Fatal error in " + phaseName);
            hasFinished = true;
        }
    }

    public void setLoggingService(IEventLoggingService loggingService) {
        this.loggingService = loggingService;

    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    private void resetPhases() {
        phases = new Phases(new AGVGoChargePhase(), new AGVReturnToWHPhase(), new ComponentsAssemblePhase(), new LoadAGVAtWHPhase(), new LoadAssemblerPhase(), new LoadWHWithPartPhase(), new TransportToAssemblerPhase(), new LoadAGVFromAssemblerPhase());
    }

    private record Phases(AGVGoChargePhase agvGoChargePhase, AGVReturnToWHPhase agvReturnToWHPhase,
                          ComponentsAssemblePhase componentsAssemblePhase, LoadAGVAtWHPhase loadAGVAtWHPhase,
                          LoadAssemblerPhase loadAssemblerPhase, LoadWHWithPartPhase loadWHWIthPartPhase,
                          TransportToAssemblerPhase transportToAssemblerPhase,
                          LoadAGVFromAssemblerPhase loadAGVFromAssemblerPhase) {
    }
}
