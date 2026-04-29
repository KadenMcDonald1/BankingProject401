package Tests;

import org.junit.Assert;

import org.junit.Test;

import BankingSystem.*;

public class SavingsAccountTest {
	
	@Test
	public void SavingsAccountConstructorTest() {
		Account saveAcc0 = new SavingsAccount(2000);
		Assert.assertNotNull(saveAcc0);
	}
	@Test
	public void freezeTest() {
		Account saveAcc1 = new SavingsAccount(2000);
		saveAcc1.freeze();
		Assert.assertTrue(saveAcc1.getIsFrozen());
	}
	@Test
	public void thawTest() {
		Account saveAcc2 = new SavingsAccount(2000);
		saveAcc2.thaw();
		Assert.assertFalse(saveAcc2.getIsFrozen());
	}
	@Test
	public void getBalanceTest() {
		Account saveAcc3 = new SavingsAccount(2000);
		Assert.assertEquals(saveAcc3.getBalance(),2000,0.01);
	}
	@Test 
	public void getAccTypeTest() {
		Account saveAcc4 = new SavingsAccount(2000);
		Assert.assertEquals(saveAcc4.getAccType(),accountType.SAVINGS);
	}
	@Test
	public void getAccountIDTest() {
		Account saveAcc5 = new SavingsAccount(2000);
		Assert.assertTrue(saveAcc5.getAccountID()>=0);
	}
	@Test
	public void depositTest1() {
		Account saveAcc6 = new SavingsAccount(2000);
		saveAcc6.deposit(100.11);
		Assert.assertEquals(saveAcc6.getBalance(),2100.11,0.01);
	}
	@Test
	public void withdrawalTest1() {
		Account saveAcc7 = new SavingsAccount(2000);
		saveAcc7.withdrawal(499.45);
		Assert.assertEquals(saveAcc7.getBalance(),1500.55,0.01);
	}
	@Test
	public void depositTest2() {
		Account saveAcc8 = new SavingsAccount(2000);
		Assert.assertFalse(saveAcc8.deposit(-100.11));
	}
	@Test
	public void withdrawalTest2() {
		Account saveAcc9 = new SavingsAccount(2000);
		Assert.assertFalse(saveAcc9.withdrawal(3000));
	}
}