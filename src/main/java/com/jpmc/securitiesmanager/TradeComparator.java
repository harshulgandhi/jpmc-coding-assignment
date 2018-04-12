package com.jpmc.securitiesmanager;

import java.util.Comparator;

/**
 * This comparator will sort TradeEvent list with Trade ID
 * and Trade Version
 * 
 * @author Harshul Gandhi
 *
 */
public class TradeComparator implements Comparator<TradeEvent> {

	public int compare(TradeEvent o1, TradeEvent o2) {
		if(o1.getTradeId() == o2.getTradeId()) {
			return o1.getTradeVersion() - o2.getTradeVersion();
		} else {
			return o1.getTradeId() - o2.getTradeId();
		}
	}
	
}
