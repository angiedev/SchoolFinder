package org.angiedev.schoolfinder.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.angiedev.schoolfinder.dao.DistrictDAO;
import org.angiedev.schoolfinder.model.District;

import java.util.List;

/**
 * HibernateDistrictDAO is a hibernate based implementation of the DistrictDAO interface.
 * @author Angela Gordon
 */

@Repository 
@Transactional
public class HibernateDistrictDAO implements DistrictDAO {

	@Autowired 
	private SessionFactory sessionFactory; 	
	
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
	public void insertDistrict(District district) {
		currentSession().save(district);
	}
	
	@Override 
	public District getDistrict(long districtId) {
		return (District)currentSession().get(District.class, districtId);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public District getDistrictByLeaId(String leaId) {
		List<District> districts = (List<District>)currentSession().getNamedQuery("District.findByLeaId").
			setParameter("leaId", leaId, StringType.INSTANCE).getResultList();
		if (districts.size() == 0) {
			return null;
		} else {
			return districts.get(0);
		}
	} 
	
	@Override
	public void updateDistrict(District district) {
		currentSession().saveOrUpdate(district);	
	}

	@Override
	public void deleteDistrict(District district) {
		currentSession().delete(district);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<District> getDistricts() {
		return (List<District>)currentSession().getNamedQuery("District.findAll").getResultList();
	}

}
