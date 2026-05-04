package Tests;

import org.junit.Assert;

import org.junit.Test;

import BankingSystem.*;

public class CheckingsAccountTest {
	
	@Test
	public void CheckingsAccountConstructorTest() {
		Account checkAcc0 = new CheckingsAccount(2000,0);
		Assert.assertNotNull(checkAcc0);
	}
	@Test
	public void freezeTest() {
		Account checkAcc1 = new CheckingsAccount(2000,0);
		checkAcc1.freeze();
		Assert.assertTrue(checkAcc1.getIsFrozen());
	}
	@Test
	public void thawTest() {
		Account checkAcc2 = new CheckingsAccount(2000,0);
		checkAcc2.thaw();
		Assert.assertFalse(checkAcc2.getIsFrozen());
	}
	@Test
	public void getBalanceTest() {
		Account checkAcc3 = new CheckingsAccount(2000,0);
		Assert.assertEquals(checkAcc3.getBalance(),2000,0.01);
	}
	@Test 
	public void getAccTypeTest() {
		Account checkAcc4 = new CheckingsAccount(2000,0);
		Assert.assertEquals(checkAcc4.getAccType(),accountType.CHECKINGS);
	}
	@Test
	public void getAccountIDTest() {
		Account checkAcc5 = new CheckingsAccount(2000,0);
		Assert.assertTrue(checkAcc5.getAccountID()>=0);
	}
	@Test
	public void depositTest1() {
		Account checkAcc6 = new CheckingsAccount(2000,0);
		checkAcc6.deposit(100.11);
		Assert.assertEquals(checkAcc6.getBalance(),2100.11,0.01);
	}
	@Test
	public void withdrawalTest1() {
		Account checkAcc7 = new CheckingsAccount(2000,0);
		checkAcc7.withdrawal(999.45);
		Assert.assertEquals(checkAcc7.getBalance(),1000.55,0.01);
	}
	@Test
	public void depositTest2() {
		Account checkAcc8 = new CheckingsAccount(2000,0);
		Assert.assertFalse(checkAcc8.deposit(-100.11));
	}
	@Test
	public void withdrawalTest2() {
		Account checkAcc9 = new CheckingsAccount(2000,0);
		Assert.assertFalse(checkAcc9.withdrawal(3000));
	}
}