package Tests;

import org.junit.Assert;

import org.junit.Test;

import BankingSystem.*;

public class CustomerTest {
	
	@Test
	public void CustomerConstructorTest() {
		Customer cust0 = new Customer(100, "password", "How old are you?", "I'm 4 years old", "Youngest Person Ever");
		Assert.assertNotNull(cust0);
	}
	@Test
	public void freezeTest() {
		Customer cust1 = new Customer(101, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		cust1.addAccount(accountType.CHECKINGS, 3000,0);
		cust1.freezeAccount(0);
		Account[] accounts = cust1.getAccounts(); 
		Assert.assertTrue(accounts[0].getIsFrozen());
	}
	@Test
	public void thawTest() {
		Customer cust2 = new Customer(102, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		cust2.addAccount(accountType.CHECKINGS, 3000,0);
		cust2.freezeAccount(0);
		cust2.thawAccount(0);
		Account[] accounts = cust2.getAccounts(); 
		Assert.assertFalse(accounts[0].getIsFrozen());
	}
	@Test
	public void getNumAccountsTest() {
		Customer cust3 = new Customer(103, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		cust3.addAccount(accountType.CHECKINGS, 3000,0);
		Assert.assertEquals(cust3.getNumAccounts(),1);
	}
	@Test 
	public void getAccountByIDTest() {
		Customer cust4 = new Customer(104, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		cust4.addAccount(accountType.CHECKINGS, 3000,0);
		Account[] accounts = cust4.getAccounts(); 
		int id = accounts[0].getAccountID();
		Assert.assertEquals(cust4.getAccountByID(id),accounts[0]);
	}
	@Test
	public void getSecurityQTest() {
		Customer cust5 = new Customer(105, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		Assert.assertEquals(cust5.getSecurityQ(),"Favorite Pets Name?");
	}
	@Test
	public void getSecurityATest() {
		Customer cust6 = new Customer(106, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		Assert.assertEquals(cust6.getSecurityA(),"Iggy");
	}
	@Test
	public void getCustNameTest() {
		Customer cust7 = new Customer(107, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		Assert.assertEquals(cust7.getCustName(),"Jose Jo");
	}
	@Test
	public void removeAccountTest() {
		Customer cust8 = new Customer(108, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		cust8.addAccount(accountType.CHECKINGS, 3000,0);
		cust8.removeAccount(cust8.getAccounts()[0].getAccountID());
		Assert.assertNull(cust8.getAccounts()[0]);
	}
	@Test
	public void transferBetweenOwnedAccountsTest() {
		Customer cust9 = new Customer(109, "password", "Favorite Pets Name?", "Iggy", "Jose Jo");
		cust9.addAccount(accountType.CHECKINGS, 3000,0);
		cust9.addAccount(accountType.SAVINGS, 4000,0);
		Account[] accounts = cust9.getAccounts();
		Assert.assertTrue(cust9.transferBetweenOwnedAccounts(accounts[0].getAccountID(),accounts[1].getAccountID(),430.59));
	}
	@Test
	public void transferBetweenOutsideAccountsTest() {
		Customer cust10 = new Customer(110, "password", "Favorite Pets Name?", "Iggy", "Joseph Jo");
		Customer cust11 = new Customer(111, "password", "Favorite Pets Name?", "Red", "Muhammad Avdol");
		cust10.addAccount(accountType.CHECKINGS, 3000,0);
		Account[] accounts0 = cust10.getAccounts();
		cust11.addAccount(accountType.SAVINGS, 4000,0);
		Account[] accounts1 = cust11.getAccounts();
		Assert.assertTrue(cust10.transferToOutsideAccounts(cust11,accounts0[0].getAccountID(),accounts1[0].getAccountID(),430.59));
	}
}