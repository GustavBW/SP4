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
                eventService.createNewEvent(
                        batch,
                        "Preparing Recipe",
                        false,
                        (float) batch.getParts().indexOf(currentPart) / batch.getParts().size(),
                        "The recipe for part id: " + currentPart.getPartId() + " and preparing its components"
                );
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
                    eventService.createNewEvent(
                            batch,
                            "Warehouse Offloading Component " + componentList.get(componentTracker).getId(),
                            false,
                            "The warehouse is preparing the component and the AGV will pick it up momentarily."
                    );
                }
                if (prepareComponentFlag.get()) {
                    prepareComponentFlag = null;
                    preparePickupTracker++;
                    break;
                }
                if (prepareComponentFlag.hasError()) {
                    eventService.createNewEvent(batch, prepareComponentFlag.getError().name(), true, prepareComponentFlag.getError().description());
                    return new PhaseUpdateResult(false, true);
                }

            }
            case 1 -> {
                if (pickupComponentFlag == null) {
                    pickupComponentFlag = agvConnector.pickupAtWarehouse();
                    eventService.createNewEvent(
                            batch,
                            "AGV Picking Component " + componentList.get(componentTracker).getId(),
                            false,
                            "The agv is retrieving the component left by the warehouse."
                    );
                }

                if (pickupComponentFlag.get()) {
                    pickupComponentFlag = null;
                    preparePickupTracker++;
                    if (componentTracker >= componentList.size() - 1) {
                        stateTracker++;
                    }
                    break;
                }
                if (pickupComponentFlag.hasError()) {
                    eventService.createNewEvent(batch, pickupComponentFlag.getError().name(), true, pickupComponentFlag.getError().description());
                    return new PhaseUpdateResult(false, true);
                }

            }
        }
        return PhaseUpdateResult.FALSE_FALSE;
    }
}
