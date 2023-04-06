package g7.sp4.common.models;

import java.util.Map;

public class Recipe {


    private Part partMade;
    private Map<Component, Integer> componentsRequired;

    public Part getPartMade() {
        return partMade;
    }

    public void setPartMade(Part partMade) {
        this.partMade = partMade;
    }

    public Map<Component, Integer> getComponentsRequired() {
        return componentsRequired;
    }

    public void setComponentsRequired(Map<Component, Integer> componentsRequired) {
        this.componentsRequired = componentsRequired;
    }

}
