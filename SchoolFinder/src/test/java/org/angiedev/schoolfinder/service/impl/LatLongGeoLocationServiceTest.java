package org.angiedev.schoolfinder.service.impl;



import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.angiedev.schoolfinder.model.GeoLocation;
import org.angiedev.schoolfinder.service.impl.LatLongGeoLocationService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/SchoolFinderConfig.xml"})
public class LatLongGeoLocationServiceTest {

	
	LatLongGeoLocationService service = new LatLongGeoLocationService();
	
	@Test
	public void testGetGeoLocationForAddress() throws Exception {
	
		String address = "10411 Lansdale Ave";
		String city = "Cupertino";
		String stateCode = "CA";
		String zip = "95014";
		
		GeoLocation location = service.getGeoLocationForAddress(address, city, stateCode, zip);
		
		assertEquals(37.3174643482011,location.getLatitude(),0.001);
		assertEquals(-122.02158463013, location.getLongitude(),0.001);
	}
	
	@Test(expected=IOException.class)
	public void testGetGeoLocationForBadAddress() throws Exception {
	
		String address = "10";
		String city = "Cupertino";
		String stateCode = "ca";
		String zip = "95014";
		
		service.getGeoLocationForAddress(address, city, stateCode, zip);
		
	}

}
