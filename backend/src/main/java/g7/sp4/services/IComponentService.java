package g7.sp4.services;

import g7.sp4.common.models.Component;
import g7.sp4.common.models.Recipe;
import g7.sp4.util.responseUtil.ComponentResponse;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface IComponentService {
    Component create(String name, Set<Recipe> recipes);
    void delete(Component comp);
    Component update(Component comp, String name, Set<Recipe> recipes);

    /**
     * Recursion safe
     * @param component
     * @return
     */
    ComponentResponse responseOf(Component component);
}
