package com.jpmc.securitiesmanager.input;

import java.io.IOException;
import java.util.List;

import com.jpmc.securitiesmanager.TradeEvent;
import com.jpmc.securitiesmanager.exceptions.InvalidInputException;

/**
 * Interface to Read Input files for Securities Application
 *  
 * @author Harshul Gandhi
 *
 */
public interface InputReader {
	/**
	 * Parses given file in any format and forms a list of TradeEvent objects.
	 * 
	 * @param fileName
	 * @return list of TradeEvent objects
	 * @throws IOException if File Input is invalid or does not exist
	 */
	public List<TradeEvent> parse(String fileName) throws IOException, InvalidInputException;
}
