package org.angiedev.schoolfinder.service.impl;

import java.io.IOException;

import org.angiedev.schoolfinder.service.impl.json.GoogleGeocodingLookupResult;
import org.angiedev.schoolfinder.model.GeoLocation;
import org.angiedev.schoolfinder.service.GeoLocationService;
import org.angiedev.schoolfinder.util.Props;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * GoogleGeoLocationService is a service which enables a user to lookup 
 * a geolocation (latitude/longitude) for an address.  This service uses 
 * the Google Maps geocoding api.
 * @author Angela Gordon
 *
 */
@Service
public class GoogleGeoLocationService implements GeoLocationService {

	 private static final String LOOKUP_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
	 private static final String ADDRESS_KEY_PARAM = "address=";
	 private static final String API_KEY_PARAM = "key=" + Props.getInstance().getGoogleAPIKey();

	@Override
	public GeoLocation getGeoLocationForAddress(String address, String city, String stateCode, String zip) throws IOException {
		
		// Remove leading #'s from street address since google apis don't support them
		if (address.startsWith("#")) {
			address = address.substring(1);
		}
		
		String query = LOOKUP_URL + ADDRESS_KEY_PARAM + address + "," + city + "," + 
				stateCode + "," + zip + "&" + API_KEY_PARAM;
		
		RestTemplate restTemplate = new RestTemplate();
		GoogleGeocodingLookupResult result = 
				restTemplate.getForObject(query, GoogleGeocodingLookupResult.class);
		
		if (result.getStatus().equals("OK")) {
			return result.getGeoLocation();
		} else {
			throw new IOException("Unable to get GeoLocation for address: " + address 
			+ "," + city + "," + stateCode + ".  GoogleGeoCode API returned status: " +
			result.getStatus());
		}
	}

}
