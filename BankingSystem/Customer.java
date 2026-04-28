package BankingSystem;

public class Customer extends User{
	private Account[] accounts;
	private String securityQ;
	private String securityA;
	private String customerName;
	private int numAccounts;
	
	public Customer(String pin, String secQ, String secA, String name) {//for new customers.
		super(pin);
		securityQ = secQ;
		securityA = secA;
		customerName = name;
		numAccounts = 0;
		accounts = new Account[5];
	}
	public Customer(int ID, String pin, String secQ, String secA, String name) {//for customers that exist in the files.
		super(ID, pin);
		securityQ = secQ;
		securityA = secA;
		customerName = name;
		numAccounts = 0;
		accounts = new Account[5];
	}
	
	public Account[] getAccounts() {
		return accounts;
	}
	
	public Account getAccountByID(int id) {
		int i;
		for (i = 0; i < numAccounts; i++) {
			if (id == accounts[i].getAccountID()) {
				return accounts[i];
			}
		}
		return null;
	}
	public void freezeAccount(int i){
		accounts[i].freeze();
	}
	public void thawAccount(int i){
		accounts[i].thaw();
	}
	public String getSecurityQ() {
		return securityQ;
	}
	public String getSecurityA() {
		return securityA;
	}
	public void setSecurityQ(String secQ) {
		securityQ = secQ;
	}
	public void setSecurityA(String secA) {
		securityA = secA;
	}
	public int getNumAccounts() {
		return numAccounts;
	}
	public String getCustName() {
		return customerName;
	}
	
	public void addAccount(accountType type, double startingDeposit) { 
		if (numAccounts == accounts.length){ // if array has no spots open, the array size will be doubled
			Account[] temp = accounts;
			accounts = new Account[(temp.length)*2];
			for (int j = 0; j < temp.length; j++) {
				accounts[j] = temp[j];
			}
		}
		if (type == accountType.CHECKINGS) {
			accounts[numAccounts] = new CheckingsAccount(startingDeposit);
		}
		else if (type == accountType.SAVINGS) {
			accounts[numAccounts] = new SavingsAccount(startingDeposit);
		}
		else if (type == accountType.CREDIT) {
			accounts[numAccounts] = new CreditAccount(startingDeposit);
		}
		else {
			return;
		}
		numAccounts++;
	}
	
	public void removeAccount(int accountID) {
		int removeIndex = 0;
		boolean found = false;
		
		//find the index of the account to remove 
		for (int i = 0; i < numAccounts; i++) {
			if(accounts[i].getAccountID() == accountID) {
				removeIndex = i;
				found = true;
				break;
			}
		}
		//if account not found return
		if (found == false) {
			return;
		}
		
		//shift all accounts to the left
		for (int i = removeIndex; i < numAccounts-1; i++) {
			accounts[i] = accounts[i + 1];
		}
		
		accounts[numAccounts - 1] = null;
		--numAccounts;
	}
	
	
	
	public boolean transferBetweenOwnedAccounts(int sendingAccountID, int recievingAccountID, double amount) {
		Account account1 = getAccountByID(sendingAccountID);
		Account account2 = getAccountByID(recievingAccountID);
		
		boolean success = true;
		success = account1.withdrawal(amount);
		if (success == false) {
			return false;
		}
		success = account2.deposit(amount);
		if (success == false) {
			success = account1.deposit(amount);//if the account 2 can't get the money (frozen account) then deposit the money back to the sender
			return false;
		}
		return success;
		
	}
	
	public boolean transferToOusideAccounts(Customer recievingCustomer, int sendingAccountID, int recievingAccountID, double amount) {
		Account account1 = getAccountByID(sendingAccountID);
		Account account2 = recievingCustomer.getAccountByID(recievingAccountID);
		
		boolean success = true;
		success = account1.withdrawal(amount);
		if (success == false) {
			return false;
		}
		
		success = account2.deposit(amount);
		if (success == false) {
			success = account1.deposit(amount);//if the account 2 can't get the money (frozen account) then deposit the money back to the sender
			return false;
		}
		
		return success;
	}
	
	public boolean checkSecurityA(String answer) {
		if (securityA.equals(answer)) {
			return true;
		}
		return false;
	}
}
