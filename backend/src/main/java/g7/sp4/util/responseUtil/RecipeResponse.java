package g7.sp4.util.responseUtil;

import java.util.Map;

public record RecipeResponse(long id, PartResponse part, Map<Long,Integer> components){}