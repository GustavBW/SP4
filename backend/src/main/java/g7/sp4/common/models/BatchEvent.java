package g7.sp4.common.models;

public class BatchEvent {

    private int eventId; //own serial event id - pk
    private int batchId; //id of batch that created said event
    private String name; //name of event
    private String description; //further description of event
    private boolean faulty; //if any error occurred
    private float progression; //as a percentage
    private long timestamp; //in ms from january 1. 1970


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFaulty() {
        return faulty;
    }

    public void setFaulty(boolean faulty) {
        this.faulty = faulty;
    }

    public float getProgression() {
        return progression;
    }

    public void setProgression(float progression) {
        this.progression = progression;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }



}
