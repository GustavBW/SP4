package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;

import g7.sp4.common.models.Component;
import g7.sp4.common.models.Recipe;
import g7.sp4.protocolHandling.AGVConnector;
import g7.sp4.protocolHandling.Flag;
import g7.sp4.protocolHandling.WHConnector;
import g7.sp4.services.IEventLoggingService;
import g7.sp4.services.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class LoadAGVAtWHPhase extends Phase {

    private final int componentTracker = 0;

    private List<Component> componentList;
    private int stateTracker = 0;
    private int preparePickupTracker = 0;
    private Flag prepareComponentFlag;
    private Flag pickupComponentFlag;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {

        switch (stateTracker) {
            case 0 -> {
                Recipe recipe = recipeService.getRecipeFor(currentPart);
                componentList = recipe.getComponentsRequired();
                stateTracker++;
            }
            case 1 -> {
                return preparePickupRoutine(batch);
            }
            case 2 -> {
                return new PhaseUpdateResult(true, false);
            }

        }
        return new PhaseUpdateResult(false, false);
    }

    private PhaseUpdateResult preparePickupRoutine(Batch batch) {
        switch (preparePickupTracker % 2) {
            case 0 -> {
                if (prepareComponentFlag == null) {
                    prepareComponentFlag = whConnector.prepareItem(componentList.get(componentTracker));
                }
                if (prepareComponentFlag.get()) {
                    prepareComponentFlag = null;
                    preparePickupTracker++;
                }
                if (prepareComponentFlag.hasError()) {
                    eventService.createNewEvent(batch, prepareComponentFlag.getError().name(), true, prepareComponentFlag.getError().description());
                    return new PhaseUpdateResult(false, true);
                }

            }
            case 1 -> {
                if (pickupComponentFlag == null) {
                    pickupComponentFlag = agvConnector.pickupAtWarehouse();

                }

                if (pickupComponentFlag.get()) {
                    pickupComponentFlag = null;
                    preparePickupTracker++;
                    if (componentTracker >= componentList.size() - 1) {
                        stateTracker++;
                    }
                }
                if (pickupComponentFlag.hasError()) {
                    eventService.createNewEvent(batch, pickupComponentFlag.getError().name(), true, pickupComponentFlag.getError().description());
                    return new PhaseUpdateResult(false, true);
                }

            }
        }
        return new PhaseUpdateResult(false, false);
    }
}
