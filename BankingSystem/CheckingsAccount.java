package BankingSystem;

import java.util.Date;
import java.text.SimpleDateFormat;

public class CheckingsAccount extends Account{

	private double dailyWithdrawalLimit;
	private String prevWithdrawalDate;
	private double amountWSF; // amountWSF = amount withdrawaled so far.
	
	public CheckingsAccount(double startingDeposit, int currAccID) { 
		super(accountType.CHECKINGS, startingDeposit, currAccID);
		dailyWithdrawalLimit = 1000;
		prevWithdrawalDate = getFormatedDate();
		amountWSF = 0;
	}
	
	//Withdraws money if the amount is valid, the account is not frozen, and the daily limit is not exceeded
	@Override
	public boolean withdrawal(double amount) {
		String currDate = getFormatedDate();
		if (amount > 0 && balance >= amount && getIsFrozen() == false && amount <= dailyWithdrawalLimit) {
			if (!currDate.equals(prevWithdrawalDate)) {
				prevWithdrawalDate = getFormatedDate();
				amountWSF = 0;
			}
			if (amount+amountWSF > dailyWithdrawalLimit) {
				return false;
			}
			balance = balance - amount;
			amountWSF += amount;
			return true;
		}
		return false;
	}
	
	//Deposits money if the amount is valid and the account is not frozen
	@Override
	public boolean deposit(double amount) {
		if (amount > 0 && getIsFrozen() == false) {
			balance = amount + balance;
			return true;
		}
		return false;
	}
	
	//Checkings accounts have a daily withdrawal limit
	public double getDailyWithdrawalLimit(){
		return dailyWithdrawalLimit;
	}
}
