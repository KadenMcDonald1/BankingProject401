
public class CreditAccount extends Account{
	
	private double creditLimit;
	private double interestRatePerMonth;
	
	public CreditAccount() {
		super(accountType.CREDIT);
		creditLimit = 2000;
		interestRatePerMonth = 0.02;
	}
	
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
	
	@Override
	public boolean deposit(double amount) {//could also let the customer put as much as they want into their credit...
		if (amount > 0 && balance < 100) {
			balance = amount + balance;
			return true;
		}
		return false;
	}
	@Override
	public boolean withdrawal(double amount) {
		if ((balance - amount) >= -2000) {
			balance = balance - amount;
			return true;
		}
		return false;
	}
}
