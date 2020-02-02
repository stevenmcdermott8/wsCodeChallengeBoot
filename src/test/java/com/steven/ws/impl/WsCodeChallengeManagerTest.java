package com.steven.ws.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steven.ws.bo.ZipCodeRangeBO;
import com.steven.ws.bo.ZipRangeBO;

public class WsCodeChallengeManagerTest {

	private WsCodeChallengeManager wsCodeChallengeManager = new WsCodeChallengeManager();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testDetermineRangeEmptyInput() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("The zipCodeRanges provided is null or empty. Check your parameters and try again.");
		wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(new ArrayList<List<String>>());
	}

	@Test
	public void testDetermineRangeBadFormat_valuesInArrayBadFormat() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("not a zip code", "94133"));
		list.add(Arrays.asList("94001", "94134"));
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage(
				"The list provided does not contain valid zip code values, expected format is 5 digit integer per entry, check your inputs and try again.");
		wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);
	}

	@Test
	public void testRetriveMinimumRangeSet_OneRange() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94000", "94133"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);
		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94000", "94133"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void testRetriveMinimumRangeSet_TwoRanges() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94000", "94133"));
		list.add(Arrays.asList("94001", "94134"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);
		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94000", "94134"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void testDetermineRangeBadFormat_valuesInArrayEmpty() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94000", "94133"));
		list.add(Arrays.asList("94133", "94299"));
		list.add(Arrays.asList("00000", "12345"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);
		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("00000", "12345"));
		expectedList.add(Arrays.asList("94000", "94299"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueOrderedList() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94133", "94133"));
		list.add(Arrays.asList("94200", "94299"));
		list.add(Arrays.asList("94226", "94399"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);
		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94133", "94133"));
		expectedList.add(Arrays.asList("94200", "94399"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueOrderedList2() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94000", "94133"));
		list.add(Arrays.asList("94134", "94299"));
		list.add(Arrays.asList("94600", "94699"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94000", "94133"));
		expectedList.add(Arrays.asList("94134", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueUnorderedList() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94134", "94299"));
		list.add(Arrays.asList("94600", "94699"));
		list.add(Arrays.asList("94000", "94133"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94000", "94133"));
		expectedList.add(Arrays.asList("94134", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueUnorderedList2() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94600", "94699"));
		list.add(Arrays.asList("94000", "94133"));
		list.add(Arrays.asList("94134", "94299"));
		list.add(Arrays.asList("00000", "12345"));

		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("00000", "12345"));
		expectedList.add(Arrays.asList("94000", "94133"));
		expectedList.add(Arrays.asList("94134", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromOverlappingOrderedList() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94133", "94133"));
		list.add(Arrays.asList("94200", "94299"));
		list.add(Arrays.asList("94600", "94699"));

		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94133", "94133"));
		expectedList.add(Arrays.asList("94200", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromOverlapingOrderedList2() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94133", "94133"));
		list.add(Arrays.asList("94133", "94299"));
		list.add(Arrays.asList("94600", "94699"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94133", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromOverlapingOrderedList3() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94000", "94133"));
		list.add(Arrays.asList("94133", "94299"));
		list.add(Arrays.asList("94600", "94699"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94000", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromOverlapingOrderedList6() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94000", "94134"));
		list.add(Arrays.asList("94133", "94299"));
		list.add(Arrays.asList("94600", "94699"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94000", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromListWithDuplicates() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94133", "94133"));
		list.add(Arrays.asList("94133", "94133"));
		list.add(Arrays.asList("94226", "94399"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);
		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94133", "94133"));
		expectedList.add(Arrays.asList("94226", "94399"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueUnorderedListWithOverLappingValues() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94600", "94699"));
		list.add(Arrays.asList("94000", "94133"));
		list.add(Arrays.asList("94133", "94299"));
		list.add(Arrays.asList("00000", "12345"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("00000", "12345"));
		expectedList.add(Arrays.asList("94000", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueUnorderedListWithOverLappingValues2() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94133", "94299"));
		list.add(Arrays.asList("94134", "94298"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("94133", "94299"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromUniqueUnorderedListWithValuesInWrongOrder() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("94600", "94699"));
		list.add(Arrays.asList("94133", "94000"));// higher value first
		list.add(Arrays.asList("94133", "94299"));
		list.add(Arrays.asList("00000", "12345"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("00000", "12345"));
		expectedList.add(Arrays.asList("94000", "94299"));
		expectedList.add(Arrays.asList("94600", "94699"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeOneListContainsTwoOtherUniqueLists() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("10000", "50000"));
		list.add(Arrays.asList("20000", "30000"));
		list.add(Arrays.asList("30001", "40000"));

		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("10000", "50000"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeOneListContainsTwoOtherOverlappingLists() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("10000", "50000"));
		list.add(Arrays.asList("20000", "30000"));
		list.add(Arrays.asList("29999", "40000"));

		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("10000", "50000"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeOneListContainsTwoOtherOverlappingUnorderedLists() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("29999", "40000"));
		list.add(Arrays.asList("10000", "50000"));
		list.add(Arrays.asList("20000", "30000"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);

		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("10000", "50000"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromValuesWithSpaces() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("29999  ", "40000"));
		list.add(Arrays.asList("10000", "   50000"));
		list.add(Arrays.asList("    20000", "30000"));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(list);
		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("10000", "50000"));
		Assert.assertEquals(expectedList, returnList);
	}

	@Test
	public void determineRangeFromBOObject() throws Exception {
		ZipRangeBO zipRangeBO = new ZipRangeBO();
		List<ZipCodeRangeBO> zipCodeRangeBOList = new ArrayList<>();
		zipRangeBO.setZipCodeRanges(zipCodeRangeBOList);

		ZipCodeRangeBO zipCodeRangeBO = new ZipCodeRangeBO();
		zipCodeRangeBO.setZipRange(Arrays.asList("29999  ", "40000"));

		ZipCodeRangeBO zipCodeRangeBO2 = new ZipCodeRangeBO();
		zipCodeRangeBO2.setZipRange(Arrays.asList("10000", "   50000"));

		ZipCodeRangeBO zipCodeRangeBO3 = new ZipCodeRangeBO();
		zipCodeRangeBO3.setZipRange(Arrays.asList("    20000", "30000"));
		zipCodeRangeBOList.add(zipCodeRangeBO);
		zipCodeRangeBOList.add(zipCodeRangeBO2);
		zipCodeRangeBOList.add(zipCodeRangeBO3);

		zipRangeBO.setZipCodeRanges(zipCodeRangeBOList);

		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(zipRangeBO));
		List<List<String>> returnList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(zipRangeBO);
		List<List<String>> expectedList = new ArrayList<>();
		expectedList.add(Arrays.asList("10000", "50000"));
		Assert.assertEquals(expectedList, returnList);
	}

}
