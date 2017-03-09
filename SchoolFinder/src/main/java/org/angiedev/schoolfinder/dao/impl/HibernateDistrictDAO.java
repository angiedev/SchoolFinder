package org.angiedev.schoolfinder.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.angiedev.schoolfinder.dao.DistrictDAO;
import org.angiedev.schoolfinder.model.District;

import java.util.List;

/**
 * HibernateDistrictDAO is a hibernate based implementation of the DistrictDAO interface.
 * @author Angela Gordon
 */

@Repository 
public class HibernateDistrictDAO implements DistrictDAO {

	@Autowired 
	private SessionFactory sessionFactory; 	
	
	@Override
	public void insertDistrict(District district) {
		sessionFactory.getCurrentSession().save(district);
	}
	
	@Override 
	public District getDistrict(long districtId) {
		return (District)sessionFactory.getCurrentSession().get(District.class, districtId);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public District getDistrictByLeaId(String leaId) {
		List<District> districts = (List<District>)sessionFactory.getCurrentSession().getNamedQuery("District.findByLeaId").
			setParameter("leaId", leaId, StringType.INSTANCE).getResultList();
		if (districts.size() == 0) {
			return null;
		} else {
			return districts.get(0);
		}
	} 
	
	@Override
	public void updateDistrict(District district) {
		sessionFactory.getCurrentSession().saveOrUpdate(district);	
	}

	@Override
	public void deleteDistrict(District district) {
		sessionFactory.getCurrentSession().delete(district);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<District> getDistricts() {
		return (List<District>)sessionFactory.getCurrentSession().getNamedQuery("District.findAll").getResultList();
	}

}
