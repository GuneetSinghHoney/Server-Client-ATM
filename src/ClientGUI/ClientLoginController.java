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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;

import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClientLoginController {
	private PrintWriter out = null;
	private BufferedReader in = null;

	public ClientLoginController() {
		super();
		connection();
	}

	@FXML
	private TextArea account;
	@FXML
	private PasswordField pass;
	@FXML
	private Button LOGIN;

	// Event Listener on Button[#LOGIN].onMouseClicked
	@FXML
	public void loginCheck(MouseEvent event) {
		// If user and pass is empty
		
		if (account.getText().equals("") || pass.getText().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Message");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter Username and Password");
			alert.show();
		} else {
			out.println(account.getText());
			out.println(pass.getText());

			// Connect and verify
			try {

				if ("true".equals(in.readLine().trim())) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Message");
					alert.setHeaderText(null);
					alert.setContentText("Login Successful !");
					alert.show();
					
					Global.authenticatedAccounts.add(Integer.parseInt(account.getText()));
					System.out.println(Integer.parseInt(account.getText()));
					//String balance = in.readLine().trim();
									
					
					Stage stage = (Stage) LOGIN.getScene().getWindow();
					
					Parent root = FXMLLoader.load(getClass().getResource("ClientPanel.fxml"));

					//System.out.println(balance);

					Scene scene = new Scene(root);

					stage.setScene(scene);

					stage.show();

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error !");
					alert.setHeaderText(null);
					alert.setContentText("Wrong Credentials !");
					alert.show();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void connection() {
		try {
			InetAddress SERVERIP = InetAddress.getByName(Global.ServerIP);
			System.out.println("addr = " + SERVERIP+Global.ClientPort);
			Socket socket = new Socket(SERVERIP, Global.ClientPort);

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Output is automatically flushed
			// by PrintWriter:

			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

		}

		catch (Exception e) {

		}
	}
}
