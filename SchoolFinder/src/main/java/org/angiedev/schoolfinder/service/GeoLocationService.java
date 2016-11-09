package org.angiedev.schoolfinder.service;

import java.io.IOException;

import org.angiedev.schoolfinder.model.GeoLocation;

/**
 * GeoLoctionService is a interface for a service which enables a user to lookup 
 * the geolocation (latitude/longitude) for an address.
 * 
 * @author Angela Gordon
 */
public interface GeoLocationService {
	/**
	  * Returns the geo location for the passed in address
	  * @param address 		street address
	  * @param city 		city	
	  * @param stateCode 	two letter state code
	  * @param zip			zip code
	  * @return 			geo location of address
	  */
	public GeoLocation getGeoLocationForAddress(String address, String city, String stateCode, String zip)
		 	throws IOException;
}
