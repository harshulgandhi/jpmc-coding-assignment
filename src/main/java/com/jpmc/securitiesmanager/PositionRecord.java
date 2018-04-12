package com.jpmc.securitiesmanager;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.jpmc.securitiesmanager.constants.Direction;
import com.jpmc.securitiesmanager.constants.Operation;

/**
 * This entity represents an output record encapsulating all the attributes of a
 * real time position record.
 * 
 * @author Harshul Gandhi
 *
 */
public class PositionRecord {
	private String accountNumber;
	private String instrument;
	private int quantity = 0;
	private Set<Integer> trades = new HashSet<Integer>();

	public PositionRecord(String accountNumber, String instrument) {
		this.accountNumber = accountNumber;
		this.instrument = instrument;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public int getQuantity() {
		return quantity;
	}

	public void updateQuantity(TradeEvent tradeEvent) {
		if (!tradeEvent.isCountable()) {
			return;
		}
		if ((isBuy(tradeEvent.getDirection()) && isAmendOrNew(tradeEvent.getOperation()))
				|| (isSell(tradeEvent.getDirection()) && isCancel(tradeEvent.getOperation()))) {
			this.quantity += tradeEvent.getTradeQuantity();
		}
		if ((isSell(tradeEvent.getDirection()) && isAmendOrNew(tradeEvent.getOperation()))
				|| (isBuy(tradeEvent.getDirection()) && isCancel(tradeEvent.getOperation()))) {
			this.quantity -= tradeEvent.getTradeQuantity();
		}
	}

	private boolean isBuy(String direction) {
		return direction.equals(Direction.BUY.name());
	}

	private boolean isSell(String direction) {
		return direction.equals(Direction.SELL.name());
	}

	private boolean isAmendOrNew(String operation) {
		return operation.equals(Operation.AMEND.name()) || operation.equals(Operation.NEW.name());
	}

	private boolean isCancel(String operation) {
		return operation.equals(Operation.CANCEL.name());
	}

	public Set<Integer> getTrades() {
		return trades;
	}

	public void addTrades(Integer trade) {
		trades.add(trade);
	}

	public void setTrades(Set<Integer> trades) {
		this.trades = trades;
	}

	@Override
	public String toString() {
		return this.accountNumber + "," + this.instrument + "," + this.quantity + "," + this.trades;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object o) {
		PositionRecord rtp = (PositionRecord) o;
		return this.accountNumber.equals(rtp.getAccountNumber()) && this.instrument.equals(rtp.getInstrument())
				&& this.quantity == rtp.getQuantity() && this.trades.equals(rtp.getTrades());

	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNumber, instrument, quantity, trades);
	}
}
