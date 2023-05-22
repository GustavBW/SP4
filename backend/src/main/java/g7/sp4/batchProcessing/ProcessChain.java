package g7.sp4.batchProcessing;

import g7.sp4.batchProcessing.Phases.*;
import g7.sp4.common.models.Batch;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.AssmConnectionService;
import g7.sp4.protocolHandling.WHConnectionService;
import g7.sp4.repositories.BatchRepository;
import g7.sp4.repositories.PartRepository;
import g7.sp4.services.IEventLoggingService;
import g7.sp4.services.IRecipeService;

import java.util.List;

public class ProcessChain {
    private Phases phases;
    private Batch batch;
    private boolean hasFinished;
    private IEventLoggingService loggingService;
    private int currentPhaseIndex = 0;
    private int partOfBatchIndex = 0;

    private AGVConnectionService agvConnector;
    private AssmConnectionService assmConnector;
    private WHConnectionService whConnector;
    private IRecipeService recipeService;
    private PartRepository partRepo;
    private BatchRepository batchRepo;

    public ProcessChain(Batch batch) {
        this.batch = batch;
        this.phases = resetPhases();
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public boolean update() {
        // false if batch is aborted
        switch (currentPhaseIndex) {
            case 0 -> { // 0 / 9
                evaluatePhaseResult(phases.loadAGVAtWHPhase.update(batch, batch.getParts().get(partOfBatchIndex)), "Load AGV at WH");
            }
            case 1 -> { // 1 / 9
                // Charge AGV
                evaluatePhaseResult(phases.agvGoChargePhase.update(batch, batch.getParts().get(partOfBatchIndex)), "AGV go to charge");
            }
            case 2 -> { // 2 / 9
                // Transport to assembler
                evaluatePhaseResult(phases.transportToAssemblerPhase.update(batch, batch.getParts().get(partOfBatchIndex)), "AGV to assembler");
            }
            case 3 -> { // 3 / 9
                // Load assembler
                evaluatePhaseResult(phases.loadAssemblerPhase.update(batch, batch.getParts().get(partOfBatchIndex)), "loadAssembler");
            }
            case 4 -> { // 4 / 9
                // assemble part from components
                evaluatePhaseResult(phases.componentsAssemblePhase.update(batch, batch.getParts().get(partOfBatchIndex)), "assemble part");
            }
            case 5 -> { // 5 / 9
                // Load AGV with part from assembler
                evaluatePhaseResult(phases.loadAGVFromAssemblerPhase.update(batch, batch.getParts().get(partOfBatchIndex)), "loading agv from assembler");
            }
            case 6 -> { // 6 / 9
                // charge AGV ?
                evaluatePhaseResult(phases.agvGoChargePhase.update(batch, batch.getParts().get(partOfBatchIndex)), "AGV go to charge2");
            }
            case 7 -> { // 7 / 9
                // Transport part to WH
                evaluatePhaseResult(phases.agvReturnToWHPhase().update(batch, batch.getParts().get(partOfBatchIndex)), "agv return to WH");
            }
            case 8 -> { // 8 / 9
                // Unload AGV at WH
                evaluatePhaseResult(phases.loadWHWIthPartPhase.update(batch, batch.getParts().get(partOfBatchIndex)), "load WH with part");
            }
        }
        if (currentPhaseIndex > 8 && partOfBatchIndex +1 >= batch.getParts().size()) {
            hasFinished = true;
            batch.setHasCompleted(true);
            batchRepo.save(batch);
            loggingService.createNewEvent(
                    batch,
                    "Batch Complete",
                    false,
                    1f,
                    "All parts have been assembled and are now stored in the warehouse."
            );
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

    public void setAgvConnector(AGVConnectionService agvConnector) {
        this.agvConnector = agvConnector;
    }

    public void setAssmConnector(AssmConnectionService assmConnector) {
        this.assmConnector = assmConnector;
    }

    public void setWhConnector(WHConnectionService whConnector) {
        this.whConnector = whConnector;
    }

    public void setRecipeService(IRecipeService recipeService){
        this.recipeService = recipeService;
    }

    public void setPartRepo(PartRepository partRepo){this.partRepo = partRepo;}

    public void setBatchRepo(BatchRepository batchRepo){this.batchRepo = batchRepo;}

    public void updateServices() {
        for (Phase phase : List.of(phases.componentsAssemblePhase(), phases.loadAssemblerPhase(), phases.agvGoChargePhase(),
                                    phases.transportToAssemblerPhase(), phases.loadAGVAtWHPhase(), phases.loadAGVFromAssemblerPhase(),
                                    phases.loadWHWIthPartPhase(), phases.agvReturnToWHPhase())) {
            phase.setAgvConnector(agvConnector);
            phase.setAssmConnector(assmConnector);
            phase.setWhConnector(whConnector);
            phase.setEventService(loggingService);
            phase.setRecipeService(recipeService);
            phase.setPartRepository(partRepo);
        }
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    private Phases resetPhases() {
        phases = new Phases(new AGVGoChargePhase(), new AGVReturnToWHPhase(), new ComponentsAssemblePhase(), new LoadAGVAtWHPhase(), new LoadAssemblerPhase(), new LoadWHWithPartPhase(), new TransportToAssemblerPhase(), new LoadAGVFromAssemblerPhase());
        updateServices();
        return phases;
    }

    private record Phases(AGVGoChargePhase agvGoChargePhase, AGVReturnToWHPhase agvReturnToWHPhase,
                          ComponentsAssemblePhase componentsAssemblePhase, LoadAGVAtWHPhase loadAGVAtWHPhase,
                          LoadAssemblerPhase loadAssemblerPhase, LoadWHWithPartPhase loadWHWIthPartPhase,
                          TransportToAssemblerPhase transportToAssemblerPhase,
                          LoadAGVFromAssemblerPhase loadAGVFromAssemblerPhase) {
    }
}
