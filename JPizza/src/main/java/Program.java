package main.java;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

import db.java.DbCreator;

public class Program{
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{

		int threadValue;
		Integer intVal=0;
		String[] pluginsList = new String[]{"pizzaplugin.jar"};
		DbCreator db = new DbCreator();
		DbCreator.UpdateIngredients("Огурец", 5);

		
		Deque<Pizza> cookQueue = new LinkedList<Pizza>();
		ConcurrentLinkedDeque<Pizza> cookDeque = new ConcurrentLinkedDeque<>();
		Deque<Pizza> deliveryQueue = new LinkedList<Pizza>();
		ConcurrentLinkedDeque<Pizza> deliveryDeque = new ConcurrentLinkedDeque<>();
		
		//Заполняем список доступных ингредиентов
		List<Ingredient> availableIngredients = new ArrayList<Ingredient>();
		/*availableIngredients.add(new Ingredient("Моцарелла",1));
		availableIngredients.add(new Ingredient("Томат",4));
		availableIngredients.add(new Ingredient("Ветчина",8));
		availableIngredients.add(new Ingredient("Перец",8));*/
		DbCreator.ReadDBToList(availableIngredients);
		
		
		 PluginLoader pluginLoader = new PluginLoader();
	        PluginValidator pluginValidator = new PluginValidator();
	        for (Class<?> plugin : pluginLoader.load(pluginsList)) {
	           pluginValidator.validate(plugin,availableIngredients);
	        }
	        
		
	

		
		//Выбор размера пиццы
		System.out.println("Выберите размер пиццы, рекомендуем 30 или 40"); 

		intVal = ConsoleHelper.readIntFromConsole();
		System.out.println("Вы выбрали пиццу размером " + intVal); 
		Pizza pizza = new Pizza();
		pizza.size = intVal;
		
		//Выбор ингредиентов для пиццы
		
		int ingNumber =0;
		
		while(!(ingNumber==-1)){
		
			System.out.println("Введите номер желаемого ингредиента из списка, чтобы закончить наполнение, введите -1");
		
			System.out.println("Номер \t Название \t Доступно");
		
			
			for (Ingredient ing : availableIngredients){
			
				if(!(ing.count==0)){
				System.out.println(availableIngredients.indexOf(ing) + ". \t " +  ing.name + ". \t " + ing.count); 
				}
			}
			ingNumber = ConsoleHelper.readIntFromConsole();
			
			if (ingNumber!=-1){
				Ingredient currentIngredient = availableIngredients.get(ingNumber);
				
				//выбор размера порции ингредиента

				System.out.println("Введите размер ингредиента от 1 до " + currentIngredient.count);
				int ingCount = ConsoleHelper.readIntFromConsole();
				if((ingCount <= currentIngredient.count)&&(ingCount>0) ){
					pizza.ingredients.add(new Ingredient(currentIngredient.name,ingCount));
					System.out.println("ДОБАВЛЕНО");
					currentIngredient.count = currentIngredient.count- ingCount;
					
				}else {
					
					System.out.println("Вы слишком много хотите, либо ингредиента уже не осталось");
				}
			}
			System.out.println("Вы заказали пиццу из следующих ингредиентов");
			for (Ingredient ing : pizza.ingredients){
				
				if(!(ing.count==0)){
				System.out.println(pizza.ingredients.indexOf(ing) + ". \t " +  ing.name + ". \t " + ing.count); 
				}
			}
			
			
		}
		
		//ПОТОКИ
		
		//отправка заказа в очередь приготовления (приготовить пиццу за 10 секунд, отправить пиццу в очередь выдачи)
		threadValue = 100;
		
		while(threadValue>0){
		
		Thread consumerThread = new Thread(new OrderMaker(pizza,cookDeque,deliveryDeque));
		consumerThread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/*cookQueue.addFirst(pizza);
		System.out.println("Пицца передана в очередь, ждите 10 сек");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Пицца готова, отправлена на доставку");
		
		deliveryQueue.addFirst(cookQueue.removeLast());
		deliveryQueue.removeLast();*/
		threadValue--;
		}

		
		
	}
}