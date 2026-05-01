package BankingSystem;
public class CreditAccount extends Account{
	
	//Credit accounts store a credit limit and monthly interest rate
	private double creditLimit;
	private double interestRatePerMonth;
	
	public CreditAccount() {
		super(accountType.CREDIT, 0);
		creditLimit = 2000;
		interestRatePerMonth = 0.02;
	}
	
	//Adds monthly interest when the credit account has a negative balance
	public void calculateInterest() {//set up to call at the start of the month or every 30 days...
		if (balance<0) {			 // could maybe add a variable that keeps track of the last time this card
									 // was used and if the month has changed since then, then call the method (this would be done on login)
			balance = balance*(1+interestRatePerMonth);
			balance = Double.parseDouble(String.format("%.2f",balance));
		}
	}
	public double getCreditLimit() {
		return creditLimit;
	}
	
	public double getInterestRatePerMonth() {
		return interestRatePerMonth;
	}
	
	@Override
	public boolean deposit(double amount) {//could also let the customer put as much as they want into their credit...
		if (amount > 0 && balance < 100) {
			balance = amount + balance;
			return true;
		}
		return false;
	}
	
	//Allows withdrawals as long as the credit limit is not exceeded
	@Override
	public boolean withdrawal(double amount) {
		if ((balance - amount) >= -creditLimit) {
			balance = balance - amount;
			return true;
		}
		return false;
	}
}
