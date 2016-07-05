package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

public class ExecuteICEMapp {
	static Properties prop;
	
	public void execute(String deviceIP, String deviceType, String deviceID,
			String bundleID, String shellFile, String IPAHash, String APKVersion) throws IOException, InterruptedException {
		Process process = null;
		File f = new File(shellFile);
				
		System.out.println(f.isFile());

		System.out.println(f.getAbsolutePath());
		System.out.println("deviceID" + deviceID);
		String[] commands = new String[] { "/bin/bash", f.getAbsolutePath(),
				deviceIP,deviceType,deviceID,bundleID,IPAHash,APKVersion };

		ProcessBuilder pb = new ProcessBuilder(commands);
		process = pb.start();

		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;

		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}

		// Wait to get exit value
		try {
			int exitValue = process.waitFor();
			System.out.println("\n\nExit Value is " + exitValue);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		process.waitFor();

	}

	int iExitValue;
	String sCommandString;

	public void runScript(String command) {
		sCommandString = command;
		CommandLine oCmdLine = CommandLine.parse(sCommandString);
		DefaultExecutor oDefaultExecutor = new DefaultExecutor();
		oDefaultExecutor.setExitValue(0);
		try {
			iExitValue = oDefaultExecutor.execute(oCmdLine);
		} catch (ExecuteException e) {
			System.err.println("Execution failed.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("permission denied.");
			e.printStackTrace();
		}
	}

	// public static void main(String[] args) throws IOException,
	// InterruptedException {
	//
	// // ExecuteICEMapp exObj = new ExecuteICEMapp();
	// //exObj.execute();
	//
	// }

}
