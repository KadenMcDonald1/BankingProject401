package BankingSystem;
public class SavingsAccount extends Account{
	
	private double dailyWithdrawalLimit;
	private String prevWithdrawalDate;
	private double amountWSF; // amountWSF = amount withdrawaled so far.
	
	public SavingsAccount(double startingAmount, int currAccID) {
		super(accountType.SAVINGS, startingAmount, currAccID);
		dailyWithdrawalLimit = 500;
		prevWithdrawalDate = getFormatedDate();
		amountWSF = 0;
	}
	
	@Override
	public boolean withdrawal(double amount) {
		String currDate = getFormatedDate();
		if (amount > 0 && balance >= amount && getIsFrozen() == false && amount <= dailyWithdrawalLimit) {
			if (!currDate.equals(prevWithdrawalDate)) {
				prevWithdrawalDate = getFormatedDate();
				amountWSF = 0;
			}
			if (amount+amountWSF > dailyWithdrawalLimit) {
				return false;
			}
			balance = balance - amount;
			amountWSF += amount;
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
