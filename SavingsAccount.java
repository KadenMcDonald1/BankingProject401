
public class SavingsAccount extends Account{
	
	private double dailyWithdrawalLimit;
	
	public SavingsAccount(double startingAmount) {
		super(accountType.SAVINGS, startingAmount);
		dailyWithdrawalLimit = 500;
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
