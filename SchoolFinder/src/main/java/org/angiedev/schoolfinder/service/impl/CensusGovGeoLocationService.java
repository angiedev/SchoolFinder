package org.angiedev.schoolfinder.service.impl;

import java.io.IOException;

import org.angiedev.schoolfinder.model.GeoLocation;
import org.angiedev.schoolfinder.service.GeoLocationService;
import org.angiedev.schoolfinder.service.impl.json.CensusGovGeocodingLookupResult;
import org.angiedev.schoolfinder.service.impl.json.LatLongGeocodingLookupResult;
import org.angiedev.schoolfinder.util.Props;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * CensusGovGeoLocationService is a service which enables a user to lookup 
 * a geolocation (latitude/longitude) for an address.  This service uses 
 * the US Census Bureau's geocoding api. (https://geocoding.geo.census.gov/)
 * @author Angela Gordon
 *
 */
@Service
public class CensusGovGeoLocationService implements GeoLocationService {
	
	 private static final String LOOKUP_URL = "https://geocoding.geo.census.gov/geocoder/locations/onelineaddress?";
	 private static final String ADDRESS_KEY_PARAM = "address=";
	 private static final String BENCHMARK_PARAM = "benchmark=Public_AR_Current";
	 private static final String JSON_PARAM = "format=json";
	 
	@Override
	public GeoLocation getGeoLocationForAddress(String address, String city, 
			String stateCode, String zip) throws IOException {
		
		String query = LOOKUP_URL + ADDRESS_KEY_PARAM + address + "," + city + "," + 
				stateCode + "," + zip +  "&" + BENCHMARK_PARAM + "&" + JSON_PARAM;
		try { 
			RestTemplate restTemplate = new RestTemplate();
			CensusGovGeocodingLookupResult result = 
					restTemplate.getForObject(query, CensusGovGeocodingLookupResult.class);
			if (result.getStatus().equals("OK")) {
				return result.getGeoLocation();
			} else {
				throw new IOException("Unable to get GeoLocation for address: " + address 
				+ "," + city + "," + stateCode + ".  CensusGovGeoCode API returned status: " +
				result.getStatus());
			}
		} catch (HttpClientErrorException e) {
			throw new IOException("Unable to get GeoLocation for address: " + address + 
				 "," + city + "," + stateCode + ".  CensusGovGeoCode API threw exception: " +
					e.getResponseBodyAsString());	
		}
	}
}
