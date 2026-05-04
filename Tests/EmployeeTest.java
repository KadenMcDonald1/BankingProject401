package Tests;

import org.junit.Assert;

import org.junit.Test;

import BankingSystem.*;

public class EmployeeTest {
	
	@Test
	public void EmployeeConstructorTest() {
		Employee emp0 = new Employee(100, "password");
		Assert.assertNotNull(emp0);
	}
	@Test 
	public void setAndGetCurrCustTest() {
		Employee emp1 = new Employee(101, "password");
		Customer cust1 = new Customer(201, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		emp1.setCurCust(cust1);
		Assert.assertNotNull(emp1.getCurCust());
	}
	@Test
	public void clearCurrCustTest() {
		Employee emp2 = new Employee(102, "password");
		Customer cust2 = new Customer(202, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		emp2.setCurCust(cust2);
		emp2.clearCurCust();
		Assert.assertNull(emp2.getCurCust());
	}
	@Test
	public void hasCurCust() {
		Employee emp3 = new Employee(103, "password");
		Customer cust3 = new Customer(203, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		emp3.setCurCust(cust3);
		Assert.assertTrue(emp3.hasCurCust());
	}
	@Test
	public void freezeCurCustAccTest() {
		Employee emp4 = new Employee(104, "password");
		Customer cust4 = new Customer(204, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		cust4.addAccount(accountType.CHECKINGS, 3000,0);
		emp4.setCurCust(cust4);
		Account[] accounts = cust4.getAccounts(); 
		emp4.freezeCurCustAcc(0); //may need to double check this logic later...
		Assert.assertTrue(accounts[0].getIsFrozen());
	}
	@Test
	public void thawCurCustAccTest() {
		Employee emp5 = new Employee(105, "password");
		Customer cust5 = new Customer(205, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		cust5.addAccount(accountType.CHECKINGS, 3000,0);
		emp5.setCurCust(cust5); 
		Account[] accounts = cust5.getAccounts(); 
		emp5.thawCurCustAcc(0); // May need to double check this logic later... (might require a
								// change to searching for AccID instead of array index)
		Assert.assertFalse(accounts[0].getIsFrozen());
	}
	@Test
	public void removeAccountTest() {
		Employee emp6 = new Employee(106, "password");
		Customer cust6 = new Customer(206, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		cust6.addAccount(accountType.CHECKINGS, 3000,0);
		emp6.setCurCust(cust6);
		Account[] accounts = cust6.getAccounts(); 
		emp6.removeAccountForCurCust(accounts[0].getAccountID());
		Assert.assertNull(cust6.getAccounts()[0]);
	}
	
	@Test
	public void transferBetweenOwnedAccountsTest() {
		Employee emp7 = new Employee(107, "password");
		Customer cust7 = new Customer(107, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		cust7.addAccount(accountType.CHECKINGS, 3000,0);
		cust7.addAccount(accountType.SAVINGS, 4000,0);
		emp7.setCurCust(cust7);
		Account[] accounts = cust7.getAccounts();
		Assert.assertTrue(emp7.transferBetweenCurCustAccounts(accounts[0].getAccountID(),accounts[1].getAccountID(),430.59));
	}
	@Test
	public void transferBetweenOutsideAccountsTest() {
		Employee emp8 = new Employee(107, "password");
		Customer cust8 = new Customer(108, "password", "Favorite Pets Name?", "Iggy", "Joseph Jo");
		Customer cust9 = new Customer(109, "password", "Favorite Pets Name?", "Red", "Muhammad Avdol");
		cust8.addAccount(accountType.CHECKINGS, 3000,0);
		Account[] accounts0 = cust8.getAccounts();
		cust9.addAccount(accountType.SAVINGS, 4000,0);
		Account[] accounts1 = cust9.getAccounts();
		emp8.setCurCust(cust8);
		Assert.assertTrue(emp8.transferToOutsideCust(cust9,accounts0[0].getAccountID(),accounts1[0].getAccountID(),430.59));
	}
}