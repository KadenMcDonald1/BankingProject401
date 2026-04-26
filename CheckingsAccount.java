
public class CheckingsAccount extends Account{

	private double dailyWithdrawalLimit;
	
	public CheckingsAccount(double startingDeposit) { 
		super(accountType.CHECKINGS, startingDeposit);
		dailyWithdrawalLimit = 1000;
	}
	
	@Override
	public boolean withdrawal(double amount) {
		if (amount > 0 && balance >= amount) {
			balance = balance - amount;
			return true;
		}
		return false;
	}
	@Override
	public boolean deposit(double amount) {
		if (amount > 0) {
			balance = amount + balance;
			return true;
		}
		return false;
	}
}
