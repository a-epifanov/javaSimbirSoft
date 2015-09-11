package main.java;
import java.util.List;

public interface IngredientMaker {

	void init();
	   
	void initIngredients(List<Ingredient> availableIngredients);

}
