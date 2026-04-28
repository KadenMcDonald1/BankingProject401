package BankingSystem;

abstract class Account {
	private int accountID;
	protected double balance;
	private boolean isFrozen;
	private static int accountIDCounter = 0;
	protected accountType accType;

	public Account(accountType aType, double startingDeposit){
		balance = startingDeposit;

		if(balance < 0) {
			System.out.println("Starting deposit cannot be negative");
			balance = 0;
		}
		
		isFrozen = false;
		accountID = accountIDCounter++;
		accType = aType;
	}
	
	public int getAccountID() {
		return accountID;
	}
	public void freeze() {
		isFrozen = true;
	}
	public void thaw() {
		isFrozen = false;
	}

	public double getBalance(){
		return balance;
	}
	public accountType getAccType() {
		return accType;
	}
	public boolean getIsFrozen() {
		return isFrozen;
	}
	// deposit and withdrawal functions may be a little different for credit vs savings/checkings...
	public abstract boolean withdrawal(double amount);
	public abstract boolean deposit(double amount);
}
