package com.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class StudentController
 */
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String USERNAME = "userName";
	private static final String PASSWORD = "password";
	private static final String URL = "testRailUrl";
	private static final String SCRIPT_PATH = "scriptPath";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	static Properties prop;
	static APIClient client;
	static String shellFile;
	RefreshPage ob;

	public void init() throws ServletException {
		try {
			System.out.println("inside init");
			prop = new Properties();
			
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
			 
			prop.load(inputStream);

			// setting client properties
			client = new APIClient(prop.getProperty(URL));
			client.setUser(prop.getProperty(USERNAME));
			client.setPassword(prop.getProperty(PASSWORD));
			shellFile = prop.getProperty(SCRIPT_PATH);
			ob = new RefreshPage();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public StudentController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("inside get");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		System.out.println(request.getParameter("run_id"));
		
		String runId = request.getParameter("run_id");
		String machineIP = request.getParameter("machineIP");
		String deviceIP = request.getParameter("deviceIP");
		String deviceType = request.getParameter("deviceType");
		String deviceID = request.getParameter("deviceID");
		String bundleID = request.getParameter("bundleID");
		String IPAHash = request.getParameter("createIPA");
		String APKVersion = request.getParameter("createAPK");
		String executedBy = request.getParameter("user_name");
		if (IPAHash == null) {
			IPAHash = "";	
		}
//		String IPAHash = "cb6277447f4f0e177b4f9feb3f560a78292f00ca";
		System.out.println("testRail data"+runId+":"+deviceType +":"+ deviceID +":"+ bundleID +":"+IPAHash + ":" +APKVersion + ":"+ executedBy);
		// updating TestRun Name
		Map data = new HashMap();
		String testTitle = "";

		try {

			JSONObject testJsonObject = (JSONObject) client.sendGet("get_run/"
					+ runId);
			testTitle = (String) testJsonObject.get("name");

			System.out.println(testTitle);
			data.put("name", 
					"Execution in Progress on Machine: " + machineIP + " - " + testTitle );
			JSONObject r = (JSONObject) client.sendPost("update_run/" + runId,
					data);
		} catch (APIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Creating Feature File

		Feature.hit(runId, prop, client);

		// Executing Test and Updating Result
		if (!runId.equalsIgnoreCase("true")) {
			try {
				ExecuteICEMapp testScript = new ExecuteICEMapp();
				testScript.execute(deviceIP, deviceType, deviceID, bundleID, shellFile, IPAHash, APKVersion);
				Thread.sleep(10000);
				TestNGRunner.jsonReader(prop, client, executedBy,runId);

				// deletion of feature file
				ob.setFlag("flag");
				Feature.delFile(runId, prop);
				PrintWriter out = response.getWriter();

				/*
				 * PrintWriter out = response.getWriter(); out.write("refresh");
				 */
				out.write("flag");
				ob.setFlag("");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (APIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} /*
			 * catch (InterruptedException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); }
			 */catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("inside else");
			PrintWriter out = response.getWriter();

			out.write(ob.getFlag());
		}

		// Rename the Test Run
		Map data1 = new HashMap();

		try {

			data1.put("name", testTitle);
			
			JSONObject r = (JSONObject) client.sendPost("update_run/" + runId,
					data1);
		} catch (APIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	

	}

}
