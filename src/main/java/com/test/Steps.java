package com.test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONObject;

public class Steps {

	Integer getStepsCount(String name, JSONArray scenarioArray) {
		JSONArray stepsArray = null;
		for (int i = 0; i < scenarioArray.toArray().length; i++) {

			org.json.simple.JSONObject scenarioObject = (org.json.simple.JSONObject) scenarioArray
					.get(i);
			if (name.equals(scenarioObject.get("id"))) {
				stepsArray = (JSONArray) scenarioObject.get("steps");
			}
		}

		return stepsArray.size();

	}

	JSONArray getStepsArray(String name, JSONArray scenarioArray) {
		JSONArray stepsArray = null;
		for (int i = 0; i < scenarioArray.toArray().length; i++) {

			org.json.simple.JSONObject scenarioObject = (org.json.simple.JSONObject) scenarioArray
					.get(i);
			if (name.equals(scenarioObject.get("id"))) {
				stepsArray = (JSONArray) scenarioObject.get("steps");

			}
		}

		return stepsArray;
	}

	Integer getTagsCount(String name, JSONArray scenarioArray) {
		JSONArray tagsArray = null;
		for (int i = 0; i < scenarioArray.toArray().length; i++) {

			org.json.simple.JSONObject scenarioObject = (org.json.simple.JSONObject) scenarioArray
					.get(i);
			if (name.equals(scenarioObject.get("id"))) {
				tagsArray = (JSONArray) scenarioObject.get("steps");
			}
		}

		return tagsArray.size();

	}

	JSONArray getTagsArray(String name, JSONArray scenarioArray) {
		JSONArray tagsArray = null;
		for (int i = 0; i < scenarioArray.toArray().length; i++) {

			org.json.simple.JSONObject scenarioObject = (org.json.simple.JSONObject) scenarioArray
					.get(i);
			if (name.equals(scenarioObject.get("id"))) {
				tagsArray = (JSONArray) scenarioObject.get("tags");

			}
		}

		return tagsArray;
	}

	JSONObject getMatchAndResultObject(String name, JSONArray scenarioArray) {
		JSONArray afterArray = null;
		for (int i = 0; i < scenarioArray.toArray().length; i++) {

			org.json.simple.JSONObject scenarioObject = (org.json.simple.JSONObject) scenarioArray
					.get(i);
			if (name.equals(scenarioObject.get("id"))) {
				afterArray = (JSONArray) scenarioObject.get("after");

			}
		}
		JSONObject matchAndResult = new JSONObject();
		if (afterArray != null) {

			for (int i = 0; i < afterArray.toArray().length; i++) {
				org.json.simple.JSONObject afterObject = (org.json.simple.JSONObject) afterArray
						.get(i);
				matchAndResult.put("match", afterObject.get("match"));
				matchAndResult.put("result", afterObject.get("result"));
			}

		}
		return matchAndResult;
	}

	JSONObject getResultObject(JSONObject stepsObject) {
		JSONObject resultObject = (JSONObject) stepsObject.get("result");
		if (resultObject.get("status").equals("failed")) {
			JSONObject resultObjectwithEmbedding = new JSONObject();
			resultObjectwithEmbedding.put("error_message",
					resultObject.get("error_message"));
			resultObjectwithEmbedding.put("result", resultObject);
			resultObjectwithEmbedding.put("embeddings",
					stepsObject.get("embeddings"));

			return resultObjectwithEmbedding;
		}
		return resultObject;

	}

	String getName(JSONObject stepsObject) {
		String name = (String) stepsObject.get("name");
		return name;

	}

	JSONObject getMatchObject(JSONObject stepsObject) {
		JSONObject matchObject = (JSONObject) stepsObject.get("match");
		return matchObject;

	}

}
