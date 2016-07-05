package com.test;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

public class JsonParser {

	public static void main(String[] args) throws IOException, ParseException {

		// System.out.println(jsonArray);
		Features feature= getFeature();
		Scenarios scenario = feature.getScenario();
		Steps steps = scenario.getStep();
		JSONArray featureArray = feature.getFeatureArray();
		String featuresName[] = feature.getAllFeaturesName((JSONArray)featureArray);
		JSONArray scenarioArray =scenario.getScenarioArray(featuresName[0],featureArray);
		String scenariosId[] = scenario.getAllScenariosId((JSONArray)scenarioArray);
		JSONArray stepsArray = steps.getStepsArray(scenariosId[0],scenarioArray);
		JSONObject resultObject = steps.getResultObject((JSONObject)stepsArray.get(9));
		System.out.println(resultObject);
		String name = steps.getName((JSONObject)stepsArray.get(0));
		System.out.println(name);
	}
	

	static Features getFeature(){
		return new Features();
	}

	
}
