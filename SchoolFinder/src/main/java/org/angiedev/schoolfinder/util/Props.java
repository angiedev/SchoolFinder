package org.angiedev.schoolfinder.util;

import java.io.IOException;
import java.util.Properties;

import org.angiedev.schoolfinder.util.Props;

/**
 * Props is a utility class used to retrieve application properties.
 * 
 * @author Angela Gordon
 *
 */
public class Props {

	private static Props instance;
	private Properties properties;
	
	public static Props getInstance() {
		return (instance == null) ? instance = new Props() : instance;
	}
	
	private Props() {
		if (properties == null) {
			try {
				properties = new Properties();
				properties.load(this.getClass().getResourceAsStream("/SchoolFinder.properties"));
			} catch (IOException e) {
				System.out.println("Unable to load Properties:" + e);
				properties = null;
			}
		}
	}
	
	public String getGoogleAPIKey() {
		return (properties == null) ? null : properties.getProperty("GOOGLE_API_KEY");
	}
	
}
