package BankingSystem;
public class CheckingsAccount extends Account{

	private double dailyWithdrawalLimit;
	private int currMonth = 0;
	private int currDay = 0;
	
	public CheckingsAccount(double startingDeposit) { 
		super(accountType.CHECKINGS, startingDeposit);
		dailyWithdrawalLimit = 1000;
	}
	
	@Override
	public boolean withdrawal(double amount) {
		if (amount > 0 && balance >= amount && getIsFrozen() == false && amount <= dailyWithdrawalLimit) {
			balance = balance - amount;
			return true;
		}
		return false;
	}
	@Override
	public boolean deposit(double amount) {
		if (amount > 0 && getIsFrozen() == false) {
			balance = amount + balance;
			return true;
		}
		return false;
	}
	
	public double getDailyWithdrawalLimit(){
		return dailyWithdrawalLimit;
	}
}
