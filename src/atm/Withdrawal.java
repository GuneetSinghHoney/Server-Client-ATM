package atm;
// Withdrawal.java
// Represents a withdrawal ATM transaction

public class Withdrawal
{

	public boolean debit(int userAccountNumber, BankDatabase atmBankDatabase, int amount)
   {

        // Get available balance of account involved:
        double availableBalance = atmBankDatabase.getTotalBalance(userAccountNumber);
        if (amount <= availableBalance)
        {  
        	//Done
        	 atmBankDatabase.debit(userAccountNumber, amount);
        	 return true; 
        }
        else
        {
        	//Insufficient Funds:
        	return false;
        }		
   }

}