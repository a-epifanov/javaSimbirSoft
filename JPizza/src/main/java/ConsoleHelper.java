package main.java;
import java.util.StringTokenizer;

public class ConsoleHelper {

	
	public static int readIntFromConsole(){
		int iCnt= 0;
		Integer intVal=0;
		StringTokenizer st;
		
		try
		{
			
			  byte bKbd[] = new byte[256];
			  
			  iCnt = System.in.read(bKbd);
			  String szStr = new String(bKbd, 0, iCnt);

			  st = new StringTokenizer(szStr, "\r\n");
			  szStr =  (String)st.nextElement();			  
			  intVal  = Integer.valueOf(szStr);
			  
			  
			}
			catch(Exception ex)
			{
			  System.out.println(ex.toString()); 
			}
		return intVal;
		
	}

}
