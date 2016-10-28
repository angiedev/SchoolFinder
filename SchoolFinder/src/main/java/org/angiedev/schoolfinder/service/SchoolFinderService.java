package org.angiedev.schoolfinder.service;

import java.util.List;

import org.angiedev.schoolfinder.model.School;

/**
 * SchoolFinderService is a interface for a service which enables a user to search 
 * for a particular set of schools.
 * 
 * @author Angela Gordon
 */
public interface SchoolFinderService {
	
	/**
	 * Retrieves the list of schools that are near the specified location (in
	 * latitude and longitude) within the passed in search radius.
	 * @param latitude		latitude of location to search from 
	 * @param longitude		longitude of location to search from
	 * @param searchRadius	search radius in miles to search within
	 * @param maxNumResults	maximum number of schools to return
	 * @return				list of schools located within search radius of search location
	 * 
	 */
	public List<School> getSchools(double latitude, double longitude, int searchRadius,
			int maxNumResults);
	
	/**
	 * Retrieves the list of schools that are near the specified location (in
	 * latitude and longitude) within the passed in search radius and whose 
	 * name contain the passed in search string.  
	 * @param latitude		latitude of location to search from 
	 * @param longitude		longitude of location to search from
	 * @param searchRadius	search radius in miles to search within
	 * @param searchString	search string to find at start of the school's name
	 * @param maxNumResults	maximum number of schools to return
	 * @return				list of schools located within search radius of search location
	 * 
	 */
	public List<School> getSchools(double latitude, double longitude, int searchRadius,
			String searchString, int maxNumResults);
	
	/**
	 * Retrieves the school identified by the passed in NCES id
	 * @param ncesId	unique NCES id identifying the school 
	 *                  (Id assigned by the National Center for Education Statistics)
	 * @return			school with the passed in NCES Id
	 * 
	 */
	public School getSchoolByNcesId(String ncesId);
}