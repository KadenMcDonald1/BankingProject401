package BankingSystem;
public class SavingsAccount extends Account{
	
	private double dailyWithdrawalLimit;
	
	public SavingsAccount(double startingAmount) {
		super(accountType.SAVINGS, startingAmount);
		dailyWithdrawalLimit = 500;
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
	
	public double getDailyWithdrawalLimit() {
		return dailyWithdrawalLimit;
	}
}
