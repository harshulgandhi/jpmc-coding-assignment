package com.jpmc.securitiesmanager;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jpmc.securitiesmanager.exceptions.InvalidInputException;
import com.jpmc.securitiesmanager.validation.TradeValidator;
import com.jpmc.securitiesmanager.validation.TradeValidatorImpl;

public class TradeValidatorImplTest {

	static TradeValidator tradeValidator;

	@BeforeClass
	public static void init() {
		tradeValidator = new TradeValidatorImpl();
	}

	@Test(expected = InvalidInputException.class)
	public void testThatIncorrectNumberOfEntriesInARowInInputShouldThrowException() throws InvalidInputException {
		tradeValidator.validate(new String[] { "1234", "2", "XYZ", "100", "BUY", "ACC-232" }, 2);
	}

	@Test(expected = InvalidInputException.class)
	public void testNullOrEmptyValuesShouldThrowException() throws InvalidInputException {
		tradeValidator.validate(new String[] { "", "2", "XYZ", null, "BUY", "ACC-232", "NEW" }, 2);
	}

	@Test(expected = InvalidInputException.class)
	public void testInvalidTradeIdShouldThrowException() throws InvalidInputException {
		tradeValidator.validate(new String[] { "a213", "2", "XYZ", "200", "BUY", "ACC-232", "NEW" }, 2);
	}

	@Test(expected = InvalidInputException.class)
	public void testInvalidTradeVersionShouldThrowException() throws InvalidInputException {
		tradeValidator.validate(new String[] { "1213", "a", "XYZ", "200", "BUY", "ACC-232", "NEW" }, 2);
	}

	@Test(expected = InvalidInputException.class)
	public void testInvalidTradeQuantityShouldThrowException() throws InvalidInputException {
		tradeValidator.validate(new String[] { "1213", "2", "XYZ", "200x", "BUY", "ACC-232", "NEW" }, 2);
	}

	@Test(expected = InvalidInputException.class)
	public void testInvalidTradeDirectionShouldThrowException() throws InvalidInputException {
		tradeValidator.validate(new String[] { "1213", "2", "XYZ", "200", "RETAIN", "ACC-232", "NEW" }, 2);
	}

	@Test(expected = InvalidInputException.class)
	public void testInvalidOperationShouldThrowException() throws InvalidInputException {
		tradeValidator.validate(new String[] { "1213", "2", "XYZ", "200", "NEW", "ACC-232", "DESOLVE" }, 2);
	}
}
