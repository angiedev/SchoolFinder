package org.angiedev.schoolfinder.service.impl;

import java.util.List;

import org.angiedev.schoolfinder.dao.SchoolDAO;
import org.angiedev.schoolfinder.model.School;
import org.angiedev.schoolfinder.service.SchoolFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SchoolFinderServiceImpl is a service which enables a user to search 
 * through the SchoolFinder database for a particular set of schools.
 *
 * @author Angela Gordon
 */
@Service
public class SchoolFinderServiceImpl implements SchoolFinderService {

	@Autowired 
	SchoolDAO schoolDAO;
	
	@Override
	public List<School> getSchools(double latitude, double longitude, int searchRadius) {
		return schoolDAO.getSchoolsNearGeoLocation(latitude, longitude, searchRadius);
	}
}
