package com.jpmc.securitiesmanager.output;

import java.io.IOException;
import java.util.List;

import com.jpmc.securitiesmanager.PositionRecord;

/**
 * Output Writer for writing Position Records to a file
 *  
 * @author Harshul Gandhi
 *
 */
public interface OutputWriter {
	/**
	 * Write position records to Given file
	 * 
	 * @param records
	 * @param file
	 * @throws IOException
	 */
	public void write(List<PositionRecord> records, String file) throws IOException;
}
