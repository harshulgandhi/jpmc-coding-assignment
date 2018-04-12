package com.jpmc.securitiesmanager;

/**
 * This entity represents an input record 
 * encapsulating all the attributes of a 
 * trade event.
 * 
 * @author Harshul Gandhi
 *
 */
public class TradeEvent {

	private int tradeId;
	private int tradeVersion;
	private String securityId;
	private int tradeQuantity;
	private String direction;
	private String accountNumber;
	private String operation;
	private boolean countable = true;
	
	public TradeEvent (int tradeId,
						int tradeVersion,
						String securityId,
						int tradeQuantity,
						String direction,
						String accountNumber,
						String operation) {
		
		this.tradeId = tradeId;
		this.tradeVersion = tradeVersion;
		this.securityId = securityId;
		this.tradeQuantity = tradeQuantity;
		this.direction = direction;
		this.accountNumber = accountNumber;
		this.operation = operation;
	}
	
	public int getTradeId() {
		return tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	public int getTradeVersion() {
		return tradeVersion;
	}

	public void setTradeVersion(int tradeVersion) {
		this.tradeVersion = tradeVersion;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public int getTradeQuantity() {
		return tradeQuantity;
	}

	public void setTradeQuantity(int tradeQuantity) {
		this.tradeQuantity = tradeQuantity;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public boolean isCountable() {
		return countable;
	}

	public void setCountable(boolean countable) {
		this.countable = countable;
	}
}
