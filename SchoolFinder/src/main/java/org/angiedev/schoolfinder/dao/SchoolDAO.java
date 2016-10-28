package org.angiedev.schoolfinder.dao;

import java.util.List;

import org.angiedev.schoolfinder.model.School;


/**
 * SchoolDAO is an interface which defines the data access operations 
 * to find, create and update schools.
 * 
 * @author Angela Gordon
 */
public interface SchoolDAO {
	
	/**
	 * Inserts a new school into the database. 
	 * @param school 	the school being added to the database.
	 */
	public void insertSchool(School school);
	
	/**
	 * Retrieves a school based on the passed in id.
	 * @param schoolId 	the id uniquely identifying the school in the database (PK).
	 * @return 			the school if found, otherwise null.
	 */
	public School getSchool(long schoolId);
	
	/**
	 * Retrieves a school based on the passed in nces id.
	 * @param ncesId 	the nces id for the school (id assigned by the National Center
	 *  				for Education Statistics).
	 * @return 			the school if found, otherwise null.
	 */
	public School getSchoolByNcesId(String ncesId);
	
	/**
	 * Updates a school with the passed in school's object data.
	 * @param school	school object containing new data that needs to be updated in database.
	 */
	public void updateSchool(School school);
	
	/**
	 * Deletes the passed in school from the database.
	 * @param school 	school being deleted.
	 */
	public void deleteSchool(School school);
	
	/**
	 * Retrieves the list of schools belonging to the passed in district.
	 * @param districtId	unique id of the district. 
	 * @return				list of schools belonging to the district.
	 */
	public List<School> getSchoolsByDistrictId(long districtId);
	
	/**
	 * Retrieves the list of schools within the passed in state that do not have 
	 * geo location data (latitude and longitude fields).
	 * @param stateCode		2 letter state code.
	 * @return 				list of schools belonging to passed in state that do 
	 *  					not have geolocation data.
	 */
	public List<School> getSchoolsByStateWithNoGeoData(String stateCode);
	
	/**
	 * Retrieves the list of schools that are located within a specified radius of a
	 * the passed in latitude and longitude geo location.
	 * @param latitude		latitude of the position to search near. 
	 * @param longitude		longitude of the position to search near.
	 * @param searchRadius	the search radius in miles to conduct the search.
	 * @param maxNumResults	maximum number of schools to return
	 * @return				the list of schools within the specified radius of the passed in 
	 * 						geo location.
	 */
	public List<School> getSchoolsNearGeoLocation(double latitude, double longitude, int searchRadius,
			int maxNumResults);
	
	/**
	 * Retrieves the list of schools that are located within a specified radius of a
	 * the passed in latitude and longitude geo location and whose name contains the passed
	 * in search string.
	 * @param latitude		latitude of the position to search near. 
	 * @param longitude		longitude of the position to search near.
	 * @param searchRadius	the search radius in miles to conduct the search.
	 * @param searchString	search string to find at start of the school's name
	 * @param numResults	maximum number of schools to return
	 * @return				the list of schools within the specified radius of the passed in 
	 * 						geo location.
	 */
	public List<School> getSchoolsNearGeoLocation(double latitude, double longitude, int searchRadius, 
			String searchString, int maxNumResults);
}
