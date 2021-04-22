package com.example.fabrickdemo.model;

public class BankAccountTransaction {
	private Long accountId;
	private String receiverName;
	private String description;
	private String currency;
	private String amount;
	private String executionDate;
	
	public BankAccountTransaction(Long accountId, String receiverName, String description, String currency,
			String amount, String executionDate) {
		super();
		this.accountId = accountId;
		this.receiverName = receiverName;
		this.description = description;
		this.currency = currency;
		this.amount = amount;
		this.executionDate = executionDate;
	}
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}

}
