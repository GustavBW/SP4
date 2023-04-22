package g7.sp4.services;

import g7.sp4.common.models.Part;
import g7.sp4.common.models.Recipe;
import g7.sp4.util.responseUtil.PartResponse;
import org.springframework.stereotype.Service;

@Service
public interface IPartService {
    Part create(String name, int count, String description, Recipe recipe);
    void delete(Part part);
    Part update(Part part, String name, int count, String description, Recipe recipe);

    /**
     * Recursion safe response type
     * @param part
     * @return
     */
    PartResponse responseOf(Part part);
}
