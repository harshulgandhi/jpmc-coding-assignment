package com.jpmc.securitiesmanager.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jpmc.securitiesmanager.TradeEvent;
import com.jpmc.securitiesmanager.exceptions.InvalidInputException;
import com.jpmc.securitiesmanager.validation.TradeValidator;
import com.jpmc.securitiesmanager.validation.TradeValidatorImpl;

/**
 * CSV Reader Implementation of InputReader
 * 
 * @author Harshul Gandhi
 *
 */
public class CsvInputReaderImpl implements InputReader {
	
	@Override
	public List<TradeEvent> parse(String fileName) throws IOException, InvalidInputException {
		List<TradeEvent> tradeEvents = new ArrayList<TradeEvent>();
		Set<String> seenEvents = new HashSet<>();
		TradeValidator validator = new TradeValidatorImpl();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
			String sCurrentLine;
			int row = 1;
			
			while ((sCurrentLine = br.readLine()) != null) {
				String[] input = sCurrentLine.split(",");
				validator.validate(input, row);
				
				String eventIdentifier = input[0] + ":" + input[1];
				if(seenEvents.contains(eventIdentifier)) {
					throw new InvalidInputException("Invalid Input format. Trade ID and version must be unique");
				}
				
				seenEvents.add(eventIdentifier);
				TradeEvent tradeEvent = buildTradeEvent(input);
				tradeEvents.add(tradeEvent);
				row ++;
			}
		} catch (IOException e) {
			throw new IOException();
		} 
		return tradeEvents;
	}

	private TradeEvent buildTradeEvent(String[] input) {
		TradeEvent tradeEvent = new TradeEvent(Integer.valueOf(input[0]), 
				Integer.valueOf(input[1]), 
				input[2], 
				Integer.valueOf(input[3]), 
				input[4], 
				input[5],
				input[6]);
		return tradeEvent;
	}
}
