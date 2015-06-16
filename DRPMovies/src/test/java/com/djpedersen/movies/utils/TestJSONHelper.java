package com.djpedersen.movies.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class TestJSONHelper {

	private JSONHelper helper;

	@Before
	public void setup() {
		helper = new JSONHelper();
	}

	@Test
	public void testNullString() {
		final String json = null;
		final String fixCaseOnJsonFields = helper.fixCaseOnJsonFields(json);
		assertNull(fixCaseOnJsonFields);
	}

	@Test
	public void testEmptyString() {
		final String json = "";
		final String fixCaseOnJsonFields = helper.fixCaseOnJsonFields(json);
		assertEquals("", fixCaseOnJsonFields);
	}

	@Test
	public void testEmptyObject() {
		final String json = "{}";
		final String fixCaseOnJsonFields = helper.fixCaseOnJsonFields(json);
		assertEquals("{}", fixCaseOnJsonFields);
	}

	@Test
	public void testSingleWordStringField() {
		final String json = "{'field':'fieldvalue'}".replace("'", "\"");
		final String fixCaseOnJsonFields = helper.fixCaseOnJsonFields(json);
		assertEquals(json, fixCaseOnJsonFields);
	}

	@Test
	public void testCorrectDoubleWordStringField() {
		final String json = "{'fieldName':'fieldvalue'}".replace("'", "\"");
		final String fixCaseOnJsonFields = helper.fixCaseOnJsonFields(json);
		assertEquals(json, fixCaseOnJsonFields);
	}

	@Test
	public void testIncorrectDoubleWordStringField() {
		final String json = "{'fieldName':'fieldvalue'}".replace("'", "\"");
		final String fixCaseOnJsonFields = helper.fixCaseOnJsonFields(json);
		assertEquals(json, fixCaseOnJsonFields);
	}
}
