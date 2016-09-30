package org.angiedev.schoolfinder.dao.impl;

import org.angiedev.schoolfinder.model.School;
import org.angiedev.schoolfinder.dao.SchoolDAO;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * HibernateSchoolDAO is a hibernate based implementation of the SchoolDAO interface.
 * 
 * @author Angela Gordon
 */

@Repository 
@Transactional
public class HibernateSchoolDAO implements SchoolDAO {

	private SessionFactory sessionFactory; 
	
	@Autowired 
	public HibernateSchoolDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session currentSession() {
		Session session;
		try {
		    session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
		    session = sessionFactory.openSession();
		}
		return session;
	}
	
	@Override
	public void insertSchool(School school) {
		currentSession().save(school);
	}
	
	@Override 
	public School getSchool(long schoolId) {
		return (School)currentSession().get(School.class, schoolId);
	}
	
	@Override
	public void updateSchool(School school) {
		currentSession().saveOrUpdate(school);	
	}

	@Override
	public void deleteSchool(School school) {
		currentSession().delete(school);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<School> getSchoolsByDistrictId(long districtId) {	
		return (List<School>)currentSession().getNamedQuery("School.findByDistrict").
				setParameter("districtId", districtId, LongType.INSTANCE).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public School getSchoolByNcesId(String ncesId) {
		List<School> schools =  (List<School>)currentSession().getNamedQuery("School.findByNcesId").
				setParameter("ncesId", ncesId, StringType.INSTANCE).getResultList();
			
		if (schools.size() == 0) {
			return null;
		} else {
			return schools.get(0);
		}
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<School> getSchoolsByStateWithNoGeoData(String stateCode) {
		return (List<School>)currentSession().getNamedQuery("School.findByStateWithNoGeoData").
			setParameter("state", stateCode, StringType.INSTANCE).getResultList();
	}

	@Override
	public List<School> getSchoolsNearGeoLocation(double latitude, double longitude, int searchRadius) {
		
		String queryStr = "select school_id, nces_id, name, district_id, street_address, city, state," +
				 " zip, status, low_grade, high_grade, longitude, latitude, 3956 * 2 * " +
		         " ASIN(SQRT( POWER(SIN((:latitude - latitude)*pi()/180/2),2)" +
		         " +COS(:latitude*pi()/180 )*COS(latitude*pi()/180)" + 
		         " *POWER(SIN((:longitude-longitude)*pi()/180/2),2)))" +
		         " as distance from School where" +
		         " longitude between (:longitude-:searchRadius/cos(radians(:latitude))*69)" + 
		         " and (:longitude+:searchRadius/cos(radians(:latitude))*69)" +
		         " and latitude between (:latitude-(:searchRadius/69))" +
		         " and (:latitude+(:searchRadius/69))"+
		         " having distance < :searchRadius order by distance limit 100"; 
		
		return currentSession().createNativeQuery(queryStr, School.class).
			setParameter("longitude", longitude, DoubleType.INSTANCE).
			setParameter("latitude", latitude, DoubleType.INSTANCE).
			setParameter("searchRadius", searchRadius, IntegerType.INSTANCE).getResultList();
		
	}
}
