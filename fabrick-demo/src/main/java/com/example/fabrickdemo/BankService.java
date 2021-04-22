package com.example.fabrickdemo;

import org.springframework.stereotype.Service;

import com.example.fabrickdemo.model.BankAccountTransaction;

@Service
public class BankService {
	
	public boolean isAccountValid(Long accountId) {
		//TODO
		return true;
	}
	
	public double getBankAccountBalance(Long accountId) {
		//TODO
		return 0.1;
	}
	
	public boolean doBankAccountTransaction(BankAccountTransaction accountTransaction) {
		//TODO 
		return true;
	}
	
	public String getAccountTransactions(Long accountId, String fromAccountingDate, String toAccountingDate) {
		//TODO 
		return "T1 T2 T3";
	}	
	
}
