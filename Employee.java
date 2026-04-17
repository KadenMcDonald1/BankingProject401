
public class Employee extends User {
	private Customer curCust;// may be stored in the main client class
	
	public Employee(String pin) {
		super(pin);
	}
	
	public void setCurCust(Customer newCust) {
		curCust = newCust;
	}
	public void clearCurCust() {
		curCust = null;
	}
	// maybe add a curr account argument as well?
}
