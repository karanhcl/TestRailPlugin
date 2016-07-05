package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

public class JenkinsTestRail {
	private static final String USERNAME = "userName";
	private static final String PASSWORD = "password";
	private static final String URL = "testRailUrl";
	//private static final String XMLFILEPATH = "testNgXmlLocation";
	private static final String  SCRIPT_PATH="scriptPath";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	static Properties prop;
	static APIClient client;
	 void propertiesSet()
	{
		try {
			System.out.println("inside init");
			String propertyValue = System.getenv("CONFIG_PROPERTY");
			prop = new Properties();

			// reading property file from classpath
             FileInputStream inputStream=new FileInputStream(propertyValue);
			/*InputStream propertiesInputStream = getClass().getClassLoader()
					.getResourceAsStream("config.properties");*/
			prop.load(inputStream);

			// setting client properties
			client = new APIClient(prop.getProperty(URL));
			client.setUser(prop.getProperty(USERNAME));
			client.setPassword(prop.getProperty(PASSWORD));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	public static void main(String[] args) throws IOException {

		new JenkinsTestRail().propertiesSet();
		System.out.println(args[0]);
		// TODO Auto-generated method stub
		for (int i = 0; i < args.length; i++) {
			
		
		System.out.println("inside dopost");
		// Feature ob=new Feature();
		String runId = args[i];
		// Feature Creation
		//Feature.hit(runId);
		Feature.hit(runId, prop, client);
		// Feature Created

		// Trigger Execution

		// testNgrunner.executeTestNGTests();

		try {
			ExecuteICEMapp testScript = new ExecuteICEMapp();
			testScript.runScript(prop.getProperty(SCRIPT_PATH));

			// reading json response

			TestNGRunner.jsonReader(prop, client, "");
			System.out.println("after json");

			// deletion of feature file

			Feature.delFile(runId, prop);
	
			
			// Report Updated

			// Feature Deletion
		//	Feature.delFile(runId);
			System.out.println("after deletion");

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		}
	}

}
