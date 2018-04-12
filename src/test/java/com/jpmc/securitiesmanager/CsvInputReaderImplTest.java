package com.jpmc.securitiesmanager;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jpmc.securitiesmanager.exceptions.InvalidInputException;
import com.jpmc.securitiesmanager.input.CsvInputReaderImpl;
import com.jpmc.securitiesmanager.input.InputReader;

public class CsvInputReaderImplTest {

	static InputReader inputReader;

	@BeforeClass
	public static void init() {
		inputReader = new CsvInputReaderImpl();
	}

	@Test(expected = InvalidInputException.class)
	public void testThatDuplicateTradeIdVersionComboThrowsException() throws IOException, InvalidInputException {
		inputReader.parse("src/main/resources/test_invalid_input_duplicate_tradeId_version.txt");
	}
}
