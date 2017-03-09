package org.angiedev.schoolfinder.service.impl;

import java.util.List;

import org.angiedev.schoolfinder.dao.SchoolDAO;
import org.angiedev.schoolfinder.model.School;
import org.angiedev.schoolfinder.service.SchoolFinderService;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SchoolFinderServiceImpl is a service which enables a user to search 
 * through the SchoolFinder database for a particular set of schools.
 *
 * @author Angela Gordon
 */
@Service
@Transactional(readOnly = true)
public class SchoolFinderServiceImpl implements SchoolFinderService {

	@Autowired 
	SchoolDAO schoolDAO;
	
	@Override
	public List<School> getSchools(double latitude, double longitude, int searchRadius, int maxNumResults) {
		List<School> schools = schoolDAO.getSchoolsNearGeoLocation(latitude, longitude, searchRadius, maxNumResults);
		fixCapitalization(schools);
		return schools;
	}

	@Override
	public List<School> getSchools(double latitude, double longitude, int searchRadius, String searchString,
			int maxNumResults) {
		List <School> schools = schoolDAO.getSchoolsNearGeoLocation(latitude, longitude, 
			searchRadius, searchString, maxNumResults);
		fixCapitalization(schools);
		return schools;
	}
	
	@Override
	public School getSchoolByNcesId(String ncesId) {
		School school = schoolDAO.getSchoolByNcesId(ncesId);
		fixCapitalization(school);
		return school;
	}
	
	/* Modifies the words in each school name, street address and city to begin 
	 * with a capitalized letter followed by lower case instead of being in all caps
	 */
	private void fixCapitalization(List<School> schools ) {
		for (School s: schools) {
			fixCapitalization(s);
		}
	}
	
	/* Modifies the words in the school's name, street address, city to begin 
	 * with a capitalized letter followed by lower case instead of being in all caps
	 */
	private void fixCapitalization(School s) {
		char [] delims = {'(', ' '};
		s.setName(WordUtils.capitalizeFully(s.getName(), delims));
		s.setCity(WordUtils.capitalizeFully(s.getCity(), delims));
		s.setStreetAddress(WordUtils.capitalizeFully(s.getStreetAddress(), delims));
	}
	
	
}
