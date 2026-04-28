package BankingSystem;

public class Employee extends User {
	private Customer curCust;// may be stored in the main client class
	
	public Employee(String pin) {//for new Employees.
		super(pin);
	}
	public Employee(int ID, String pin) {//for existing Employees.
		super(ID, pin);
	}
	
	public Customer getCurCust() {
		return curCust;
	}
	
	public void setCurCust(Customer newCust) {
		curCust = newCust;
	}
	public void clearCurCust() {
		curCust = null;
	}
	public boolean hasCurCust() {
		if (curCust != null) {
			return true;
		}
		return false;
	}
	
	public void openAccountForCurCust(accountType type, double startingDeposit) {
		curCust.addAccount(type, startingDeposit);
	}
	
	public void removeAccountForCurCust(int accountID) {
		curCust.removeAccount(accountID);
	}
	
	public void freezeCurCustAcc(int index) {
		curCust.freezeAccount(index);
		
	}
	public void thawCurCustAcc(int index) {
		curCust.thawAccount(index);
	}
	
	public boolean transferBetweenCurCustAccounts(int sendingAccount, int recievingAccount, double amount) {
		return curCust.transferBetweenOwnedAccounts(sendingAccount, recievingAccount, amount);
	}
	
	public boolean transferToOutsideCust(Customer recievingCustomer, int sendingAccountID, int recievingAccountID, double amount) {
		return curCust.transferToOusideAccounts(recievingCustomer, sendingAccountID, recievingAccountID, amount);
	}
}
