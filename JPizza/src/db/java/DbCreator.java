package db.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.Ingredient;

public class DbCreator {
	
	 public static Connection conn;
	 public static Statement statmt;
	 public static ResultSet resSet;
	 
	 public DbCreator() throws ClassNotFoundException, SQLException{
		 Conn();
		 CreateDB();
		// WriteDB(null);
		// ReadDB();
		// CloseDB();
	 }
	
		//----------Обновление базы ингредиентов---------
	 	public static void UpdateIngredients(String ingname, int newcount) throws SQLException{
	 		conn.setAutoCommit(false);
	 		try {
	 			statmt.executeUpdate("UPDATE ingredients SET count = "+ newcount + " WHERE name = '"+ingname+"'"); 			
	 		
	 			conn.commit(); // фиксируем транзакцию
	 		} catch (SQLException e) {
	 		// Если что-то пошло не так, откатываем всю транзакцию
	 			conn.rollback();
	 		}
	 	}
		 // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
	    public static void Conn() throws ClassNotFoundException, SQLException 
	       {
	           conn = null;
	           Class.forName("org.sqlite.JDBC");
	           conn = DriverManager.getConnection("jdbc:sqlite:ingredients.s3db");
	           
	           System.out.println("База Подключена!");
	       }
	    
	    // --------Создание таблицы--------
	    public static void CreateDB() throws ClassNotFoundException, SQLException
	       {
	        statmt = conn.createStatement();
	        statmt.execute("CREATE TABLE if not exists 'ingredients' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'count' INT, 'name' text);");
	        
	        System.out.println("Таблица создана или уже существует.");
	       }
	 // --------Заполнение таблицы--------
	    public static void WriteDB(ArrayList<Ingredient> ingredients) throws SQLException
	    {	    	    	
	    	
	           statmt.execute("INSERT INTO 'ingredients' ('count', 'name') VALUES (5, 'Огурец'); ");
	           statmt.execute("INSERT INTO 'ingredients' ('count', 'name') VALUES (10, 'Помидор');");
	           statmt.execute("INSERT INTO 'ingredients' ('count', 'name') VALUES (15,'Перец' );");
	           
	           
	          
	           System.out.println("Таблица заполнена");
	    }


	    // -------- Вывод таблицы--------
	    public static void ReadDBToList(List<Ingredient> ingredients) throws ClassNotFoundException, SQLException
	       {
	        resSet = statmt.executeQuery("SELECT * FROM ingredients");
	        
	        while(resSet.next())
	        {
	           // int id = resSet.getInt("id");
	        	Ingredient ingredient = new Ingredient(resSet.getString("name"), resSet.getInt("count"));
	        	ingredients.add(ingredient);
	        	
	        	/*    int  count = resSet.getInt("count");
	            String  name = resSet.getString("name");
	           System.out.println( "ID = " + id );
	             System.out.println( "count = " + count );
	             System.out.println( "name = " + name );
	             System.out.println();*/
	            
	        }	
	        
	        System.out.println("Таблица выведена");
	        }
	    
	        // --------Закрытие--------
	        public static void CloseDB() throws ClassNotFoundException, SQLException
	           {
	            conn.close();
	            statmt.close();
	            resSet.close();
	            
	            System.out.println("Соединения закрыты");
	           }
	

}
