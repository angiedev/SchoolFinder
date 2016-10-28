package org.angiedev.schoolfinder.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.angiedev.schoolfinder.model.School;
import org.angiedev.schoolfinder.service.SchoolFinderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/SchoolFinderConfig.xml"})
public class SchoolFinderServiceImplTest {

	@Autowired 
	SchoolFinderService service;
	
	@Test
	public void testGetSchoolsWithSmallRadius() throws Exception {
		
		// Address of Safeway parking lot in Almaden
		double latitude = 37.219836;
		double longitude = -121.861611;
		int searchRadius = 2;
		
		List<School> schools = service.getSchools(latitude, longitude, searchRadius, 100);
		
		List<String> schoolNames = new ArrayList<String>();
		
		for (School school: schools) {
			schoolNames.add(school.getName());
		}
		
		assertTrue("Simonds should be found", schoolNames.contains("Simonds Elementary"));
		assertTrue("Graystone should be found", schoolNames.contains("Graystone Elementary"));
		assertTrue("Williams should be found", schoolNames.contains("Williams Elementary"));
		assertTrue("Castillero should be found", schoolNames.contains("Castillero Middle"));
		assertTrue("Leland should be found", schoolNames.contains("Leland High"));
		assertFalse("Pioneer should not be found", schoolNames.contains("Pioneer High"));
		assertFalse("Gunderson should not be found", schoolNames.contains("Gunderson High"));
		assertFalse("Willow Glen should not be found", schoolNames.contains("Willow Glen Elementary"));
		assertFalse("Reed should not be found", schoolNames.contains("Reed Elementary"));
	
	}

	@Test
	public void testGetSchoolsWithLargerRadius() throws Exception {
		
		// Address of Safeway parking lot in Almaden
		double latitude = 37.219836;
		double longitude = -121.861611;
		int searchRadius = 5;
		
		List<School> schools = service.getSchools(latitude, longitude, searchRadius, 100);
		
		List<String> schoolNames = new ArrayList<String>();
		
		for (School school: schools) {
			schoolNames.add(school.getName());
		}
		
		assertTrue("Simonds should be found", schoolNames.contains("Simonds Elementary"));
		assertTrue("Graystone should be found", schoolNames.contains("Graystone Elementary"));
		assertTrue("Williams should be found", schoolNames.contains("Williams Elementary"));
		assertTrue("Castillero should be found", schoolNames.contains("Castillero Middle"));
		assertTrue("Leland should be found", schoolNames.contains("Leland High"));
		assertTrue("Pioneer should be found", schoolNames.contains("Pioneer High"));
		assertTrue("Gunderson should be found", schoolNames.contains("Gunderson High"));
		assertFalse("Willow Glen should not be found", schoolNames.contains("Willow Glen Elementary"));
		assertTrue("Reed should  be found", schoolNames.contains("Reed Elementary"));
		
	}
	
	@Test
	public void testGetSchoolsWithSearchString() throws Exception {
		// Address of Safeway parking lot in Almaden
		double latitude = 37.219836;
		double longitude = -121.861611;
		int searchRadius = 15;
		
		List<School> schools = service.getSchools(latitude, longitude, searchRadius, "Will", 100);
		
		List<String> schoolNames = new ArrayList<String>();
		
		for (School school: schools) {
			schoolNames.add(school.getName());
		}
		
		assertFalse("Simonds should not be found", schoolNames.contains("Simonds Elementary"));
		assertFalse("Graystone should not be found", schoolNames.contains("Graystone Elementary"));
		assertTrue("Williams should be found", schoolNames.contains("Williams Elementary"));
		assertFalse("Castillero should not be found", schoolNames.contains("Castillero Middle"));
		assertFalse("Leland should not be found", schoolNames.contains("Leland High"));
		assertFalse("Pioneer should not be found", schoolNames.contains("Pioneer High"));
		assertFalse("Gunderson should not be found", schoolNames.contains("Gunderson High"));
		assertTrue("Willow Glen should not be found", schoolNames.contains("Willow Glen Elementary"));
		assertFalse("Reed should not be found", schoolNames.contains("Reed Elementary"));
	}

	@Test
	public void testGetSchoolByNcesId() throws Exception {
		String ncesId = "063459005738";  // NCES Id of Simonds Elementary
		School s = service.getSchoolByNcesId(ncesId);
		
		assertNotNull("School should not be null", s);
		assertTrue("School name should be Simonds Elementary", s.getName().equals("Simonds Elementary"));
		assertTrue("School city should be San Jose", s.getCity().equals("San Jose"));
		assertTrue("School address should be 6515 Grapevine Way", s.getStreetAddress().equals("6515 Grapevine Way"));
		assertTrue("School state should be CA", s.getState().equals("CA"));
		assertTrue("School zip should be 95120", s.getZip().equals("95120"));
		assertTrue("School low grade should be KG", s.getLowGrade().equals("KG"));
		assertTrue("School high grade should be KG", s.getHighGrade().equals("05"));
		assertTrue("School id should be 063459005738", s.getNcesId().equals("063459005738"));
	}

}
