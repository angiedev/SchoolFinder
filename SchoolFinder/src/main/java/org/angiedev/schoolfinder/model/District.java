package org.angiedev.schoolfinder.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *  District is a data model representing a District (Local Education Agency (LEA)) responsible for a school. 
 *  
 *  @author Angela Gordon
 */
@Entity 
@Table(name="District")
@NamedQueries(
	{ @NamedQuery(name = "District.findAll", query = "from District"),
	  @NamedQuery(name = "District.findByLeaId", query = "from District where leaId=:leaId")})

public class District {
	
	private long districtId;
	private String leaId; // local education agency id defined by NCES (Natl Center for Education Statistics)
	private String name; 
	
	/**
	 * Creates a district with the passed in name and leaId.
	 * @param name		name of district.
	 * @param leaId		unique local education agency id defined by NCES (Natl Center for Education Statistics).
	 */
	public District(String name, String leaId) {
		this.name = name;
		this.leaId = leaId;
	}

	/**
	 * Default constructor required for hibernate
	 */
	District() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="district_id")
	public long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}

	@Column(name="lea_id")
	public String getLeaId() {
		return leaId;
	}

	public void setLeaId(String leaId) {
		this.leaId = leaId;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (districtId ^ (districtId >>> 32));
		result = prime * result + ((leaId == null) ? 0 : leaId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		District other = (District) obj;
		if (districtId != other.districtId)
			return false;
		if (leaId == null) {
			if (other.leaId != null)
				return false;
		} else if (!leaId.equals(other.leaId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "District [districtId=" + districtId + ", leaId=" + leaId + ", name=" + name + "]";
	}

	
}
