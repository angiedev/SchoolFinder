package org.angiedev.schoolfinder.dao.impl;

import java.util.List;

import org.angiedev.schoolfinder.dao.DistrictDAO;
import org.angiedev.schoolfinder.model.District;

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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/SchoolFinderConfig.xml"})

public class HibernateDistrictDAOTest {
	
	private static final String DISTRICT_NAME = "Test: District Name";
	private static final String NEW_DISTRICT_NAME = "Test: NewDistrict Name";
	private static final String DISTRICT_LEA_ID = "1234567";
	private static final String NEW_DISTRICT_LEA_ID = "7654321";

	@Autowired 
	private DistrictDAO districtDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testInsertAndGetDistrict() throws Exception {
		
		// Insert District into DB 
		District district = new District(DISTRICT_NAME, DISTRICT_LEA_ID);
		districtDAO.insertDistrict(district);
		
		// Validate id was generated for district
		long districtId = district.getDistrictId();
		assertNotEquals(0, districtId);
		
		// Retrieve district by Id 
		District fetchedDistrict = districtDAO.getDistrict(districtId);
		assertNotNull(fetchedDistrict);
		assertEquals(DISTRICT_LEA_ID, fetchedDistrict.getLeaId());
		assertEquals(DISTRICT_NAME, fetchedDistrict.getName());
	}
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testGetDistrictByLeaId() throws Exception {
		
		// Insert District into DB 
		District district = new District(DISTRICT_NAME, DISTRICT_LEA_ID);
		districtDAO.insertDistrict(district);
		
		// Retrieve district by LEA Id
		District fetchedDistrict = districtDAO.getDistrictByLeaId(DISTRICT_LEA_ID);
		assertNotNull(fetchedDistrict);
		assertEquals(district, fetchedDistrict);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetDistrictByLeaIdWithNonExistentLeaId() throws Exception {
		
		// Insert District into DB 
		District district = new District(DISTRICT_NAME, DISTRICT_LEA_ID);
		districtDAO.insertDistrict(district);
		
		// Retrieve it by LEA Id
		District fetchedDistrict = districtDAO.getDistrictByLeaId("INVALID");
		assertNull(fetchedDistrict);
	}
		
	@Test 
	@Transactional
	@Rollback(true)
	public void testUpdateDistrict()  throws Exception {
		
		// Insert District into DB 
		District district = new District(DISTRICT_NAME, DISTRICT_LEA_ID);
		districtDAO.insertDistrict(district);
		
		// Update district object 
		district.setLeaId(NEW_DISTRICT_LEA_ID);
		district.setName(NEW_DISTRICT_NAME);
		
		// Update district in DB
		districtDAO.updateDistrict(district);
		
		District fetchedDistrict = districtDAO.getDistrict(district.getDistrictId());
		assertNotNull(fetchedDistrict);
		assertEquals(NEW_DISTRICT_LEA_ID, fetchedDistrict.getLeaId());
		assertEquals(NEW_DISTRICT_NAME, fetchedDistrict.getName());
	}
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testDeleteDistrict() throws Exception {
		
		// Insert District into DB 
		District district = new District(DISTRICT_NAME, DISTRICT_LEA_ID);
		districtDAO.insertDistrict(district);
		
		// insure was created 
		long districtId = district.getDistrictId();
		assertNotEquals(0, districtId);
		
		// delete district from DB 
		districtDAO.deleteDistrict(district);
		District fetchedDistrict = districtDAO.getDistrict(districtId);
		
		// validate district was deleted
		assertNull(fetchedDistrict);
	}
	
	@Test 
	@Transactional
	@Rollback(true)
	public void testGetDistrictsWithMultipleDistricts() throws Exception {
		
		// Insert multiple districts
		District[] districts = new District[] {
				new District( "District 11111", "11111"),
				new District( "District 22222", "22222"),
				new District( "District 33333", "33333"),
				new District( "District 44444", "44444"),
				new District( "District 55555", "55555"),
				new District( "District 66666", "66666"),
				new District( "District 77777", "77777"),
				new District( "District 88888", "88888"),
				new District( "District 99999", "99999") };
		
		for (District d: districts) {
			districtDAO.insertDistrict(d);
		}
		
		List<District> fetchedDistricts = districtDAO.getDistricts();
		
		for (District d: districts) {
			assertTrue(fetchedDistricts.contains(d));
		}
	}
	
}
