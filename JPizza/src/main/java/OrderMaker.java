package main.java;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class OrderMaker  implements Runnable{

	private Pizza pizza;
	private ConcurrentLinkedDeque<Pizza> cookDeque ;
	private ConcurrentLinkedDeque<Pizza> deliveryDeque ;
	
	public OrderMaker(Pizza pizza,ConcurrentLinkedDeque<Pizza> cookDeque, ConcurrentLinkedDeque<Pizza> deliveryDeque){
		this.pizza = pizza;
		this.cookDeque = cookDeque;
		this.deliveryDeque = deliveryDeque;
	}
	
	@Override
	public void run() {

		cookDeque.addFirst(pizza);
		System.out.println("Пицца передана в очередь, ждите 10 сек. Ваш номер"+ cookDeque.size());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		deliveryDeque.addFirst(cookDeque.removeLast());
		System.out.println("Пицца готова, отправлена на доставку, ее номер"+deliveryDeque.size() );
		deliveryDeque.removeLast();
		System.out.println("Пицца доставлена, ее номер"+deliveryDeque.size() );

	}

}
