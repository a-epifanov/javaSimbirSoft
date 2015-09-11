package main.java;
import java.util.List;

public class PizzaPlugin implements IngredientMaker {

	@Override
	public  void initIngredients(List<Ingredient> availableIngredients) {
		availableIngredients.add(new Ingredient("Морковь",1));
		availableIngredients.add(new Ingredient("Чеснок",4));
		availableIngredients.add(new Ingredient("Колбаски",8));
		availableIngredients.add(new Ingredient("Сосиски",8));
		System.out.println("AZAZA");
		
		
	}

	@Override
	public void init() {
		System.out.println("Плагин запустили");
		
	}
	
	public static void main(String[] args){
		System.out.println("Плагин запустили");

	}
	
}
