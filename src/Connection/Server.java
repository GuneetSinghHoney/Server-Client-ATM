package Connection;

import java.io.*;
import java.net.*;

import Global.Global;
import atm.*;

public class Server {
	private Socket mySocket = null;
	private static Withdrawal debit = null;
	private static BankDatabase mydb = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private boolean flag = false;
    private ServerSocket serverTrac;
	public Server() {
		startServer();

	}

	public void startServer() {

		try {
			ServerSocket server = new ServerSocket(Global.ServerPort);
			serverTrac = new ServerSocket(Global.ServerTransectionPort);
			System.out.println("Server Started on Port Number:" + Global.ServerPort);
			// -- Server started

			mydb = new BankDatabase();
			debit = new Withdrawal();
			while (true) {
				mySocket = server.accept();
				
				
			
				new Thread(new Runnable() {
					public void run() {
						try {
							System.out.println("Connection accepted: " + mySocket.getInetAddress().getHostName());

							// Joining Input Stream
							in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
							// Output is automatically flushed
							// by PrintWriter:
							out = new PrintWriter(
									new BufferedWriter(new OutputStreamWriter(mySocket.getOutputStream())), true);

							// Write code for connection
							while (true) {
								int accountNumber = Integer.parseInt(in.readLine().trim());
								int pin = Integer.parseInt(in.readLine().trim());

								// If user and password is empty.
								//
								//

								if (mydb.authenticateUser(accountNumber, pin)) {
									// user is authentic
									out.println("true");
									System.out.println("Transaction Server Started on Port Number:"
											+ Global.ServerTransectionPort);
									Socket mySocketTrac = serverTrac.accept();
									System.out.println("TRAN connection accepted");
									 
										new Thread(new Runnable() {

											@Override
											public void run() {
											
												try {

													flag = true;
													
													BufferedReader inTrac = new BufferedReader(
															new InputStreamReader(mySocketTrac.getInputStream()));
													// Output is automatically
													// flushed
													// by PrintWriter:

													PrintWriter outTrac = new PrintWriter(new BufferedWriter(
															new OutputStreamWriter(mySocketTrac.getOutputStream())),
															true);

													// -- Server started
													int accountTracNumber = Integer.parseInt(inTrac.readLine().trim());
													System.out.println(accountTracNumber);
													outTrac.println(mydb.getTotalBalance(accountTracNumber));

													// user commands Action !
													do {
														String action = inTrac.readLine().trim();
														System.out.println(action);
														if (action.equals("debit")) {
															System.out.println("debit");
															// Debit transaction
															int amount = Integer.parseInt(inTrac.readLine().trim());
															System.out.println(amount);
															if (amount < 0 || amount == 0) {
																outTrac.println(
																		"The Amount can not be Negative or Zero");
															} else {
																boolean check = debit.debit(accountTracNumber, mydb,
																		amount);
																System.out.println(check);
																if (check) {
																	outTrac.println("done");
																	outTrac.println(
																			mydb.getTotalBalance(accountTracNumber));
																	System.out.println(
																			mydb.getTotalBalance(accountTracNumber));
																} else {
																	outTrac.println("Insufficient Balance");
																}
															}
														} else if (action.equals("credit")) {
															// Add funds
															int amount = Integer.parseInt(inTrac.readLine().trim());
															if (amount < 0 || amount == 0) {
																outTrac.println(
																		"The Amount can not be Negative or Zero");
															} else {
																mydb.credit(accountTracNumber, amount);
																outTrac.println("done");
																outTrac.println(
																		mydb.getTotalBalance(accountTracNumber));
															}

														} else if (action.equals("exit")) {
															System.out.println("EXIT from server");

															// break;
														} else {
															// break;
														}

													} while (true);

												} catch (Exception e) {

													try {
														in.close();
														out.close();
														 
														flag = false;
														e.printStackTrace();
													} catch (IOException e1) {
														// TODO Auto-generated
														// catch block
														e1.printStackTrace();
													}

												}
											}
										}).start();

									

									break; 
								} else {
									// User is fake
									out.println("fake");
									continue;
								}

							}
						} catch (Exception e) {

						}
					}
				}).start();

			}

		} catch (Exception e) {

		}

	}

}
