package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.common.models.Recipe;
import g7.sp4.protocolHandling.AssmConnectionService;
import g7.sp4.protocolHandling.Flag;
import g7.sp4.services.IEventLoggingService;
import g7.sp4.services.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;

public class ComponentsAssemblePhase extends Phase{

    private int stateTracker = 0;

    private Flag assmConnectorBuildDone;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {
        switch (stateTracker) {
            case 0 -> {
                if (assmConnectorBuildDone==null) {
                    eventService.createNewEvent(
                            batch,
                            "Assembly Station starting build",
                            false,
                            (float) ((batch.getParts().indexOf(currentPart)+0.5)/batch.getParts().size()),
                            "Assembly Station has started building the part."
                    );
                    Recipe recipe = recipeService.getRecipeFor(currentPart);
                    if(recipe == null) {
                        eventService.createNewEvent(
                                batch,
                                "Recipe error",
                                true,
                                "No recipe was found."
                        );
                        return new PhaseUpdateResult(false, true);
                    }
                    assmConnectorBuildDone=assmConnector.build(recipe.getId().intValue());
                }

                if(assmConnectorBuildDone.get()) {
                    stateTracker++;
                }

                if(assmConnectorBuildDone.hasError()) {
                    throwErrorEvent(assmConnectorBuildDone, batch);
                    return new PhaseUpdateResult(false, true);
                }

            }

            case 1 -> {
                return new PhaseUpdateResult( true, false);
            }
        }

        return new PhaseUpdateResult(false, false);
    }
}
