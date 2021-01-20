package com.iso.webbot;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GeneralTest {
	@Test
	@DisplayName("displayName")
	void test() {
		List<Integer> numbers = List.of(2, 3, 4, 5);
		assertAll(() -> assertEquals(1, numbers.get(0)),
					() -> assertEquals(1, numbers.get(1)),
					() -> assertEquals(1, numbers.get(2)),
					() -> assertEquals(1, numbers.get(3)));
	}
	
}
