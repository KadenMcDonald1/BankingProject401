import java.io.Serializable;

public class Message implements Serializable {
	
	private commandStatus cStatus;  
	private commandType cType;
	private userType uType;
	private userStatus uStatus;
	private Customer currCust;
	private Employee activeEmployee;
	private String userID;
	private String text;
	
	public Message(){
		cStatus = commandStatus.UNDEFINED;
		cType = commandType.UNDEFINED;
		uType = userType.UNDEFINED;
		uStatus = userStatus.UNDEFINED;
		currCust = null;
		activeEmployee = null;
		userID = "Undefined";
		text = "Undefined";
	}
	public Message(userType UType, userStatus UStatus, commandType CType, commandStatus CStatus,
				   Customer CurrCust, Employee ActiveEmployee, String UserID, String Text) {
					
		cStatus = CStatus;
		cType = CType;
		uType = UType;
		uStatus = UStatus;
		userID = UserID;
		text = Text;
		currCust = CurrCust;
		activeEmployee = ActiveEmployee;
	}
	
	public commandStatus getCStatus(){
		return cStatus;
	}
	public commandType getCType(){
		return cType;
	}
	public userType getUType(){
		return uType;
	}
	public userStatus getUStatus(){
		return uStatus;
	}
	public Customer getCurrCust() {
		return currCust;
	}
	public Employee getActEmployee() {
		return activeEmployee;
	}
	public String GetUserID() {
		return userID;
	}
	public String getText(){
		return text;
	}
	
}
