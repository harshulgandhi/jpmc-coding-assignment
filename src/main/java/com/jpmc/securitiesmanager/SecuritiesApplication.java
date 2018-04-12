package com.jpmc.securitiesmanager;

import java.io.IOException;
import java.util.List;

import com.jpmc.securitiesmanager.exceptions.InvalidInputException;
import com.jpmc.securitiesmanager.input.CsvInputReaderImpl;
import com.jpmc.securitiesmanager.input.InputReader;
import com.jpmc.securitiesmanager.output.CsvOutputWriterImpl;
import com.jpmc.securitiesmanager.output.OutputWriter;

/**
 * Main Entry class for Securities Application
 * 
 * @author Harshul Gandhi
 */
public class SecuritiesApplication {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("No input file specified");
			System.err.println("Usage: java -jar jarfile.jar <input_file_name.csv>");
			System.exit(1);
		}
		String fileName = args[0];
		final String OUTPUT_FILE = "position_records.csv";
		InputReader reader = new CsvInputReaderImpl();
		SecurityManager securityManager = new SecurityManager();
		OutputWriter writer = new CsvOutputWriterImpl();
		try {
			List<TradeEvent> tradeEvents = reader.parse(fileName);
			List<PositionRecord> positionRecords = securityManager.realTimePosition(tradeEvents);
			writer.write(positionRecords, OUTPUT_FILE);

		} catch (IOException e) {
			System.err.println("An Error occurred while processing the file");
			System.err.println("The error stack trace is: " + e);

		} catch (InvalidInputException e) {
			System.err.println("An Error occurred while parsing due to Invalid Input format");
			System.err.println("The error stack trace is: " + e);
		}
	}
}
