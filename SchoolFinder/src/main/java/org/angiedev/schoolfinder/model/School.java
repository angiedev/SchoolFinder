package org.angiedev.schoolfinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 *  School is a data model representing a School within a district.
 *  
 *  @author Angela Gordon
 */

@Entity
@Table(name="School")
@NamedQueries({
	@NamedQuery(name="School.findByDistrict", query="from School where district.districtId=:districtId order by name"),
	@NamedQuery(name="School.findByNcesId", query="from School where ncesId=:ncesId"),
	@NamedQuery(name="School.findByStateWithNoGeoData", query="from School where state=:state and longitude = null")})
public class School {
	
	private long schoolId; 
	private String ncesId;
	private String name;
	private String streetAddr;
	private String city;
	private String state;
	private String zip;
	private int status;
	private String lowGrade;
	private String highGrade;
	private Double longitude;
	private Double latitude;
	private District district;

	/** 
	 * Creates an instance of a school.
	 * @param ncesId		unique id for school assigned by the National Center for Education Statistics (NCES).
	 * @param name			name of school.
	 * @param streetAddr	street address of school.
	 * @param city			the city the school is located in.
	 * @param state			the state the school is located in.
	 * @param zip			the zip code the school is located in.
	 * @param status		the status provided by the NCES for the school. 
	 * @param lowGrade		the lowest grade being taught at the school.
	 * @param highGrade 	the highest grade being taught at the school.
	 * @param district		the district the school belongs to.
	 */
	public School(String ncesId, String name, String streetAddr, String city, String state, String zip,
			int status, String lowGrade, String highGrade, District district) {
		this.ncesId = ncesId;
		this.name = name;
		this.streetAddr = streetAddr;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.setStatus(status);
		this.setLowGrade(lowGrade);
		this.setHighGrade(highGrade);
		this.district = district;
	}
	
	/**
	 * Default constructor required for hibernate
	 */
	School() {		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="school_id")
	public long getSchoolId() {
		return schoolId;
	}
	
	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}
	
	@Column(name="nces_id")
	public String getNcesId() {
		return ncesId;
	}

	public void setNcesId(String ncesId) {
		this.ncesId = ncesId;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name="district_id")
	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}
	
	@Column(name="street_address")
	public String getStreetAddr() {
		return streetAddr;
	}

	public void setStreetAddr(String streetAddr) {
		this.streetAddr = streetAddr;
	}

	@Column(name="city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	@Column(name="state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name="zip")
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	
	@Column(name="status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name="low_grade")
	public String getLowGrade() {
		return lowGrade;
	}

	public void setLowGrade(String lowGrade) {
		this.lowGrade = lowGrade;
	}

	@Column(name="high_grade")
	public String getHighGrade() {
		return highGrade;
	}

	public void setHighGrade(String highGrade) {
		this.highGrade = highGrade;
	}

	@Column(name="longitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name="latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((highGrade == null) ? 0 : highGrade.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((lowGrade == null) ? 0 : lowGrade.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ncesId == null) ? 0 : ncesId.hashCode());
		result = prime * result + (int) (schoolId ^ (schoolId >>> 32));
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + status;
		result = prime * result + ((streetAddr == null) ? 0 : streetAddr.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		School other = (School) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (highGrade == null) {
			if (other.highGrade != null)
				return false;
		} else if (!highGrade.equals(other.highGrade))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (lowGrade == null) {
			if (other.lowGrade != null)
				return false;
		} else if (!lowGrade.equals(other.lowGrade))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ncesId == null) {
			if (other.ncesId != null)
				return false;
		} else if (!ncesId.equals(other.ncesId))
			return false;
		if (schoolId != other.schoolId)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (status != other.status)
			return false;
		if (streetAddr == null) {
			if (other.streetAddr != null)
				return false;
		} else if (!streetAddr.equals(other.streetAddr))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "School [schoolId=" + schoolId + ", ncesId=" + ncesId + ", name=" + name + ", streetAddr=" + streetAddr
				+ ", city=" + city + ", state=" + state + ", zip=" + zip + ", status=" + status + ", lowGrade="
				+ lowGrade + ", highGrade=" + highGrade + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", district=" + district + "]";
	}


}
