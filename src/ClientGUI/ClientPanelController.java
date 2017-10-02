package ClientGUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import Global.Global;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

public class ClientPanelController 
{
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String balanceCurrent = null;
	private int accountnumber = 0;
	public ClientPanelController()
	{
		super();
		setup();
	
	}
	private void setup()
	{
		connection();
		try {
			out.println(Global.authenticatedAccounts.get(Global.authenticatedAccounts.size()-1));
			accountnumber = Global.authenticatedAccounts.get(Global.authenticatedAccounts.size()-1);
			Global.authenticatedAccounts.remove(Global.authenticatedAccounts.indexOf(accountnumber));
			balanceCurrent = in.readLine()+"";
			
			Alert alert = new Alert(AlertType.INFORMATION);
			 alert.setTitle("CURRENT BALANCE - ");
			 alert.setHeaderText(null);
			 alert.setContentText("CURRENT BALANCE: "+balanceCurrent);
			 alert.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	}
	@FXML
	private Label bal;
	@FXML
	private TextField amount;
	@FXML
	private Button deposit;
	@FXML
	private Button withdraw;

	// Event Listener on Button[#deposit].onMouseClicked
	@FXML
	public void depositeMoney(MouseEvent event)
	{
		
		try{
		  out.println("credit");
	       
      	  out.println(amount.getText());
      	  if("done".equals(in.readLine().trim()))
      	  {
      		  bal.setText( in.readLine()  ); 
      		  
      		 Alert alert = new Alert(AlertType.INFORMATION);
			 alert.setTitle("Information Message");
			 alert.setHeaderText(null);
			 alert.setContentText("Mondey Deposited ! ");
			 alert.show();
      		  
      	  }
      	  else
      	  {
      		 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("Error!");
			 alert.setHeaderText(null);
			 alert.setContentText("Value should be Positive");
			 alert.show();
      	  }
		}catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("Error !");
			 alert.setHeaderText(null);
			 alert.setContentText("Some Error Occured!");
			alert.show();
		}
		
	}
	// Event Listener on Button[#withdraw].onMouseClicked
	@FXML
	public void withdrawMoney(MouseEvent event)
	{
		try
		{
		out.println("debit");
       	 
      	  out.println(amount.getText());
      	  String resp = in.readLine().trim();
      	  if("done".equals(resp))
      	  {
      		  bal.setText(in.readLine());
      		 Alert alert = new Alert(AlertType.INFORMATION);
			 alert.setTitle("Information Message");
			 alert.setHeaderText(null);
			 alert.setContentText("Please Take your Money");
			 alert.show();
      		
      		 
      	  }
      	  else
      	  {

       		 Alert alert = new Alert(AlertType.ERROR);
 			 alert.setTitle("Error!");
 			 alert.setHeaderText(null);
 			 alert.setContentText(resp.toString());
 			 alert.show();
      	  }
			
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("Error !");
			 alert.setHeaderText(null);
			 alert.setContentText("Some Error Occured !");
			alert.show();
			
		}
		
	}
	private void connection() {
		try {
			InetAddress SERVERIP = InetAddress.getByName(Global.ServerIP);
			System.out.println("addr = " + SERVERIP);
			Socket socket = new Socket(SERVERIP, Global.ServerTransectionPort);

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Output is automatically flushed
			// by PrintWriter:

			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

		}

		catch (Exception e) {

		}
	}
}
