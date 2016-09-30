package org.angiedev.schoolfinder.loader;

import java.util.Scanner;

import org.angiedev.schoolfinder.dao.DistrictDAO;
import org.angiedev.schoolfinder.dao.SchoolDAO;
import org.angiedev.schoolfinder.model.District;
import org.angiedev.schoolfinder.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


/**
 * SchoolDataLoader loads the School Finder database with the school data provided by 
 * the NCES (National Center for Education Statistics).
 * <p>
 * The loader parses the school data file which provides our application with the school, 
 * location and district information for private and public elementary and secondary schools 
 * in the United States and adds the data into our database.  This loader should only be run 
 * once when first initializing our application.
 * <p>
 * Input Data Expected (separated by tabs):
 *<ul>
 *   <li>NCES School Id 
 *   <li>LEA ID (district Id)
 *   <li>District Name
 *   <li>School Name
 *   <li>Street Address
 *   <li>City
 *   <li>State
 *   <li>Zip
 *   <li>Status
 *   <li>Low Grade
 *   <li>High Grade
 *  </ul>
 *  <p>
 *  The first line of the input file will be skipped since it contains the data headers.
 *  <p>
 *   
 * @author Angela Gordon
 */
@Component
public class SchoolDataLoader {

	// Data field positions in input file 
	private static final int NCES_ID = 0, LEA_ID = 1, DISTRICT = 2, SCHOOL = 3, 
			ADDRESS = 4, CITY = 5, STATE = 6, ZIP = 7, STATUS = 8, LOW_GRADE = 9, 
			HIGH_GRADE = 10;
    
    @Autowired
    private DistrictDAO districtDAO;
    
    @Autowired
    private SchoolDAO schoolDAO;
    
    /**
     * Kicks off the data load using the passed in data file
     * @param args	First argument identifies the data file that needs to be loaded
     * 				(i.e. /data/Parsed-Sch14pre.txt).
     */
	public static void main(String[] args) {
		try (ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("/WEB-INF/SchoolFinderConfig.xml")) {
			SchoolDataLoader loader = context.getBean(SchoolDataLoader.class);
			
			if (args.length == 0) {
				System.out.println("Usage: java SchoolDataLoader <dataFileName>");
				return;
			}
			loader.parseAndLoadData(args[0]);
		}
	}
	
	/*
	 * Parses and loads school data into the School Finder database.
	 */
	private void parseAndLoadData(String inputFile) {
		
		String s = null;
		String lastLeaId = null;
		District district = null;
		
		int numSchools = 0, numNewSchools = 0;
		int numDistricts = 0, numNewDistricts = 0;
		
		try (Scanner scanner = new Scanner(this.getClass().getResourceAsStream(inputFile))) {
			if (scanner.hasNext()) {
				scanner.nextLine(); // skip header data
			}
			while (scanner.hasNext()) {
				s = scanner.nextLine();
				String[] tokens = s.split("\t");
				
				// don't repeatedly try to create a district already created in input file 
				// (note: records in input file are listed in order of districts)
				if (!tokens[LEA_ID].equals(lastLeaId)) {
					numDistricts++;
					district = districtDAO.getDistrictByLeaId(tokens[LEA_ID]);
					
					// if district doesn't already exist then create it 
					if (district == null) {
						district = new District(tokens[DISTRICT], tokens[LEA_ID]);
						districtDAO.insertDistrict(district);
						numNewDistricts++;
					}
					lastLeaId = district.getLeaId();
				}
				
				numSchools++;
				School school = schoolDAO.getSchoolByNcesId(tokens[NCES_ID]);
				
				if (school == null) {
					school = new School(tokens[NCES_ID], tokens[SCHOOL], 
						tokens[ADDRESS], tokens[CITY], tokens[STATE], tokens[ZIP], 
						Integer.parseInt(tokens[STATUS]), tokens[LOW_GRADE], tokens[HIGH_GRADE], district);
					schoolDAO.insertSchool(school);
					numNewSchools++;
				}
			}
		} finally {
			System.out.println("Load results:");
			System.out.println("-----------------------------------------");
			System.out.println("# of Districts processed: " + numDistricts);
			System.out.println("# of Districts added to DB: " + numNewDistricts);
			System.out.println("# of Schools processed: " + numSchools);
			System.out.println("# of Schools added to DB: " + numNewSchools);
		}
	}

}
