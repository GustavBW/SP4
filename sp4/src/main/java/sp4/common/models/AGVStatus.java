package sp4.common.models;

public record AGVStatus(int battery, String programName, AGVState state, String timestamp, int code) {}
