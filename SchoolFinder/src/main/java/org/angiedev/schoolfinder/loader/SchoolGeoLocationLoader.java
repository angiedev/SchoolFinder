package org.angiedev.schoolfinder.loader;

import java.io.IOException;
import java.util.List;

import org.angiedev.schoolfinder.dao.SchoolDAO;
import org.angiedev.schoolfinder.service.GeoLocationService;
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
	 
	 private GeoLocationService service;
	 	 
	 /**
	  * Kicks off the geo location data loader for the schools in the states 
	  * identified by the passed in argument
	  * @param args First argument identifies the state codes of the schools that 
	  *  			geo-location data should be loaded for.
	  */
	 public static void main(String[] args) {
		try (ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("/WEB-INF/SchoolFinderConfig.xml")) {
			SchoolGeoLocationLoader loader = (SchoolGeoLocationLoader)context.getBean("loader");
			loader.loadGeoLocationData(args[0]);
		}
	 }
	 
	 public void setService(GeoLocationService service) {
		 this.service = service;
	 }
	 /** 
	  * Retrieves the schools that need geo location data, looks up the geo-location data and 
	  * stores the geo-location data into the database.
	  * @param stateCodeList a comma separated list of state codes of the schools 
	  *  		that geo-location data should be loaded for.
	  */
	 public void loadGeoLocationData(String stateCodeList) {
		 
		 int numFailures = 0;
		 int numSuccess = 0;
		 for (String stateCode: stateCodeList.split(",")) {
			 System.out.println("Getting geo codes for schools in: " + stateCode);
			// find addresses that need lat/long data 
			 List<School> schools = schoolDAO.getSchoolsByStateWithNoGeoData(stateCode);
			 
			 // for each address call Geo Location Service to get lat/long data for the school
			 // based on the school's address, then save the lat/long in our db
		
			 for (School school : schools) {
				 try {
					 GeoLocation geoLocation = service.getGeoLocationForAddress(
						school.getStreetAddress().replace("'", " "), school.getCity(), school.getState(),school.getZip());
					 school.setLatitude(geoLocation.getLatitude());
					 school.setLongitude(geoLocation.getLongitude());
					 schoolDAO.updateSchool(school);
					 numSuccess++;
				 } catch (Exception e) {
					 System.out.println("Unable to complete load of geo location data for school " + school + "  Exception thrown: " +e);
					 numFailures++;
					 continue;
				 }
			 }
		 }
		 System.out.println("Retrieved Geo location for " + numSuccess + " schools");
		 System.out.println("Unable to retrieve Geo location for " + numFailures + " schools");
		 
	 }
	 
}
	 
	

