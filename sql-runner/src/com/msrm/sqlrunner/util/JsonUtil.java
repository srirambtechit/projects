package com.msrm.sqlrunner.util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
	public static final String quote = "\"";
	public static final String openBrace = "{";
	public static final String closeBrace = "}";

	public enum Status {
		SUCCESS, ERROR;
	}

	public static class ArrayBuilder {

		private List<List<String>> list;

		public ArrayBuilder() {
			this.list = new ArrayList<>();
		}

		public static ArrayBuilder newArrayBuilder() {
			return new ArrayBuilder();
		}

		public ArrayBuilder elements(List<String> args) {
			List<String> array = new ArrayList<>();
			for (String str : args) {
				array.add(quote + str + quote);
			}
			list.add(array);
			return this;
		}

		public ArrayBuilder elements(String... args) {
			List<String> array = new ArrayList<>();
			for (String str : args) {
				array.add(quote + str + quote);
			}
			list.add(array);
			return this;
		}

		public String singleArray(List<String> args) {
			List<String> array = new ArrayList<>();
			for (String str : args) {
				array.add(quote + str + quote);
			}
			StringBuffer json = new StringBuffer(array.toString());
			json.insert(0, openBrace + quote + "aaData" + quote + ":");
			json.append(closeBrace);
			return json.toString();
		}

		public String build() {
			if (list.isEmpty())
				return openBrace + closeBrace;
			StringBuffer json = new StringBuffer(list.toString());
			json.insert(0, openBrace + quote + "aaData" + quote + ":");
			json.append(closeBrace);
			return json.toString();
		}

	}

	public static class Builder {

		private Map<String, String> map;

		public Builder() {
			this.map = new LinkedHashMap<>();
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public Builder property(String name, String value) {
			map.put(quote + name + quote, quote + value + quote);
			return this;
		}

		public String build() {
			if (map.isEmpty())
				return openBrace + closeBrace;
			String json = map.toString().replaceAll("=", ":");
			map.clear();
			return json;
		}

		public Builder encodeJson(String name, String json) {
			String encodedJsonString = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
			if (encodedJsonString.endsWith("==")) {
				encodedJsonString = encodedJsonString.substring(0, encodedJsonString.length() - 2);
			} else if (encodedJsonString.endsWith("=")) {
				encodedJsonString = encodedJsonString.substring(0, encodedJsonString.length() - 1);
			}
			property(name, encodedJsonString);
			return this;
		}

		public String buildAsDatatable() {
			if (map.isEmpty())
				return openBrace + closeBrace;
			StringBuffer json = new StringBuffer(map.toString().replaceAll("=", ":"));
			json.insert(0, openBrace + quote + "aaData" + quote + ":");
			json.append(closeBrace);
			map.clear();
			return json.toString();
		}

	}

	public static void main(String[] args) {

		String json = "{ \"status\" : \"success\"}";
		String plainString = json.replaceAll("\"", "\\\\\"");
		System.out.println("Embed json : " + plainString);

		String columns = ArrayBuilder.newArrayBuilder().singleArray(Arrays.asList("code", "name", "price"));
		System.out.println(columns);

		//@formatter:off
		String json1 = JsonUtil.ArrayBuilder.newArrayBuilder()
				.elements("1", "sriram", "1000")
				.elements("2", "prabhu", "1001")
				.elements("3","kannan", "1010")
				.build();
		//@formatter:on
		System.out.println(json1);
	}

	public void testArray() {
		// first one
		//@formatter:off
		String json = JsonUtil.ArrayBuilder.newArrayBuilder()
				.build();
		//@formatter:on
		System.out.println(json);

		// second one
	}

	public void testDatatable() {
		//@formatter:off
		String json = JsonUtil.Builder.newBuilder()
				.property("code", "200")
				.property("httpStatus", "OK")
				.buildAsDatatable();
		//@formatter:on
		System.out.println(json);
	}

	public void testOneObject() {
		//@formatter:off
				String json = JsonUtil.Builder.newBuilder()
						.property("payload", "server response")
						.property("code", "200")
						.property("httpStatus", "OK")
						.property("message", "server is down")
						.property("code", "500")
						.property("version", "1.0")
						.build();
				//@formatter:on
		System.out.println(json);
	}

}
