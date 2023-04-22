package g7.sp4.services;

import g7.sp4.common.models.Component;
import g7.sp4.common.models.Recipe;
import g7.sp4.repositories.ComponentRepository;
import g7.sp4.util.responseUtil.ComponentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ComponentService implements IComponentService{

    @Autowired
    private ComponentRepository compRepo;

    /**
     * Creates a new component, saves it in the DB then returns it
     * @param name of the component
     * @param recipes the component is part of
     * @return as saved in db
     */
    public Component create(String name, Set<Recipe> recipes){
        return compRepo.save(new Component(name, recipes));
    }
    public void delete(Component comp){
        compRepo.delete(comp);
    }

    /**
     * Sets the values of the component to the new values - ignores null - then saves it in the DB.
     * @param comp
     * @param name
     * @param recipes
     * @return as updated and saved in DB
     */
    public Component update(Component comp, String name, Set<Recipe> recipes){
        if(name != null && !name.isEmpty())
            comp.setName(name);
        if(recipes != null && !recipes.isEmpty())
            comp.setRecipes(recipes);
        return compRepo.save(comp);
    }

    public ComponentResponse responseOf(Component component){
        return new ComponentResponse(component.getName(), component.getId());
    }

}
