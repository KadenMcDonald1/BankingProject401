
public class Customer extends User{
	private Account[] accounts;
	private String securityQ;
	private String securityA;
	private String customerName;
	private int numAccounts;
	
	public Customer(String pin,  String secQ, String secA,String name) {
		super(pin);
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
	
	public void addAccount(accountType type) { 
		if (numAccounts == accounts.length){ // if array has no spots open
			Account[] temp = accounts;
			accounts = new Account[(temp.length)*2];
			for (int j = 0; j < temp.length; j++) {
				accounts[j] = temp[j];
			}
		}
		if (type == accountType.CHECKINGS) {// do we need both a savings and a checkings?...
			accounts[numAccounts] = new CheckingsAccount();
		}
		else if (type == accountType.SAVINGS) {
			accounts[numAccounts] = new SavingsAccount();
		}
		else if (type == accountType.CREDIT) {
			accounts[numAccounts] = new CreditAccount();
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
	
}
