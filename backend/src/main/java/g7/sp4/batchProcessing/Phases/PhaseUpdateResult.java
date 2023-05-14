package g7.sp4.batchProcessing.Phases;

public record PhaseUpdateResult(boolean hasFinished, boolean fatalError){
    public static final PhaseUpdateResult FALSE_FALSE = new PhaseUpdateResult(false, false);
}
