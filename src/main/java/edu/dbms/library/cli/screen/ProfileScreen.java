package edu.dbms.library.cli.screen;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import dnl.utils.text.table.TextTable;
import edu.dbms.library.cli.Constant;
import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;
import edu.dbms.library.entity.Faculty;
import edu.dbms.library.entity.Patron;
import edu.dbms.library.entity.Student;
import edu.dbms.library.session.SessionUtils;

/*
 *  TODO: 	Fire a query based on the PATRON table for details
 *  	  	Pass back some flag or parameter to differentiate between 
 *  	  	Student and Faculty
 */
public class ProfileScreen extends BaseScreen {

	public ProfileScreen() {
		super();
		options.put(1, new Route(RouteConstant.BACK));
		options.put(2, new Route(RouteConstant.LOGOUT));
		getPatronDetails();
	}
	
	@Override
	public void execute() {
		displayOptions();
		readInputLabel();
		Object o = readInput();
		while(!(o instanceof Integer)) {
			System.out.println("Incorrect input.");
			readInputLabel();
			o = readInput();
		}
		
		BaseScreen nextScreen = getNextScreen(options.get((Integer)o).getRouteKey());
		nextScreen.execute();
	}

	private void displayPatronDetails() {
		Patron patronObj;
		Student studentObj;
		Faculty facultyObj;
		
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(
				DBUtils.DEFAULT_PERSISTENCE_UNIT_NAME);
		/*EntityManager entitymanager = emFactory.createEntityManager();
		Query query = entitymanager.createQuery("SELECT p, d.departmentName"
				+ " FROM Patron p, Department d"
				+ " WHERE p.id=:patronId"
				+ " AND d.id=p.department.id");
		query.setParameter("patronId", SessionUtils.getPatronId());
		patronDataObj = query.getSingleResult();*/
		patronObj = (Patron) DBUtils.findEntity(Patron.class, SessionUtils.getPatronId(), String.class);
		
		if(SessionUtils.isStudent()) {
			/*Query patronTypeQuery = entitymanager.createQuery("SELECT s.student_id, s.phone_no, s.alt_phone_no, s.dob, s.sex, s.addressLineOne, s.addressLineTwo, s.cityName, s.pinCode"
					+ " FROM Student s"
					+ " WHERE s.student_id=:patronId");
			patronTypeQuery.setParameter("patronId", SessionUtils.getPatronId());
			patronTypeQuery.getSingleResult();*/
			
		} else {
			/*Query patronTypeQuery = entitymanager.createQuery("SELECT f.faculty_id, fc.name AS cat_name"
					+ " FROM Faculty f, Faculty_Category fc"
					+ " WHERE f.faculty_id=:patronId"
					+ " AND fc.id=f.category_id");
			patronTypeQuery.setParameter("patronId", SessionUtils.getPatronId());
			patronTypeDataObj = patronTypeQuery.getSingleResult();*/
			
		}
		
		System.out.println("Name: " + patronObj.getFirstName() + " " + patronObj.getLastName());
		System.out.println("Email Address: " + (patronObj.isEmailAddresNull() ? "--" : patronObj.getEmailAddress()));
		if(SessionUtils.isStudent())
		{
			studentObj = (Student) DBUtils.findEntity(Student.class, SessionUtils.getPatronId(), String.class);
			System.out.println("Phone Number: " + (studentObj.isPhoneNumberNull() ? "--" : studentObj.getPhoneNumber()));
			System.out.println("Alternate Phone Number: " + (studentObj.isAlternatePhoneNumberNull() ? "--" : studentObj.getAlternatePhoneNumber()));
			System.out.println("Home Address: " + (studentObj.isHomeAddressNull() ? "--" : studentObj.getHomeAddressString()));
			System.out.println("Date of Birth: " + (studentObj.isDateOfBirthNull() ? "--" : studentObj.getDateOfBirth().toString()));
			System.out.println("Sex: " + (studentObj.isSexNull() ? "--" : studentObj.getSex()));
			System.out.println("Student Classification: " + studentObj.getDegreeYear().getDegreeProgram().getClassification().getName());
			System.out.println("Degree: " + studentObj.getDegreeYear().getDegreeProgram().getName());
		} else {
			facultyObj = (Faculty) DBUtils.findEntity(Faculty.class, SessionUtils.getPatronId(), String.class);
			System.out.println("Faculty Category: " + facultyObj.getCategory().getName());
		}
		System.out.println("Nationality: " + patronObj.getNationality());
		System.out.println("Department: " + patronObj.getDepartment().getDepartmentName());
	}
	
	@Override
	public void displayOptions() {
		
		System.out.println("Your Profile:");
		displayPatronDetails();
		System.out.println("-------------------------------------------");
		
		String[] title = {""};
		String[][] options = { 
							{Constant.OPTION_BACK},
							{Constant.OPTION_LOGOUT}
							};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();
		
	}
	
	public void readInputLabel() {
		System.out.print("Enter your choice: ");
	}
	
	public Object readInput() {
		int option = inputScanner.nextInt();
		return option;
	}

	// Implement the internal logic for this method
	private Object getPatronDetails() {
		
		return null;
	}
	
}
