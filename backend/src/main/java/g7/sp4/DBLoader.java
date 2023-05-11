package g7.sp4;

import g7.sp4.common.models.Component;
import g7.sp4.common.models.Part;
import g7.sp4.common.models.Recipe;
import g7.sp4.protocolHandling.WHConnectionService;
import g7.sp4.repositories.*;
import g7.sp4.services.IComponentService;
import g7.sp4.services.IPartService;
import g7.sp4.services.IRecipeService;
import g7.sp4.util.ArrayUtil;
import org.springframework.stereotype.Service;

import java.util.*;

public class DBLoader {

    //Fills the db with recipes and stuff
    private final IComponentService compService;
    private final IRecipeService recipeService;
    private final IPartService partService;
    private final WHConnectionService whConnector;

    public DBLoader(IComponentService compService, IRecipeService recipeService, IPartService partService, WHConnectionService whConnector)
    {
        this.compService =  Objects.requireNonNull(compService);
        this.recipeService = Objects.requireNonNull(recipeService);
        this.partService = Objects.requireNonNull(partService);
        this.whConnector = whConnector;
    }

    public void run()
    {
        System.out.println("DBLoader running");
        List<Component> components = loadComponentPool();
        List<Recipe> recipes = loadRecipePool();
        List<Part> parts = loadPartPool(components,recipes);
        whConnector.loadComponents(
                components
        );
        System.out.println("DBLoader finished");
    }

    private List<Component> loadComponentPool(){
        List<Component> asSaved = new ArrayList<>();
        for(Component c : componentPool)
            asSaved.add(compService.update(c,null,null));
        return asSaved;
    }

    private List<Recipe> loadRecipePool(){
        List<Recipe> recipes = new ArrayList<>();
        for(int i = 0; i < 10; i++)
            recipes.add(recipeService.create(null,null));
        return recipes;
    }

    private List<Part> loadPartPool(List<Component> components, List<Recipe> recipes){
        List<Part> toReturn = new ArrayList<>();
        for(int i = 0; i < recipes.size(); i++){
            List<Component> componentSubset = getRandomSubsetOf(components, 2);
            Recipe recipe = recipes.get(i);
            Part part = partService.create(
                    "Drone Assembly " + i,
                    (int) Math.floor(Math.random()*10),
                    "Magical Drone Assembly containing: " + ArrayUtil.arrayJoinWith(
                            componentSubset, Component::getName,", "
                    ) + ".",
                    recipe
                    );
            recipeService.update(recipe, part, componentSubset);
            toReturn.add(part);
        }
        return toReturn;
    }


    private <T> List<T> getRandomSubsetOf(List<T> list, int subsetSize)
    {
        int[] indexToSample = new int[subsetSize];
        for(int i = 0; i < indexToSample.length; i++){
            indexToSample[i] = (int) Math.floor(Math.random() * list.size());
        }
        List<T> toReturn = new ArrayList<>();
        for(int i : indexToSample){
            toReturn.add(list.get(i));
        }
        return toReturn;
    }


    private static final Component[] componentPool = new Component[]{
            new Component("Motor"),
            new Component("ESC"),
            new Component("Propeller"),
            new Component("Battery"),
            new Component("Flight Controller"),
            new Component("GPS Module"),
            new Component("Receiver"),
            new Component("Transmitter"),
            new Component("Camera"),
            new Component("Gimbal"),
            new Component("Power Distribution Board"),
            new Component("Voltage Regulator"),
            new Component("Accelerometer"),
            new Component("Gyroscope"),
            new Component("Barometer"),
            new Component("Compass"),
            new Component("Ultrasonic Sensor"),
            new Component("Infrared Sensor"),
            new Component("Lidar Sensor"),
            new Component("LED Lights")
    };

}
