package com.serio.core.utils;

import java.io.IOException;
import java.util.Properties;

public class VersionUtils {
	private static String version = null;
	static{
		try {
			Properties props = new Properties();
			props.load(VersionUtils.class.getClassLoader().getResourceAsStream("application.properties"));
			version = props.getProperty("pom.version");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getVersion(){		
		return version;
	}
}
