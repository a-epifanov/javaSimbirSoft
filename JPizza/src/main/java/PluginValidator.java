package main.java;
import java.util.ArrayList;
import java.util.List;

public class PluginValidator {
	public void validate(Class<?> pluginClass,List<Ingredient> availableIngredients) {

        if (IngredientMaker.class.isAssignableFrom(pluginClass)) {
            try {
                System.out.println("Нашелся плагин");
                
                IngredientMaker plugin = (IngredientMaker) pluginClass.newInstance();
        		//List<Ingredient> availableIngredients = new ArrayList<Ingredient>();
            	availableIngredients.add(new Ingredient("Морковь",1));
        		availableIngredients.add(new Ingredient("Чеснок",4));
        		availableIngredients.add(new Ingredient("Колбаски",8));
        		availableIngredients.add(new Ingredient("Сосиски",8));
        		plugin.init();
                plugin.initIngredients(availableIngredients);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

    }

}
