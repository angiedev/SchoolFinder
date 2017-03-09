package org.angiedev.schoolfinder.service.impl;

import java.io.IOException;

import org.angiedev.schoolfinder.model.GeoLocation;
import org.angiedev.schoolfinder.service.GeoLocationService;
import org.angiedev.schoolfinder.service.impl.json.LatLongGeocodingLookupResult;
import org.angiedev.schoolfinder.util.Props;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * LatLongGeoLocationService is a service which enables a user to lookup 
 * a geolocation (latitude/longitude) for an address.  This service uses 
 * the LatLon.io geocoding api.
 * @author Angela Gordon
 *
 */
@Service
@Transactional(readOnly=true)
public class LatLongGeoLocationService implements GeoLocationService {
	
	 private static final String LOOKUP_URL = "https://latlon.io/api/v1/geocode?";
	 private static final String ADDRESS_KEY_PARAM = "address=";
	 private static final String API_KEY_PARAM = "token=" + Props.getInstance().getLatLongAPIKey();
	 
	@Override
	public GeoLocation getGeoLocationForAddress(String address, String city, 
			String stateCode, String zip) throws IOException {
		
		String query = LOOKUP_URL + ADDRESS_KEY_PARAM + address + "," + city + "," + 
				stateCode + "," + zip +  "&" + API_KEY_PARAM;
		try { 
			RestTemplate restTemplate = new RestTemplate();
			LatLongGeocodingLookupResult result = 
					restTemplate.getForObject(query, LatLongGeocodingLookupResult.class);
			if (result.getStatus().equals("OK")) {
				return result.getGeoLocation();
			} else {
				throw new IOException("Unable to get GeoLocation for address: " + address 
				+ "," + city + "," + stateCode + ".  LatLongGeoCode API returned status: " +
				result.getStatus());
			}
		} catch (HttpClientErrorException e) {
			throw new IOException("Unable to get GeoLocation for address: " + address + 
				 "," + city + "," + stateCode + ".  LatLongGeoCode API threw exception: " +
					e.getResponseBodyAsString());	
		}
	}
}
