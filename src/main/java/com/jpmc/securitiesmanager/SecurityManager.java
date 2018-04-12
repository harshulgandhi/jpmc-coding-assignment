package com.jpmc.securitiesmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SecurityManager contains the core logic for 
 * calculating Real Time Position.
 * 
 * @author Harshul Gandhi
 *
 */
public class SecurityManager {
	
	/**
	 * This method takes in list of TradeEvents, performs
	 * manipulation on it and returns the list of 
	 * PositionRecord to construct real time position.
	 * 
	 * @param tradeEvents list of TradeEvent to be processed 
	 * @return list of PositionRecord
	 */
	public List<PositionRecord> realTimePosition(List<TradeEvent> tradeEvents) {
		
		filterHighestVersionTrades(tradeEvents);
		return processPosition(tradeEvents);
		
	}
	
	private void filterHighestVersionTrades(List<TradeEvent> tradeEvents) {
		sortByTrade(tradeEvents);
		
		TradeEvent previous = null;
		for (TradeEvent tradeEvent:tradeEvents) {
			if (previous != null && sameTradeId(previous, tradeEvent)) {
				previous.setCountable(false);
			}
			previous = tradeEvent;
		}
	}
	
	private List<PositionRecord> processPosition(List<TradeEvent> tradeEvents) {
		sortByAccount(tradeEvents);
		
		List<PositionRecord> positionRecords =  new ArrayList<PositionRecord>();
		TradeEvent previous = null;
		PositionRecord positionRecord = null;
		
		for (TradeEvent tradeEvent:tradeEvents) {
			if (previous == null || !sameAccountSecurity(previous, tradeEvent)) {
				positionRecord =  new PositionRecord(tradeEvent.getAccountNumber(), 
						tradeEvent.getSecurityId());
				positionRecords.add(positionRecord);
			}
			
			positionRecord.updateQuantity(tradeEvent);
			positionRecord.addTrades(tradeEvent.getTradeId());
			
			previous = tradeEvent;
		}
		
		return positionRecords;
	}
	
	private boolean sameAccountSecurity(TradeEvent tr1, TradeEvent tr2) {
		return tr1.getAccountNumber().equals(tr2.getAccountNumber())
				&& tr1.getSecurityId().equals(tr2.getSecurityId());
	}

	private void sortByAccount(List<TradeEvent> tradeEvents) {
		Collections.sort(tradeEvents, new AccountSecurityComparator());
	}

	private void sortByTrade(List<TradeEvent> tradeEvents) {
		Collections.sort(tradeEvents, new TradeComparator());
	}
	
	private boolean sameTradeId(TradeEvent tr1, TradeEvent tr2) {
		return tr1.getTradeId() == tr2.getTradeId();
	}
}
