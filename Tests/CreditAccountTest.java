package Tests;

import org.junit.Assert;

import org.junit.Test;

import BankingSystem.*;

public class CreditAccountTest {
	
	@Test
	public void CreditAccountConstructorTest() {
		Account credAcc0 = new CreditAccount();
		Assert.assertNotNull(credAcc0);
	}
	@Test
	public void freezeTest() {
		Account credAcc1 = new CreditAccount();
		credAcc1.freeze();
		Assert.assertTrue(credAcc1.getIsFrozen());
	}
	@Test
	public void thawTest() {
		Account credAcc2 = new CreditAccount();
		credAcc2.thaw();
		Assert.assertFalse(credAcc2.getIsFrozen());
	}
	@Test
	public void getBalanceTest() {
		Account credAcc3 = new CreditAccount();
		Assert.assertEquals(credAcc3.getBalance(),0,0.01);
	}
	@Test 
	public void getAccTypeTest() {
		Account credAcc4 = new CreditAccount();
		Assert.assertEquals(credAcc4.getAccType(),accountType.CREDIT);
	}
	@Test
	public void getAccountIDTest() {
		Account credAcc5 = new CreditAccount();
		Assert.assertTrue(credAcc5.getAccountID()>=0);
	}
	@Test
	public void depositTest1() {
		Account credAcc6 = new CreditAccount();
		credAcc6.deposit(50);
		Assert.assertEquals(credAcc6.getBalance(),50,0.01);
	}
	@Test
	public void withdrawalTest1() {
		Account credAcc7 = new CreditAccount();
		credAcc7.withdrawal(999.45);
		Assert.assertEquals(credAcc7.getBalance(),-999.45,0.01);
	}
	@Test
	public void depositTest2() {
		Account credAcc8 = new CreditAccount();
		Assert.assertFalse(credAcc8.deposit(-100.11));
	}
	@Test
	public void withdrawalTest2() {
		Account credAcc9 = new CreditAccount();
		Assert.assertFalse(credAcc9.withdrawal(3000));
	}
}