package g7.sp4.util.responseUtil;

import java.util.List;

public record PartResponse(long id, String name, int count, String description, List<Long> batches){}