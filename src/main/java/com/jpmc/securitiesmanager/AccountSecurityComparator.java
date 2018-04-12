package com.jpmc.securitiesmanager;

import java.util.Comparator;

/**
 * This comparator will sort TradeEvent list with Account Number
 * and Security Identifier
 * 
 * @author Harshul Gandhi
 *
 */
public class AccountSecurityComparator implements Comparator<TradeEvent> {

	public int compare(TradeEvent o1, TradeEvent o2) {
		if(o1.getAccountNumber().equals(o2.getAccountNumber())) {
			return o1.getSecurityId().compareTo(o2.getSecurityId());
		} else {
			return o1.getAccountNumber().compareTo(o2.getAccountNumber());
		}
	}
	
}
