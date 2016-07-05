package com.test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Scenarios {

	Steps steps;

	public Steps getStep() {
		return new Steps();
	}

	Integer getScenrioCount(String name, JSONArray featureArray) {

		JSONArray scenarioArray = null;
		for (int i = 0; i < featureArray.toArray().length; i++) {

			org.json.simple.JSONObject featureObject = (org.json.simple.JSONObject) featureArray
					.get(i);
			if (name.equals(featureObject.get("name"))) {
				scenarioArray = (JSONArray) featureObject.get("elements");

			}

		}

		return scenarioArray.size();

	}

	JSONArray getScenarioArray(String name, JSONArray featureArray) {
		JSONArray scenarioArray = null;
		for (int i = 0; i < featureArray.toArray().length; i++) {

			org.json.simple.JSONObject featureObject = (org.json.simple.JSONObject) featureArray
					.get(i);
			if (name.equals(featureObject.get("name"))) {
				scenarioArray = (JSONArray) featureObject.get("elements");

			}

		}

		return scenarioArray;

	}
	
	String[] getAllScenariosId(JSONArray jsonArray){
		String[] id = new String[jsonArray.toArray().length];

		for (int i = 0; i < jsonArray.toArray().length; i++) {
			org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonArray
					.get(i);
			id[i]=(String) jsonObject.get("id");
			
		}
		return id;
				
	}
}
