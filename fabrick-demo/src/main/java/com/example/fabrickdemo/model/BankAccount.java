package com.example.fabrickdemo.model;

public class BankAccount {
	private long accountId;
	private double balance;
	
	public BankAccount(long accountId, double balance) {
		super();
		this.accountId = accountId;
		this.balance = balance;
	}
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "BankAccount [accountId=" + accountId + ", balance=" + balance + "]";
	}	
}
