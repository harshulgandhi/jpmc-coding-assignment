package com.jpmc.securitiesmanager.validation;

import com.jpmc.securitiesmanager.exceptions.InvalidInputException;

/**
 * Validates each row in the input file for Securities Manager
 * 
 * @author Harshul Gandhi
 *
 */
public interface TradeValidator {
	/**
	 * Main method for validation
	 * 
	 * @param input
	 * @throws InvalidInputException if the input format is invalid
	 */
	public void validate(String[] input, int row) throws InvalidInputException;
}
