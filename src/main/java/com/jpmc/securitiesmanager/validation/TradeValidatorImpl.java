package com.jpmc.securitiesmanager.validation;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.jpmc.securitiesmanager.constants.Direction;
import com.jpmc.securitiesmanager.constants.Operation;
import com.jpmc.securitiesmanager.exceptions.InvalidInputException;

/**
 * Validate Trade Input CSV rows 
 * 
 * @author Harshul Gandhi
 *
 */
public class TradeValidatorImpl implements TradeValidator {
	
	@Override
	public void validate(String[] input, int row) throws InvalidInputException {
		if(input.length != 7) {
			throw new InvalidInputException("Less columns found than 6 expected at row: " + row);
		}
		
		if(!allFieldsPresent(input)) {
			throw new InvalidInputException("Empty values found in CSV at row: " + row);
		}
		
		if(!isTradeIdValid(input[0])) {
			throw new InvalidInputException("Trade ID is invalid at row: "  + row);
		}
		
		if(!isTradeVersionValid(input[1])) {
			throw new InvalidInputException("Trade version is invalid at row: "  + row);
		}
		
		if(!isSecurityIdValid(input[2])) {
			throw new InvalidInputException("Trade version is invalid at row: "  + row);
		}
		
		if(!isTradeQuantityValid(input[3])) {
			throw new InvalidInputException("Trade Quantity invalid at row: "  + row);
		}
		
		if(!isTradeDirectionValid(input[4])) {
			throw new InvalidInputException("Invalid Trade Direction at row: "  + row);
		}
		
		if(!isAccountNumberValid(input[5])) {
			throw new InvalidInputException("Invalid Account number at row: "  + row);
		}
		
		if(!isOperationValid(input[6])) {
			throw new InvalidInputException("Invalid Operation at row: "  + row);
		}
	}
	
	/**
	 * Check if Operation Type is valid
	 * 
	 * @param string
	 * @return
	 */
	private boolean isOperationValid(String string) {
		try {
			Operation op = Operation.valueOf(string.trim());
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Check if Account Number is valid. Can validate from other
	 * business validation util
	 * 
	 * @param string
	 * @return
	 */
	private boolean isAccountNumberValid(String string) {
		return true;
	}

	/**
	 * Check if trade direction is valid 
	 * 
	 * @param string
	 * @return
	 */
	private boolean isTradeDirectionValid(String string) {
		try {
			Direction.valueOf(string.trim());
		} catch(IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Check if security ID specified is valid. Can validate from other
	 * business validation util
	 * 
	 * @param string
	 * @return
	 */
	private boolean isSecurityIdValid(String string) {
		return true;
	}

	/**
	 * Check if trade version is valid. Can validate from 
	 * other business validation util
	 * 
	 * @param string
	 * @return
	 */
	private boolean isTradeVersionValid(String input) {
		try {
			Integer ver = Integer.parseInt(input.trim());
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Check if Trade ID is a valid. Can validate from 
	 * some business validation util 
	 * 
	 * @param string
	 * @return
	 */
	private boolean isTradeIdValid(String input) {
		try {
			Integer tradeId = Integer.parseInt(input.trim());
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Check if trade quantity is a valid integer and non-negative
	 * 
	 * @param input
	 * @return
	 */
	private boolean isTradeQuantityValid(String input) {
		Integer tradeQuantity = -1;
		try {
			tradeQuantity = Integer.parseInt(input.trim());
		} catch(NumberFormatException e) {
			return false;
		}
		return tradeQuantity >= 0;
	}
	
	/**
	 * Check if any field in the row is empty
	 * 
	 * @param inputStrings
	 * @return
	 */
	private boolean allFieldsPresent(String[] inputStrings) {
		Predicate<String> notEmpty = str -> str != null && !str.isEmpty();
		return Stream.of(inputStrings[0], 
				inputStrings[1], 
				inputStrings[2],
				inputStrings[3], 
				inputStrings[4], 
				inputStrings[5], 
				inputStrings[6])
			.allMatch(notEmpty); 
	}
}
