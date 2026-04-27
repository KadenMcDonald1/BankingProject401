
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
		super(pin);
		securityQ = secQ;
		securityA = secA;
		customerName = name;
		numAccounts = 0;
		accounts = new Account[5];
		userID = ID;
		spinBackIDCounter();
	}
	
	public Account[] getAccounts() {
		return accounts;
	}
	public Account getAccountByID(int id) {
		int i;
		for (i = 0; i < numAccounts; i++) {
			if (id == accounts[i].getAccountID()) {
				break;
			}
		}
		return accounts[i];
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
	public void seSecurityA(String secA) {
		securityA = secA;
	}
	public int getNumAccounts() {
		return numAccounts;
	}
	public String getCustName() {
		return customerName;
	}
	
	public void addAccount(accountType type, double startingDeposit) { 
		if (numAccounts == accounts.length){ // if array has no spots open
			Account[] temp = accounts;
			accounts = new Account[(temp.length)*2];
			for (int j = 0; j < temp.length; j++) {
				accounts[j] = temp[j];
			}
		}
		if (type == accountType.CHECKINGS) {// do we need both a savings and a checkings?...
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
	public boolean transferBetweenOwnedAccounts(int sendingAccountID, int recievingAccountID, double amount) {
		Account account1 = getAccountByID(sendingAccountID);
		Account account2 = getAccountByID(recievingAccountID);
		boolean succsess = true;
		succsess = account1.withdrawal(amount);
		if (succsess == false) {
			return succsess;
		}
		succsess = account2.deposit(amount);
		if (succsess == false) {
			succsess = account1.deposit(amount);//undo the effect of the line: succsess=account1.withdrawal(amount);
			return succsess;
		}
		return succsess;
	}
	
	public boolean transferToOusideAccounts(int recievingCustomer, int sendingAccountID, int recievingAccountID, double amount) {
		
		return false; // need to implement still...
	}
	
}
