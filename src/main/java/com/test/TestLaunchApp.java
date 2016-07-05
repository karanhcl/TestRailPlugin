package com.test;

import java.lang.annotation.Annotation;

import cucumber.api.*;
import cucumber.api.testng.AbstractTestNGCucumberTests;



@CucumberOptions(
		 features={"D:\\testingProjectFeature\\"},
        //tags="@Sanity",
        strict = true,
        glue="com.tavant",
        format = {
					"pretty",
					"json:D:\\karan\\TestLaunchApp.json",
					}
	                )
public class TestLaunchApp extends AbstractTestNGCucumberTests {
}
