package org.angiedev.schoolfinder.dao;

import java.util.List; 

import org.angiedev.schoolfinder.model.District;

/**
 * DistrictDAO is an interface which defines the data access operations 
 * to find, create and update districts.
 * 
 * @author Angela Gordon
 */ 
public interface DistrictDAO {

	/**
	 * Inserts a new district into the database.
	 * @param district 	the district being added to the database.
	 */
	public void insertDistrict(District district);
	
	/**
	 * Retrieves a district based on the passed in id.
	 * @param districtId 	the id uniquely identifying the district in the database (PK).
	 * @return 				the district if found, otherwise null.
	 */
	public District getDistrict(long districtId);
	
	/**
	 * Retrieves a district based on the passed in lea id.
	 * @param leaId 	the Local Education Agency Id for the district assigned by 
	 * 					the National Center for Education Statistics.
	 * @return 			the district if found, otherwise null.
	 */
	public District getDistrictByLeaId(String leaId);
	
	/**
	 * Updates a district with the passed in district's object data. 
	 * @param district	district object containing new data that needs to be updated in database.
	 */
	public void updateDistrict(District district);
	
	/**
	 * Deletes the passed in district from the database.
	 * @param district 	district being deleted.
	 */
	public void deleteDistrict(District district);
	
	/**
	 * Retrieves the list of districts in the database.
	 * @return	the list of districts in database.
	 */
	public List<District> getDistricts();
}
