package org.angiedev.schoolfinder.web.controller;

import java.util.List;

import org.angiedev.schoolfinder.model.School;
import org.angiedev.schoolfinder.service.SchoolFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * SchoolFinderRestController is a REST based webservice that enables 
 * clients to retrieve schools within a specified radius of a given 
 * search location (identified by latitude and longitude).
 * 
 * @author Angela Gordon
 */
@RestController
@RequestMapping("/schools")
public class SchoolFinderRestController {

	@Autowired 
	private SchoolFinderService finderService;
	
	/**
	 * Returns the list of schools within the passed in searchRadius of the given 
	 * location identified by latitude and longitude and optionally matching a passed
	 * in search string within the school's name 
	 * @param searchString	search string to find within the school's name
	 * @param latitude		latitude of location we are searching from.
	 * @param longitude		longitude of location we are searching from.
	 * @param searchRadius	search radius in miles in which we are searching
	 *                      (must be a string with value "BY_DISTANCE" or "BY_NAME")
	 * @param maxNumResults	maximum number of schools to return
	 * @return				list of schools within search radius
	 */
	
	@RequestMapping(value="search", method=RequestMethod.GET)
	public List<School> searchForSchools(
			@RequestParam(value="searchString", required=false) String searchString,
			@RequestParam("lat") double latitude,
			@RequestParam("long") double longitude, 
			@RequestParam("searchRadius") int searchRadius,
			@RequestParam("maxNumResults") int maxNumResults) {
		
		if (searchString == null) {
			return finderService.getSchools(latitude, longitude, searchRadius, maxNumResults);
		} else {
			return finderService.getSchools(latitude, longitude, searchRadius, searchString, maxNumResults);
		}
	}
	
	/**
	 * Returns a JSON representation of a school identified by the passed in ncesId
	 * @param ncesId	NCES Id identifying school being retrieved
	 * @return 			school 
	 */
	@RequestMapping(value="/{ncesId}", method=RequestMethod.GET)
	public School getSchoolByNcesId(@PathVariable String ncesId) {
		return finderService.getSchoolByNcesId(ncesId);
	}
	
	public SchoolFinderService getFinderService() {
		return finderService;
	}

	public void setFinderService(SchoolFinderService finderService) {
		this.finderService = finderService;
	}
}

