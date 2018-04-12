package com.jpmc.securitiesmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SecurityManagerTest {

	static SecurityManager securityManager;

	@BeforeClass
	public static void init() {
		securityManager = new SecurityManager();
	}

	@Test
	public void testThatGivenInputGeneratesExpectedOutput() {
		List<TradeEvent> events = TradeEventFactory.getProvidedInput();
		List<PositionRecord> result = securityManager.realTimePosition(events);
		List<PositionRecord> expected = PositionRecordFactory.getProvidedOutput();

		Assert.assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
	}

	@Test
	public void testThatOutOfOrderInputGivesExpectedOutput() {
		List<TradeEvent> events = TradeEventFactory.getOutOfOrderInput();
		List<PositionRecord> result = securityManager.realTimePosition(events);
		List<PositionRecord> expected = PositionRecordFactory.getOutputForOutOfOrderInput();

		Assert.assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
	}

	@Test
	public void testThatMultipleUniqueTradesWithoutValidVersionGivesExpectedOutput() {
		List<TradeEvent> events = TradeEventFactory.getInputWithTwoUniqueTradesHavingNoValidVersion();
		List<PositionRecord> result = securityManager.realTimePosition(events);
		List<PositionRecord> expected = PositionRecordFactory.getOutputForTwoUniqueTradeWithNoValidVersion();

		Assert.assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
	}

	@Test
	public void testThatInputWithMissingIntermediateVersionOfTradesGiveExpectedOutput() {
		List<TradeEvent> events = TradeEventFactory.getInputMissingIntermediateVersionsOfTrade();
		List<PositionRecord> result = securityManager.realTimePosition(events);
		List<PositionRecord> expected = PositionRecordFactory.getOutputForMissingIntermediateTradeVersionInput();

		Assert.assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
	}

	@Test
	public void testThatInputWithTwoSellDirectionGivesNegativeQuantity() {
		List<TradeEvent> events = TradeEventFactory.getInputWithTwoSellDirectionForUniqueTradeEvent();
		List<PositionRecord> result = securityManager.realTimePosition(events);
		List<PositionRecord> expected = PositionRecordFactory.getOutputForInputWithTwoSellDirections();

		Assert.assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
	}

	@Test
	public void testThatInputWithAllVersionsGreaterThanOne() {
		List<TradeEvent> events = TradeEventFactory.getInputWithVersionsGreaterThanOne();
		List<PositionRecord> result = securityManager.realTimePosition(events);
		List<PositionRecord> expected = PositionRecordFactory.getOutputForInputWithoutAnyVersionAsOne();

		Assert.assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
	}

	@Test
	public void testThatQuantityDecreasesWithBuyCancelCombination() {
		List<TradeEvent> events = TradeEventFactory.getInputWithBuyCancelCombination();
		List<PositionRecord> result = securityManager.realTimePosition(events);
		List<PositionRecord> expected = PositionRecordFactory.getOutputForInputWithBuyCancelCombination();

		Assert.assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
	}

	@Test
	public void testThatQuantityIncreassWithSellCancelCombination() {
		List<TradeEvent> events = TradeEventFactory.getInputWithSellCancelCombination();
		List<PositionRecord> result = securityManager.realTimePosition(events);
		List<PositionRecord> expected = PositionRecordFactory.getOutputForInputWithSellCombination();

		Assert.assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
	}

	static class TradeEventFactory {
		public static List<TradeEvent> getProvidedInput() {
			List<TradeEvent> events = new ArrayList<>();
			List<Integer> tradeId = Arrays.asList(1234, 1234, 5678, 5678, 2233, 2233, 2233, 8896, 6638, 6363, 7666,
					6363, 7666, 8686, 8686, 9654, 1025, 1036, 1025, 1122, 1122, 1122, 1144, 1144, 1155, 1155);
			List<Integer> versions = Arrays.asList(1, 2, 1, 2, 1, 2, 3, 1, 1, 1, 1, 2, 2, 1, 2, 1, 1, 1, 2, 1, 2, 3, 1,
					2, 1, 2);
			List<String> securities = Arrays.asList("XYZ", "XYZ", "QED", "QED", "RET", "RET", "RET", "YUI", "YUI",
					"HJK", "HJK", "HJK", "HJK", "FVB", "GBN", "FVB", "JKL", "JKL", "JKL", "KLO", "HJK", "KLO", "KLO",
					"KLO", "KLO", "KLO");
			List<Integer> quantity = Arrays.asList(100, 150, 200, 0, 100, 400, 0, 300, 100, 200, 200, 100, 50, 100, 100,
					200, 100, 100, 100, 100, 100, 100, 300, 400, 600, 0);
			List<String> direction = Arrays.asList("BUY", "BUY", "BUY", "BUY", "SELL", "SELL", "SELL", "BUY", "SELL",
					"BUY", "BUY", "BUY", "SELL", "BUY", "BUY", "BUY", "BUY", "BUY", "SELL", "BUY", "SELL", "SELL",
					"BUY", "BUY", "SELL", "BUY");
			List<String> account = Arrays.asList("ACC-1234", "ACC-1234", "ACC-2345", "ACC-2345", "ACC-3456", "ACC-3456",
					"ACC-3456", "ACC-4567", "ACC-4567", "ACC-5678", "ACC-5678", "ACC-5678", "ACC-5678", "ACC-6789",
					"ACC-6789", "ACC-6789", "ACC-7789", "ACC-7789", "ACC-8877", "ACC-9045", "ACC-9045", "ACC-9045",
					"ACC-9045", "ACC-9045", "ACC-9045", "ACC-9045");
			List<String> operation = Arrays.asList("NEW", "AMEND", "NEW", "CANCEL", "NEW", "AMEND", "CANCEL", "NEW",
					"NEW", "NEW", "NEW", "AMEND", "AMEND", "NEW", "AMEND", "NEW", "NEW", "NEW", "AMEND", "NEW", "AMEND",
					"AMEND", "NEW", "AMEND", "NEW", "CANCEL");

			for (int i = 0; i < 26; i++) {
				events.add(new TradeEvent(tradeId.get(i), versions.get(i), securities.get(i), quantity.get(i),
						direction.get(i), account.get(i), operation.get(i)));
			}
			return events;
		}

		public static List<TradeEvent> getInputWithTwoUniqueTradesHavingNoValidVersion() {
			List<TradeEvent> events = new ArrayList<>();
			List<Integer> tradeId = Arrays.asList(1122, 1122, 1122, 1144, 1144, 1144, 1155, 1155);
			List<Integer> versions = Arrays.asList(1, 2, 3, 1, 2, 3, 1, 2);
			List<String> securities = Arrays.asList("KLO", "HJK", "KLO", "KLO", "HJK", "KLO", "KLO", "KLO");
			List<Integer> quantity = Arrays.asList(100, 100, 100, 300, 200, 400, 600, 0);
			List<String> direction = Arrays.asList("BUY", "SELL", "SELL", "BUY", "BUY", "BUY", "SELL", "BUY");
			List<String> account = Arrays.asList("ACC-9045", "ACC-9045", "ACC-9045", "ACC-9045", "ACC-9045", "ACC-9045",
					"ACC-9045", "ACC-9045");
			List<String> operation = Arrays.asList("NEW", "AMEND", "AMEND", "NEW", "NEW", "AMEND", "NEW", "CANCEL");

			for (int i = 0; i < 8; i++) {
				events.add(new TradeEvent(tradeId.get(i), versions.get(i), securities.get(i), quantity.get(i),
						direction.get(i), account.get(i), operation.get(i)));
			}
			return events;
		}

		public static List<TradeEvent> getInputMissingIntermediateVersionsOfTrade() {
			List<TradeEvent> events = new ArrayList<>();
			List<Integer> tradeId = Arrays.asList(1122, 1122, 1144, 1144, 1155, 1155);
			List<Integer> versions = Arrays.asList(1, 3, 1, 3, 1, 2);
			List<String> securities = Arrays.asList("KLO", "KLO", "KLO", "KLO", "KLO", "KLO");
			List<Integer> quantity = Arrays.asList(100, 100, 300, 400, 600, 0);
			List<String> direction = Arrays.asList("BUY", "SELL", "BUY", "BUY", "SELL", "BUY");
			List<String> account = Arrays.asList("ACC-9045", "ACC-9045", "ACC-9045", "ACC-9045", "ACC-9045",
					"ACC-9045");
			List<String> operation = Arrays.asList("NEW", "AMEND", "NEW", "AMEND", "NEW", "CANCEL");

			for (int i = 0; i < 6; i++) {
				events.add(new TradeEvent(tradeId.get(i), versions.get(i), securities.get(i), quantity.get(i),
						direction.get(i), account.get(i), operation.get(i)));
			}
			return events;
		}

		public static List<TradeEvent> getInputWithTwoSellDirectionForUniqueTradeEvent() {
			List<TradeEvent> events = new ArrayList<>();
			List<Integer> tradeId = Arrays.asList(1234, 1234, 5678, 7867);
			List<Integer> versions = Arrays.asList(1, 2, 1, 1);
			List<String> securities = Arrays.asList("XYZ", "XYZ", "XYZ", "ABC");
			List<Integer> quantity = Arrays.asList(100, 200, 600, 150);
			List<String> direction = Arrays.asList("BUY", "SELL", "SELL", "BUY");
			List<String> account = Arrays.asList("ACC-2345", "ACC-2345", "ACC-2345", "ACC-1234");
			List<String> operation = Arrays.asList("NEW", "NEW", "NEW", "AMEND");

			for (int i = 0; i < 4; i++) {
				events.add(new TradeEvent(tradeId.get(i), versions.get(i), securities.get(i), quantity.get(i),
						direction.get(i), account.get(i), operation.get(i)));
			}
			return events;
		}

		public static List<TradeEvent> getInputWithVersionsGreaterThanOne() {
			List<TradeEvent> events = new ArrayList<>();
			List<Integer> tradeId = Arrays.asList(1122, 1122, 1122, 1144, 1144, 1144, 1155, 1155);
			List<Integer> versions = Arrays.asList(2, 3, 4, 2, 3, 4, 2, 3);
			List<String> securities = Arrays.asList("KLO", "HJK", "KLO", "KLO", "HJK", "KLO", "KLO", "KLO");
			List<Integer> quantity = Arrays.asList(100, 100, 100, 300, 200, 400, 600, 0);
			List<String> direction = Arrays.asList("BUY", "SELL", "SELL", "BUY", "BUY", "BUY", "SELL", "BUY");
			List<String> account = Arrays.asList("ACC-9045", "ACC-9045", "ACC-9045", "ACC-9045", "ACC-9045", "ACC-9045",
					"ACC-9045", "ACC-9045");
			List<String> operation = Arrays.asList("NEW", "AMEND", "AMEND", "NEW", "NEW", "AMEND", "NEW", "CANCEL");

			for (int i = 0; i < 8; i++) {
				events.add(new TradeEvent(tradeId.get(i), versions.get(i), securities.get(i), quantity.get(i),
						direction.get(i), account.get(i), operation.get(i)));
			}
			return events;
		}

		public static List<TradeEvent> getInputWithBuyCancelCombination() {
			List<TradeEvent> events = new ArrayList<>();
			List<Integer> tradeId = Arrays.asList(1155, 1155);
			List<Integer> versions = Arrays.asList(1, 2);
			List<String> securities = Arrays.asList("KLO", "KLO");
			List<Integer> quantity = Arrays.asList(600, 800);
			List<String> direction = Arrays.asList("SELL", "BUY");
			List<String> account = Arrays.asList("ACC-9045", "ACC-9045");
			List<String> operation = Arrays.asList("NEW", "CANCEL");

			for (int i = 0; i < 2; i++) {
				events.add(new TradeEvent(tradeId.get(i), versions.get(i), securities.get(i), quantity.get(i),
						direction.get(i), account.get(i), operation.get(i)));
			}
			return events;
		}

		public static List<TradeEvent> getInputWithSellCancelCombination() {
			List<TradeEvent> events = new ArrayList<>();
			List<Integer> tradeId = Arrays.asList(1155, 1155);
			List<Integer> versions = Arrays.asList(1, 2);
			List<String> securities = Arrays.asList("KLO", "KLO");
			List<Integer> quantity = Arrays.asList(600, 800);
			List<String> direction = Arrays.asList("BUY", "SELL");
			List<String> account = Arrays.asList("ACC-9045", "ACC-9045");
			List<String> operation = Arrays.asList("NEW", "CANCEL");

			for (int i = 0; i < 2; i++) {
				events.add(new TradeEvent(tradeId.get(i), versions.get(i), securities.get(i), quantity.get(i),
						direction.get(i), account.get(i), operation.get(i)));
			}
			return events;
		}

		public static List<TradeEvent> getOutOfOrderInput() {
			List<TradeEvent> events = new ArrayList<>();
			List<Integer> tradeId = Arrays.asList(1234, 5678, 5678, 1234);
			List<Integer> versions = Arrays.asList(1, 1, 2, 2);
			List<String> securities = Arrays.asList("XYZ", "QED", "QED", "XYZ");
			List<Integer> quantity = Arrays.asList(100, 200, 0, 150);
			List<String> direction = Arrays.asList("BUY", "BUY", "BUY", "BUY");
			List<String> account = Arrays.asList("ACC-1234", "ACC-2345", "ACC-2345", "ACC-1234");
			List<String> operation = Arrays.asList("NEW", "NEW", "CANCEL", "AMEND");

			for (int i = 0; i < 4; i++) {
				events.add(new TradeEvent(tradeId.get(i), versions.get(i), securities.get(i), quantity.get(i),
						direction.get(i), account.get(i), operation.get(i)));
			}
			return events;
		}
	}

	static class PositionRecordFactory {
		public static List<PositionRecord> getProvidedOutput() {
			List<PositionRecord> positionRecord = new ArrayList<>();
			List<String> account = Arrays.asList("ACC-1234", "ACC-2345", "ACC-3456", "ACC-4567", "ACC-5678", "ACC-6789",
					"ACC-6789", "ACC-7789", "ACC-8877", "ACC-9045", "ACC-9045");
			List<String> security = Arrays.asList("XYZ", "QED", "RET", "YUI", "HJK", "GBN", "FVB", "JKL", "JKL", "KLO",
					"HJK");
			List<Integer> quantity = Arrays.asList(150, 0, 0, 200, 50, 100, 200, 100, -100, 300, 0);
			List<Set<Integer>> trades = Arrays.asList(new HashSet<>(Arrays.asList(1234)),
					new HashSet<>(Arrays.asList(5678)), new HashSet<>(Arrays.asList(2233)),
					new HashSet<>(Arrays.asList(8896, 6638)), new HashSet<>(Arrays.asList(6363, 7666)),
					new HashSet<>(Arrays.asList(8686)), new HashSet<>(Arrays.asList(9654, 8686)),
					new HashSet<>(Arrays.asList(1036, 1025)), new HashSet<>(Arrays.asList(1025)),
					new HashSet<>(Arrays.asList(1122, 1144, 1155)), new HashSet<>(Arrays.asList(1122)));

			for (int i = 0; i < 11; i++) {
				PositionRecord record = new PositionRecord(account.get(i), security.get(i));
				record.setTrades(trades.get(i));
				record.setQuantity(quantity.get(i));
				positionRecord.add(record);
			}

			return positionRecord;
		}

		public static List<PositionRecord> getOutputForTwoUniqueTradeWithNoValidVersion() {
			List<PositionRecord> positionRecord = new ArrayList<>();
			List<String> account = Arrays.asList("ACC-9045", "ACC-9045");
			List<String> security = Arrays.asList("KLO", "HJK");
			List<Integer> quantity = Arrays.asList(300, 0);
			List<Set<Integer>> trades = Arrays.asList(new HashSet<>(Arrays.asList(1122, 1144, 1155)),
					new HashSet<>(Arrays.asList(1122, 1144)));

			for (int i = 0; i < 2; i++) {
				PositionRecord record = new PositionRecord(account.get(i), security.get(i));
				record.setTrades(trades.get(i));
				record.setQuantity(quantity.get(i));
				positionRecord.add(record);
			}

			return positionRecord;
		}

		public static List<PositionRecord> getOutputForMissingIntermediateTradeVersionInput() {
			List<PositionRecord> positionRecord = new ArrayList<>();
			List<String> account = Arrays.asList("ACC-9045");
			List<String> security = Arrays.asList("KLO");
			List<Integer> quantity = Arrays.asList(300);
			List<Set<Integer>> trades = Arrays.asList(new HashSet<>(Arrays.asList(1122, 1144, 1155)));

			for (int i = 0; i < 1; i++) {
				PositionRecord record = new PositionRecord(account.get(i), security.get(i));
				record.setTrades(trades.get(i));
				record.setQuantity(quantity.get(i));
				positionRecord.add(record);
			}

			return positionRecord;
		}

		public static List<PositionRecord> getOutputForInputWithTwoSellDirections() {
			List<PositionRecord> positionRecord = new ArrayList<>();
			List<String> account = Arrays.asList("ACC-1234", "ACC-2345");
			List<String> security = Arrays.asList("ABC", "XYZ");
			List<Integer> quantity = Arrays.asList(150, -800);
			List<Set<Integer>> trades = Arrays.asList(new HashSet<>(Arrays.asList(7867)),
					new HashSet<>(Arrays.asList(1234, 5678)));

			for (int i = 0; i < 2; i++) {
				PositionRecord record = new PositionRecord(account.get(i), security.get(i));
				record.setTrades(trades.get(i));
				record.setQuantity(quantity.get(i));
				positionRecord.add(record);
			}

			return positionRecord;
		}

		public static List<PositionRecord> getOutputForInputWithoutAnyVersionAsOne() {
			List<PositionRecord> positionRecord = new ArrayList<>();
			List<String> account = Arrays.asList("ACC-9045", "ACC-9045");
			List<String> security = Arrays.asList("KLO", "HJK");
			List<Integer> quantity = Arrays.asList(300, 0);
			List<Set<Integer>> trades = Arrays.asList(new HashSet<>(Arrays.asList(1122, 1144, 1155)),
					new HashSet<>(Arrays.asList(1122, 1144)));

			for (int i = 0; i < 2; i++) {
				PositionRecord record = new PositionRecord(account.get(i), security.get(i));
				record.setTrades(trades.get(i));
				record.setQuantity(quantity.get(i));
				positionRecord.add(record);
			}

			return positionRecord;
		}

		public static List<PositionRecord> getOutputForInputWithBuyCancelCombination() {
			List<PositionRecord> positionRecord = new ArrayList<>();
			List<String> account = Arrays.asList("ACC-9045");
			List<String> security = Arrays.asList("KLO");
			List<Integer> quantity = Arrays.asList(-800);
			List<Set<Integer>> trades = Arrays.asList(new HashSet<>(Arrays.asList(1155)));

			for (int i = 0; i < 1; i++) {
				PositionRecord record = new PositionRecord(account.get(i), security.get(i));
				record.setTrades(trades.get(i));
				record.setQuantity(quantity.get(i));
				positionRecord.add(record);
			}

			return positionRecord;
		}

		public static List<PositionRecord> getOutputForInputWithSellCombination() {
			List<PositionRecord> positionRecord = new ArrayList<>();
			List<String> account = Arrays.asList("ACC-9045");
			List<String> security = Arrays.asList("KLO");
			List<Integer> quantity = Arrays.asList(800);
			List<Set<Integer>> trades = Arrays.asList(new HashSet<>(Arrays.asList(1155)));

			for (int i = 0; i < 1; i++) {
				PositionRecord record = new PositionRecord(account.get(i), security.get(i));
				record.setTrades(trades.get(i));
				record.setQuantity(quantity.get(i));
				positionRecord.add(record);
			}

			return positionRecord;
		}

		public static List<PositionRecord> getOutputForOutOfOrderInput() {
			List<PositionRecord> positionRecord = new ArrayList<>();
			List<String> account = Arrays.asList("ACC-1234", "ACC-2345");
			List<String> security = Arrays.asList("XYZ", "QED");
			List<Integer> quantity = Arrays.asList(150, 0);
			List<Set<Integer>> trades = Arrays.asList(new HashSet<>(Arrays.asList(1234)),
					new HashSet<>(Arrays.asList(5678)));

			for (int i = 0; i < 2; i++) {
				PositionRecord record = new PositionRecord(account.get(i), security.get(i));
				record.setTrades(trades.get(i));
				record.setQuantity(quantity.get(i));
				positionRecord.add(record);
			}

			return positionRecord;
		}

	}
}
