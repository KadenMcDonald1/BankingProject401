package BankingSystem;

import java.io.Serializable;

abstract class User implements Serializable{	
	protected int userID; //this being protected may cause issues...
	private String pin;
	private boolean isLoggedIn;
	private static int userIDCounter = 0;
	//will need a method to set this counter to the current highest ID number
	
	// new user
	public User(String pin) {
		userID = userIDCounter++;
		isLoggedIn = false;
		this.pin = pin;
	}
	
	//existing user
	public User(int userID, String pin) {
		this.userID = userID;
		isLoggedIn = false;
		this.pin = pin;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}
	
	public String getPin() {
		return pin;
	}
	
	public int getUserIDCounter() {
		return userIDCounter;
	}
	
	public boolean checkPin(String Pin) {// May have this function built into the server later 
		if (pin.equals(Pin)) {			 // (meaning: may not need it here)
			return true;
		}
		else {
			return false;
		}
	}
	public void setIsLoggedIn(boolean bool) {
		isLoggedIn = bool;
	}
	public void setPin(String newPin) {
		pin = newPin;
	}

	public void setIDCounter(int i) {
		userIDCounter = i;
	}
	
}
