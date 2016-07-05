package com.test;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
// adding comment
public class Feature {
	private final static String featureExtension = ".feature";
	private final static String featureFilePath = "featureFilePath";

	static void hit(String runId, Properties prop, APIClient client)
			throws IOException {
		JSONArray testJsonArray = null;
		
		File file = new File(prop.getProperty(featureFilePath) + runId
				+ featureExtension);
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		System.out.println("fw"+fw);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("Feature: " + runId.toString());
		bw.write("\r\n");
		bw.write("\r\n");

		try {
			testJsonArray = (JSONArray) client.sendGet("get_tests/" + runId);
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int j = 0; j < testJsonArray.toArray().length; j++) {
			JSONObject jsonObjectOfTest = (JSONObject) testJsonArray.get(j);
			String testTitle = jsonObjectOfTest.get("title").toString();
			String testCaseSteps = "";
			String testCaseId = jsonObjectOfTest.get("id").toString();
			if (jsonObjectOfTest.get("custom_steps") != null)
				testCaseSteps = jsonObjectOfTest.get("custom_steps").toString();	
//			bw.write(testCaseId + "-*-" + testTitle);
//			bw.write("##" + testCaseId + "-*-" + testTitle);
			bw.write("\r\n");
			bw.write("\r\n");
			int index=testCaseSteps.indexOf(":");
			bw.write(testCaseSteps.substring(0, testCaseSteps.indexOf(":")+1)+" "+testCaseId + "-*-"+testCaseSteps.substring(index+1));
			bw.write("\r\n");
			bw.write("\r\n");
		}
		bw.flush();
		bw.close();
	}

	static void delFile(String runId, Properties prop) {

		boolean success = (new File(prop.getProperty(featureFilePath) + runId
				+ featureExtension)).delete();
		if (success) {
			System.out.println("The file has been successfully deleted");
		}

	}
}