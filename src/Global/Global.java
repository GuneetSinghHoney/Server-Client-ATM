package Global;

import java.net.SocketImpl;
import java.util.ArrayList;

import atm.Account;

public class Global
{
	public static int ServerTransectionPort = 8088;
	public static int ServerPort = 8081;
	public static ArrayList<Integer> authenticatedAccounts = new ArrayList<>(); 
	public static String ServerIP = "127.0.0.1";
	
	public static int ClientPort = 8081;
	public static Account[] accounts;
	
}
