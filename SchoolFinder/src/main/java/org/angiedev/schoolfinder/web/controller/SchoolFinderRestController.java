package org.angiedev.schoolfinder.web.controller;

import java.util.List;

import org.angiedev.schoolfinder.model.School;
import org.angiedev.schoolfinder.service.SchoolFinderService;
import org.springframework.beans.factory.annotation.Autowired;
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
	 * Returns a JSON representation of the list of schools within the 
	 * passed in searchRadius of the given location identified by latitude
	 * and longitude and optionally matching a passed in search string within
	 * the school's name 
	 * @param searchString	search string to find within the school's name
	 * @param latitude		latitude of location we are searching from.
	 * @param longitude		longitude of location we are searching from.
	 * @param searchRadius	search radius in miles in which we are searching
	 *                      (must be a string with value "BY_DISTANCE" or "BY_NAME")
	 * @return				list of schools within search radius
	 */
	@RequestMapping(method=RequestMethod.GET, produces={"application/json"})
	public List<School> getSchools(
			@RequestParam(value="searchString", required=false) String searchString,
			@RequestParam("lat") double latitude,
			@RequestParam("long") double longitude, 
			@RequestParam("searchRadius") int searchRadius) {
		
		if (searchString == null) {
			return finderService.getSchools(latitude, longitude, searchRadius);
		} else {
			return finderService.getSchools(latitude, longitude, searchRadius, searchString);
		}
	}
	
	public SchoolFinderService getFinderService() {
		return finderService;
	}

	public void setFinderService(SchoolFinderService finderService) {
		this.finderService = finderService;
	}
}

