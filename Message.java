import java.io.Serializable;

public class Message implements Serializable {
	
	private transactionType tType;
	private commandStatus cStatus; 
	private commandType cType;
	private userType uType;
	private userStatus uStatus;
	private String userID;
	private String text;
	
	public Message(){
		tType = transactionType.UNDEFINED;
		cStatus = commandStatus.UNDEFINED;
		cType = commandType.UNDEFINED;
		uType = userType.UNDEFINED;
		uStatus = userStatus.UNDEFINED;
		userID = "Undefined";
		text = "Undefined";
	}
	public Message(transactionType TType, commandStatus CStatus, commandType CType, userType UType, userStatus UStatus, String UserID, String Text){
		tType = TType;
		cStatus = CStatus;
		cType = CType;
		uType = UType;
		uStatus = UStatus;
		userID = UserID;
		text = Text;
	}
	
	public transactionType getTType(){
		return tType;
	}
	public commandStatus getCStatus(){
		return cStatus;
	}
	public commandType getCType(){
		return cType;
	}
	public userType getUserType(){
		return uType;
	}
	public userStatus getUStatus(){
		return uStatus;
	}
	public String GetUserID() {
		return userID;
	}
	public String getText(){
		return text;
	}
	
}
