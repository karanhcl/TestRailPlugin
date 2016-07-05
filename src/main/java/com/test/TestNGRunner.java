package com.test;

import gherkin.deps.com.google.gson.JsonArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.support.StaticApplicationContext;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import cucumber.api.cli.Main;

public class TestNGRunner {

	private static final Logger logger;
	private static String xmlFile;
	private static final String ELEMENTS = "elements";
	private static final String JSONPATH = "jsonPath";
	private static final String NAME = "name";
	private static final String STEPS = "steps";
	private static final String RESULT = "result";
	private static final String STATUS = "status";
	private static final String PASSED = "passed";
	private static final String  STATUS_ID="status_id";
	private static final String  CASE_ID="case_id";

	static {
		logger = Logger.getLogger(TestNGRunner.class);

	}

	public TestNGRunner(String testNgXmlFileName) {
		this.xmlFile = testNgXmlFileName;
	}

	/**
	 * Run TestClasses configured in testng xml file
	 * 
	 * @param testNgXmlFileName
	 */
	static public void executeTestNGTests() {
		TestNG testng = new TestNG();

		try {
			testng.setXmlSuites((List<XmlSuite>) (new Parser(xmlFile).parse()));

		} catch (Exception e) {
			/*
			 * logger.error("An error occurred while executing tests" +
			 * e.getMessage());
			 */
		}
		testng.run();

	}

	static void jsonReader(Properties prop, APIClient client, String executedBy,String runId)
			throws IOException, ParseException, APIException {
		Map data = new HashMap();
		Map resultMap = new HashMap();
		ArrayList resultsList = new ArrayList();
		JSONArray finalArr = new JSONArray();
		// data.put("comment", "This test worked fine!");
		
		FileReader reader = new FileReader(prop.getProperty(JSONPATH));
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonObject = (JSONArray) jsonParser.parse(reader);
		JSONObject jsonObjectOfTestID = null;
		
		String status = "";
		// JSONArray lang= (JSONArray) jsonObject.get("name");
		for (int i = 0; i < jsonObject.toArray().length; i++) {
			JSONObject jsonObjectOfTestCase = (JSONObject) jsonObject.get(i);
			JSONArray jsonObjectOfTestCase1 = (JSONArray) jsonObjectOfTestCase
					.get(ELEMENTS);

			for (int j = 0; j < jsonObjectOfTestCase1.toArray().length; j++) {
				jsonObjectOfTestID = (JSONObject) jsonObjectOfTestCase1.get(j);

				String resultId = jsonObjectOfTestID
						.get(NAME)
						.toString()
						.substring(
								0,
								jsonObjectOfTestID.get(NAME).toString()
										.indexOf("-*-")).trim();
				JSONArray steps = (JSONArray) jsonObjectOfTestID.get(STEPS);
				if (steps != null) {
					for (int j2 = 0; j2 < steps.toArray().length; j2++) {
						JSONObject stepsObject = (JSONObject) steps.get(j2);
						// System.out.println(stepsObject);
						JSONObject resultObjectArray = (JSONObject) stepsObject
								.get(RESULT);
						if (resultObjectArray.get(STATUS) != null) {
							status = resultObjectArray.get(STATUS).toString();
							if (!status.equalsIgnoreCase(PASSED)) {
								status = "5";
								break;
							} else {
								status = "1";
							}
						}

					}

					data.put(STATUS_ID, new Integer(String.valueOf(status)));
					data.put(CASE_ID, resultId);
					data.put("custom_executed_by", "executedby");
//					finalArr.add(data);
					/*JSONObject r = (JSONObject) client.sendPost("add_result/"
							+ resultId, data);*/
				} else {
					data.put(STATUS_ID, new Integer(5));
					data.put(CASE_ID, resultId);
					data.put("custom_executed_by", "executedby");
					/*JSONObject r = (JSONObject) client.sendPost("add_result/"
					+ resultId, data);*/

				}
				
				resultsList.add(data);
			}

		}
		
		resultMap.put("results", resultsList);
		JSONObject r = (JSONObject) client.sendPost("add_results/"
				+ runId, resultMap);
//		
//		JsonArray jsonArray = JSON.
//		JSONObject r = (JSONObject) client.sendPost("add_results/"
//				, data);

	}
	
	
	static void jsonReader2() throws IOException, ParseException
	{

		
		Map resultMap = new HashMap();
		ArrayList resultsList = new ArrayList();
		JSONArray finalArr = new JSONArray();
		// data.put("comment", "This test worked fine!");
		
		FileReader reader = new FileReader("D:\\cucumber.json");
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonObject = (JSONArray) jsonParser.parse(reader);
		JSONObject jsonObjectOfTestID = null;
		
		String status = "";
		// JSONArray lang= (JSONArray) jsonObject.get("name");
		for (int i = 0; i < jsonObject.toArray().length; i++) {
			
			JSONObject jsonObjectOfTestCase = (JSONObject) jsonObject.get(i);
			JSONArray jsonObjectOfTestCase1 = (JSONArray) jsonObjectOfTestCase
					.get(ELEMENTS);

			for (int j = 0; j < jsonObjectOfTestCase1.toArray().length; j++) {
				Map data = new HashMap();
				jsonObjectOfTestID = (JSONObject) jsonObjectOfTestCase1.get(j);

				String resultId = jsonObjectOfTestID
						.get(NAME)
						.toString()
						.substring(
								0,
								jsonObjectOfTestID.get(NAME).toString()
										.indexOf("-*-")).trim();
				JSONArray steps = (JSONArray) jsonObjectOfTestID.get(STEPS);
				if (steps != null) {
					for (int j2 = 0; j2 < steps.toArray().length; j2++) {
						JSONObject stepsObject = (JSONObject) steps.get(j2);
						// System.out.println(stepsObject);
						JSONObject resultObjectArray = (JSONObject) stepsObject
								.get(RESULT);
						if (resultObjectArray.get(STATUS) != null) {
							status = resultObjectArray.get(STATUS).toString();
							if (!status.equalsIgnoreCase(PASSED)) {
								status = "5";
								break;
							} else {
								status = "1";
							}
						}

					}

					data.put(STATUS_ID, new Integer(String.valueOf(status)));
					data.put(CASE_ID, resultId);
					data.put("custom_executed_by", "executedby");
//					finalArr.add(data);
					/*JSONObject r = (JSONObject) client.sendPost("add_result/"
							+ resultId, data);*/
				} else {
					data.put(STATUS_ID, new Integer(5));
					data.put(CASE_ID, resultId);
					data.put("custom_executed_by", "executedby");
					/*JSONObject r = (JSONObject) client.sendPost("add_result/"
					+ resultId, data);*/

				}
				
				resultsList.add(data);
			}

		}
		
		resultMap.put("results", resultsList);
		JSONObject ob =new JSONObject();
		ob.putAll(resultMap);
		/*JSONObject r = (JSONObject) client.sendPost("add_results/"
				+ runId, resultMap);*/
//		
//		JsonArray jsonArray = JSON.
//		JSONObject r = (JSONObject) client.sendPost("add_results/"
//				, data);

	
	}
	
public static void main(String[] args) throws IOException, ParseException {
	jsonReader2();
}
	
	
	

}