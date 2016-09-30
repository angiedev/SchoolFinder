package org.angiedev.schoolfinder.dao.impl;


import org.angiedev.schoolfinder.model.District;
import org.angiedev.schoolfinder.model.School;
import org.angiedev.schoolfinder.dao.DistrictDAO;
import org.angiedev.schoolfinder.dao.SchoolDAO;

import java.util.List; 

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/SchoolFinderConfig.xml"})


public class HibernateSchoolDAOTest {

	private static final String DISTRICT_NAME = "Test: District Name";
	private static final String NEW_DISTRICT_NAME = "Test: New District Name";
	private static final String DISTRICT_LEA_ID = "1234567";
	private static final String NEW_DISTRICT_LEA_ID = "7654321";
	private static final String SCHOOL_NCES_ID = "Test:NCESID";
	private static final String NEW_SCHOOL_NCES_ID = "Test:NCESID2";
	private static final String SCHOOL_NAME = "Test: School Name";
	private static final String NEW_SCHOOL_NAME = "Test: New Name";
	private static final String SCHOOL_STR_ADDRESS = "Test: School Address";
	private static final String NEW_SCHOOL_STR_ADDRESS = "Test: New School Address";
	private static final String SCHOOL_CITY = "Test: School City";
	private static final String NEW_SCHOOL_CITY = "Test: New School City";
	private static final String SCHOOL_STATE = "CA";
	private static final String NEW_SCHOOL_STATE = "AZ";
	private static final String SCHOOL_ZIP = "12345";
	private static final String NEW_SCHOOL_ZIP = "23456";
	private static final int SCHOOL_STATUS = 1;
	private static final int NEW_SCHOOL_STATUS = 2;
	private static final String SCHOOL_LOW_GRADE = "01";
	private static final String NEW_SCHOOL_LOW_GRADE = "06";
	private static final String SCHOOL_HIGH_GRADE = "06";
	private static final String NEW_SCHOOL_HIGH_GRADE = "12";
	
	@Autowired 
	private DistrictDAO districtDAO;
	
	@Autowired 
	private SchoolDAO schoolDAO;

	private District district;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {	

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {	
		if (district == null) {
			// Create and Insert District to be used for testing
			district = new District(DISTRICT_NAME, DISTRICT_LEA_ID);
			districtDAO.insertDistrict(district);
		}		
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testGetAndInsertSchool() throws Exception {
		
        // Create School
		School school = new School(SCHOOL_NCES_ID, SCHOOL_NAME, SCHOOL_STR_ADDRESS, SCHOOL_CITY, 
				SCHOOL_STATE, SCHOOL_ZIP, SCHOOL_STATUS, SCHOOL_LOW_GRADE, SCHOOL_HIGH_GRADE, district);
		schoolDAO.insertSchool(school);
		
		// Confirm school was created
		long schoolId = school.getSchoolId();
		assertNotEquals(0,schoolId);
		
		// Retrieve School 
		School fetchedSchool = schoolDAO.getSchool(schoolId);

		// Confirm insert results
		assertEquals(SCHOOL_NAME, fetchedSchool.getName());
		assertEquals(SCHOOL_NCES_ID, fetchedSchool.getNcesId());
		assertEquals(SCHOOL_STR_ADDRESS, fetchedSchool.getStreetAddr());
		assertEquals(SCHOOL_CITY, fetchedSchool.getCity());
		assertEquals(SCHOOL_STATE, fetchedSchool.getState());
		assertEquals(SCHOOL_ZIP, fetchedSchool.getZip());	
		assertEquals(SCHOOL_STATUS, fetchedSchool.getStatus());	
		assertEquals(SCHOOL_LOW_GRADE, fetchedSchool.getLowGrade());	
		assertEquals(SCHOOL_HIGH_GRADE, fetchedSchool.getHighGrade());	
		assertEquals(district.getDistrictId(), fetchedSchool.getDistrict().getDistrictId());
		assertEquals(district.getName(), fetchedSchool.getDistrict().getName());
		assertEquals(district.getLeaId(), fetchedSchool.getDistrict().getLeaId());
	}
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testGetSchoolByNcesId() throws Exception {
		
		// Create School
		School school = new School(SCHOOL_NCES_ID, SCHOOL_NAME, SCHOOL_STR_ADDRESS, SCHOOL_CITY, 
				SCHOOL_STATE, SCHOOL_ZIP, SCHOOL_STATUS, SCHOOL_LOW_GRADE, SCHOOL_HIGH_GRADE, district);

		schoolDAO.insertSchool(school);
	
		// Retrieve school using NCES Id 
		School fetchedSchool = schoolDAO.getSchoolByNcesId(SCHOOL_NCES_ID);
		
		// Validate correct school returned
		assertEquals(school, fetchedSchool);
		
	}
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testGetSchoolByNcesIdWithNonExistentNcesId() throws Exception {
		
		// Create School
		School school = new School(SCHOOL_NCES_ID, SCHOOL_NAME, SCHOOL_STR_ADDRESS, SCHOOL_CITY, 
				SCHOOL_STATE, SCHOOL_ZIP, SCHOOL_STATUS, SCHOOL_LOW_GRADE, SCHOOL_HIGH_GRADE, district);
		schoolDAO.insertSchool(school);
	
		School fetchedSchool = schoolDAO.getSchoolByNcesId("INVALID");
		
		assertNull(fetchedSchool);
	}
	
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testUpdateSchool() {
		
		// Create School
		School school = new School(SCHOOL_NCES_ID, SCHOOL_NAME, SCHOOL_STR_ADDRESS, SCHOOL_CITY, 
				SCHOOL_STATE, SCHOOL_ZIP, SCHOOL_STATUS, SCHOOL_LOW_GRADE, SCHOOL_HIGH_GRADE, district);
		schoolDAO.insertSchool(school);

		// Update School Info
		school.setName(NEW_SCHOOL_NAME);
		school.setNcesId(NEW_SCHOOL_NCES_ID);
		school.setStreetAddr(NEW_SCHOOL_STR_ADDRESS);
		school.setCity(NEW_SCHOOL_CITY);
		school.setState(NEW_SCHOOL_STATE);
		school.setZip(NEW_SCHOOL_ZIP);
		school.setStatus(NEW_SCHOOL_STATUS);
		school.setLowGrade(NEW_SCHOOL_LOW_GRADE);
		school.setHighGrade(NEW_SCHOOL_HIGH_GRADE);
	
		// Update school's district
		District newDistrict = new District(NEW_DISTRICT_NAME, NEW_DISTRICT_LEA_ID);
		districtDAO.insertDistrict(newDistrict);
        school.setDistrict(newDistrict);
        
		// Update DB
		schoolDAO.updateSchool(school);
	
		// Retrieve from DB 
		School fetchedSchool = schoolDAO.getSchool(school.getSchoolId());
		
		// verify data was updated
		assertEquals(NEW_SCHOOL_NAME, fetchedSchool.getName());
		assertEquals(NEW_SCHOOL_NCES_ID, fetchedSchool.getNcesId());
		assertEquals(NEW_SCHOOL_STR_ADDRESS, fetchedSchool.getStreetAddr());
		assertEquals(NEW_SCHOOL_CITY, fetchedSchool.getCity());
		assertEquals(NEW_SCHOOL_STATE, fetchedSchool.getState());
		assertEquals(NEW_SCHOOL_ZIP, fetchedSchool.getZip());	
		assertEquals(NEW_SCHOOL_STATUS, fetchedSchool.getStatus());	
		assertEquals(NEW_SCHOOL_LOW_GRADE, fetchedSchool.getLowGrade());	
		assertEquals(NEW_SCHOOL_HIGH_GRADE, fetchedSchool.getHighGrade());	
		
		assertEquals(newDistrict, fetchedSchool.getDistrict());
	}
	
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testDeleteSchool() {
		
		// Create School
		School school = new School(SCHOOL_NCES_ID, SCHOOL_NAME, SCHOOL_STR_ADDRESS, SCHOOL_CITY, 
				SCHOOL_STATE, SCHOOL_ZIP, SCHOOL_STATUS, SCHOOL_LOW_GRADE, SCHOOL_HIGH_GRADE, district);

		schoolDAO.insertSchool(school);
		
		long schoolId = school.getSchoolId();
	
		// Delete School
		schoolDAO.deleteSchool(school);
		
		// Confirm deletion 
		School fetchedSchool = schoolDAO.getSchool(schoolId);
		assertNull(fetchedSchool);
	}
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testGetSchoolsInDistrictWithOneSchool() {
		
		// Create School
		School school = new School(SCHOOL_NCES_ID, SCHOOL_NAME, SCHOOL_STR_ADDRESS, SCHOOL_CITY, 
				SCHOOL_STATE, SCHOOL_ZIP, SCHOOL_STATUS, SCHOOL_LOW_GRADE, SCHOOL_HIGH_GRADE, district);

		schoolDAO.insertSchool(school);
		
		List<School> schools = schoolDAO.getSchoolsByDistrictId(district.getDistrictId());
		
		assertEquals(1, schools.size());
		assertEquals(school, schools.get(0));
		
	}
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testGetSchoolsInDistrictWithNoSchools() {
		
		// Create school
		School school = new School(SCHOOL_NCES_ID, SCHOOL_NAME, SCHOOL_STR_ADDRESS, SCHOOL_CITY, 
				SCHOOL_STATE, SCHOOL_ZIP, SCHOOL_STATUS, SCHOOL_LOW_GRADE, SCHOOL_HIGH_GRADE, district);

		schoolDAO.insertSchool(school);
		
		// Create a new district without any schools
		District newDistrict = new District(NEW_DISTRICT_NAME, NEW_DISTRICT_LEA_ID);
		districtDAO.insertDistrict(newDistrict);

		// Get schools for district with no schools
		List<School> schools = schoolDAO.getSchoolsByDistrictId(newDistrict.getDistrictId());
		
		// Should be none!
		assertEquals(0, schools.size());
		
	}
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testGetSchoolsInDistrictWithMultipleSchools() {
		
		// Create multiple schools in district
		School[] schools = new School[10];
		
		for (int i = 0; i < schools.length; i++) {
			schools[i] = new School(""+i, "NAME" + i , "ADDRESS" + i, "CITY" + i, 
				"CA", "" + i, i, "0" + Integer.toString(i), "1"+ Integer.toString(i), district);
			schoolDAO.insertSchool(schools[i]);
		}
		
		List<School> fetchedSchools = schoolDAO.getSchoolsByDistrictId(district.getDistrictId());
		
		for (School s: schools) {
			assertTrue(fetchedSchools.contains(s));
		}
		
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetSchoolsByStateWithNoGeoData() throws Exception {
		School school1 = new School("1", "School in CA", "123 Main Street", 
				"San Diego", "CA", "92093", 1, "01", "06", district);
		schoolDAO.insertSchool(school1);
		
		School school2 = new School("2", "Another School in CA", "123 Main Street", 
				"San Diego", "CA", "92093", 1, "01", "06", district);
		schoolDAO.insertSchool(school2);
		
		School school3 = new School("3", "School in OR", "123 Main Street", 
				"Portland", "OR", "97201", 1, "01", "06", district);
		schoolDAO.insertSchool(school3);
		
		School school4 = new School("4", "School in NY", "123 Main Street", 
				"New York", "NY", "10001", 1, "01", "06", district);
		schoolDAO.insertSchool(school4);
		
		List<School> fetchedSchools = schoolDAO.getSchoolsByStateWithNoGeoData("CA");
		
		assertTrue(fetchedSchools.contains(school1));
		assertTrue(fetchedSchools.contains(school2));
		assertFalse(fetchedSchools.contains(school3));
		assertFalse(fetchedSchools.contains(school4));
		
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetSchoolsNearGeoLocation() throws Exception {
		School school1 = new School("1", "SIMONDS ELEMENTARY", "6515 GRAPEVINE WAY",
				"SAN JOSE", "CA", "95120",1,"KG", "05",district);
		school1.setLatitude( 37.2205341);
		school1.setLongitude(-121.8690651);
		schoolDAO.insertSchool(school1);
		
		School school2 = new School("2", "WILLIAMS ELEMENTARY", "1150 RAJKOVICH WAY",
				"SAN JOSE", "CA", "95120",1,"KG", "05",district);
		school2.setLatitude( 37.2077983);
		school2.setLongitude(-121.8515333);
		schoolDAO.insertSchool(school2);
		
		School school3 = new School("3", "BOOKSIN ELEMENTARY", "1590 DRY CREEK RD.",
				"SAN JOSE", "CA", "95125",1,"KG", "05",district);
		school3.setLatitude(37.2881426);
		school3.setLongitude(-121.9058075);
		schoolDAO.insertSchool(school3);
		
		School school4 = new School("4", "LAKE FOREST ELEMENTARY", "21801 PITTSFORD DR.",
				"LAKE FOREST", "CA", "92630",1,"KG", "06",district);
		school4.setLatitude( 33.6527242);
		school4.setLongitude( -117.6725555);
		schoolDAO.insertSchool(school4);
		
		List<School> schools = schoolDAO.getSchoolsNearGeoLocation(37.21873,-121.886661, 3);
		
		assertTrue("Was school 1 returned as expected?", schools.contains( school1));
		assertTrue("Was school 2 returned as expected?", schools.contains(school2));
		assertFalse("Was school 3 not returned as expected?", schools.contains(school3));
		assertFalse("Was school 4 not returned as expected?", schools.contains(school4));
	}
	
}
