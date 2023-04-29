package g7.sp4.util.responseUtil;

import java.util.Map;

public record BatchResponse(long id, boolean hasCompleted, String employee, Map<PartResponse, Integer> partsCountMap) {
}
