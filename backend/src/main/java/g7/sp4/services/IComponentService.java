package g7.sp4.services;

import g7.sp4.common.models.Component;
import g7.sp4.common.models.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface IComponentService {
    Component create(String name, Set<Recipe> recipes);
    void delete(Component comp);
    Component update(Component comp, String name, Set<Recipe> recipes);
}
