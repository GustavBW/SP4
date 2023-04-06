package g7.sp4.common.models;

import java.util.Map;

public class Batch {

    private int id;
    private boolean hasCompleted = false;
    private String employeeId;
    private Map<Part, Integer> parts;

}
