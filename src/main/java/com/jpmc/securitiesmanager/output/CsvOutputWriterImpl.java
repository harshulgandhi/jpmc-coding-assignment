package com.jpmc.securitiesmanager.output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.jpmc.securitiesmanager.PositionRecord;

/**
 * CSV Writer Implementation of OutputWriter 
 * 
 * @author Harshul Gandhi
 *
 */
public class CsvOutputWriterImpl implements OutputWriter {
	private final String NEW_LINE = "\n";
	
	@Override
	public void write(List<PositionRecord> records, String file) throws IOException {
		List<String> headers = Arrays.asList("Account", "Instrument", "Quantity", "Trades");
		try (FileWriter writer = new FileWriter(file)) {
			String header = String.join(",", headers);
			writer.write(header + NEW_LINE);
			StringBuilder strBuilder = new StringBuilder();
			
			for (PositionRecord record : records) {
				strBuilder.append(record.toString()).append(NEW_LINE);
				writer.write(strBuilder.toString());
				strBuilder.setLength(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
