package org.angiedev.schoolfinder.loader;

import java.io.IOException;
import java.util.List;

import org.angiedev.schoolfinder.dao.SchoolDAO;
import org.angiedev.schoolfinder.loader.json.GoogleGeocodingLookupResult;
import org.angiedev.schoolfinder.model.GeoLocation;
import org.angiedev.schoolfinder.model.School;
import org.angiedev.schoolfinder.util.Props;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * SchoolGeoLocationLoader lookups and adds geo-location (longitude and latitude)
 * data for schools in the SchoolFinder database that do not already have geo-location
 * data available.  The physical addresses are retrieved from the SchoolFinder 
 * database for schools that do not already have geo-location data.  Only schools that
 * are located in one of the states identified in the passed in stateCodeList parameter
 * will be checked for missing geo-location data.  The geo-location data is retrieved 
 * for these schools using Google's geocoding API and added into the SchoolFinder database. 
 * <p>
 * The loader is kicked off for a subset of states at a time due to the daily  
 * limitation of calling the Google GeoCoding API. (Only allowed 2500 API calls  
 * per day)
 * <p>
 * Geo-location data is needed to support the retrieval of schools based on 
 * a client's provided geo-location and search radius.
 * <p>
 * Usage: SchoolGeoLocationLoader &lt;stateCodeList&gt;
 * <ul>
 * <li>stateCodeList: a comma separated list of state codes (i.e. "CA,OR") 
 *  </ul>
 * @author Angela Gordon
 */
@Component
public class SchoolGeoLocationLoader {

	 @Autowired
	 private SchoolDAO schoolDAO;
	 
	 private static final String LOOKUP_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
	 private static final String ADDRESS_KEY_PARAM = "address=";
	 private static final String API_KEY_PARAM = "key=" + Props.getInstance().getGoogleAPIKey();
	 
	 /**
	  * Kicks off the geo location data loader for the schools in the states 
	  * identified by the passed in argument
	  * @param args First argument identifies the state codes of the schools that 
	  *  			geo-location data should be loaded for.
	  */
	 public static void main(String[] args) {
		try (ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("/WEB-INF/SchoolFinderConfig.xml")) {
			SchoolGeoLocationLoader loader = context.getBean(SchoolGeoLocationLoader.class);
			loader.loadGeoLocationData(args[0]);
		}
	 }
	 
	 /** 
	  * Retrieves the schools that need geo location data, looks up the geo-location data and 
	  * stores the geo-location data into the database.
	  * @param stateCodeList a comma separated list of state codes of the schools 
	  *  		that geo-location data should be loaded for.
	  */
	 public void loadGeoLocationData(String stateCodeList) {
		 for (String stateCode: stateCodeList.split(",")) {
			// find addresses that need lat/long data 
			 List<School> schools = schoolDAO.getSchoolsByStateWithNoGeoData(stateCode);
			 
			 // for each address call Geo Location Service to get lat/long data for the school
			 // based on the school's address, then save the lat/long in our db
		
			 for (School school : schools) {
				 try {
					 GeoLocation geoLocation = getGeoLocationForAddress(
						school.getStreetAddress().replace("'", " "), school.getCity(), school.getState());
					 school.setLatitude(geoLocation.getLatitude());
					 school.setLongitude(geoLocation.getLongitude());
					 schoolDAO.updateSchool(school);
				 } catch (IOException e) {
					 System.out.println("Unable to complete load of geo location data.  Exception thrown: " +e);
					 break;
				 }
			 }
		 }
		 
	 }
	 
	 /**
	  * Calls the google geo code service to look up the geo location for the passed in address
	  * @param address 		street address
	  * @param city 		city	
	  * @param stateCode 	two letter state code
	  * @return 			geo location of address
	  */
	 private GeoLocation getGeoLocationForAddress(String address, String city, String stateCode)
	 	throws IOException {
		
		String query = LOOKUP_URL + ADDRESS_KEY_PARAM + address + "," + city + "," + 
				stateCode + "&" + API_KEY_PARAM;
		
		RestTemplate restTemplate = new RestTemplate();
		GoogleGeocodingLookupResult result = 
				restTemplate.getForObject(query, GoogleGeocodingLookupResult.class);
		
		switch (result.getStatus()) {
			case "OK":
				return result.getGeoLocation();
			case "ZERO_RESULTS":
				return null;
			default:
				throw new IOException("Unable to get GeoLocation for address: " + address 
						+ "," + city + "," + stateCode + ".  GeoCode API returned status: " +
						result.getStatus());
		}
	}
	
}
