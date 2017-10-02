package Connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import Global.Global;
 
public class Client
{
	public Client()
	{
		try
		{
		InetAddress SERVERIP = InetAddress.getByName(Global.ServerIP);
		System.out.println("addr = " + SERVERIP);
	    Socket socket = new Socket(SERVERIP,Global.ClientPort);
	    
	    BufferedReader in =
	            new BufferedReader(
	              new InputStreamReader(
	                socket.getInputStream()));

	          // Output is automatically flushed
	          // by PrintWriter:

	          PrintWriter out =
	            new PrintWriter(
	              new BufferedWriter(
	                new OutputStreamWriter(
	                  socket.getOutputStream())),true);
	          
	          out.println("12345");
	          out.println("54321");
	          
	          if("true".equals(in.readLine().trim()))
	          {
	        	  System.out.println("TRUE USER");
	        	  System.out.println("BALANCE "+in.readLine().trim());
	        	  /*
	        //	 debit
	        	  out.println("debit");
	        	 
	        	  out.println("100");
	        	  if("done".equals(in.readLine().trim()))
	        	  {
	        		  System.out.println(in.readLine());
	        		
	        		 
	        	  }
	        	  else
	        	  {
	        		  System.out.println("fail");
	        	  }
	        	 
	        	
	        	  
	        	  //credit
	        	  
	        	  Scanner scan = new Scanner(System.in);
	        	  
	        	  out.println("credit");
		        System.out.println("Enter amount");
	        	  out.println(scan.next());
	        	  if("done".equals(in.readLine().trim()))
	        	  {
	        		  System.out.println(in.readLine());       		 
	        	  }
	        	  else
	        	  {
	        		  System.out.println("fail");
	        	  }
	        	  	
	        	  */
	        	  
	          }
	          else
	          {
	        	  System.out.println("FAKE USER");
	          }
	    
		}catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	
	}
}
