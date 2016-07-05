package com.test;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//added second comments
public class Features {
	Scenarios scenario;

	public Scenarios getScenario() {
		return new Scenarios();
	}

	
	JSONArray getFeatureArray() throws IOException, ParseException {

		FileReader reader = new FileReader("D:\\TestFirst.json");
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
		return jsonArray;

	}

	Integer getFeatureCount(JSONArray jsonArray) {
		int count = 0;

		for (int i = 0; i < jsonArray.toArray().length; i++) {
			org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonArray
					.get(i);
			count++;
		}
		return count;
	}
	
	String[] getAllFeaturesName(JSONArray jsonArray){
		String[] name = new String[jsonArray.toArray().length];

		for (int i = 0; i < jsonArray.toArray().length; i++) {
			org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonArray
					.get(i);
			name[i]=(String) jsonObject.get("name");
			
		}
		return name;
				
	}
}
