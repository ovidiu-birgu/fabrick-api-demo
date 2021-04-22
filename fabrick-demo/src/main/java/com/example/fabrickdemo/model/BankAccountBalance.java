package com.example.fabrickdemo.model;

import java.util.Date;

public class BankAccountBalance {
	private Date date; 
	private double balance;
	private double availableBalance;
	private String currency;
	
	public BankAccountBalance() {
		super();		
	}
	
	public BankAccountBalance(Date date, double balance, double availableBalance, String currency) {
		super();
		this.date = date;
		this.balance = balance;
		this.availableBalance = availableBalance;
		this.currency = currency;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
