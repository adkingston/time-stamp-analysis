package personofinterest.intelligentagent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import personofinterest.intelligentagent.model.Person;
import personofinterest.intelligentagent.model.TravelHistory;
import personofinterest.intelligentagent.model.Trip;

public class PersonDAOImpl implements PersonDAO {

	public ArrayList<Person> getAllPersons() {
		ArrayList<Person> details= new ArrayList<Person>();
		try {
			Connection con = ConnectionPool.getInstance().getConnection();
			PreparedStatement query = con.prepareStatement(
					"SELECT DISTINCT UUID, TITLE, FIRSTNAME, LASTNAME, COB, POB, DOB, PASSPORTNO FROM TESTDATA;"
					);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				Person person = new Person(rs);
				details.add(person);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}
	
	public ArrayList<Person> getSomePersons(ArrayList<String> ID) {
		ArrayList<Person> details= new ArrayList<Person>();
		try {
			Connection con = ConnectionPool.getInstance().getConnection();
			String queryString = "SELECT DISTINCT UUID, TITLE, FIRSTNAME, LASTNAME, COB, POB, DOB, PASSPORTNO FROM TESTDATA WHERE ";
			for (int i=0; i<ID.size(); i++) {
				String id = ID.get(i);
				queryString += "FIRSTNAME=" + "'" + id + "'" + 
							   " OR LASTNAME=" + "'" + id + "'" + 
							   " OR UUID=" + "'" + id + "'" + 
							   " OR PASSPORTNO=" + "'" + id + "'";
				if (i <= ID.size()-2) {
					queryString += " OR ";
				}
			}
			queryString += ";";
			PreparedStatement query = con.prepareStatement(queryString);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				Person person = new Person(rs);
				details.add(person);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}

	public TravelHistory getPersonTravelHistory(Person person) {
		String UUID = person.getUUID();
		TravelHistory history = new TravelHistory(person);
		try {
			// Open connection
			Connection con = ConnectionPool.getInstance().getConnection();
			PreparedStatement query = con.prepareStatement(
					"SELECT ENTRY_EXIT, TIMESTAMP FROM TESTDATA WHERE UUID = ?;"
					);
			query.setString(1, UUID);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				// Declare Travel Type
				String indicator = rs.getString(1); 
				String tracker;
				if (indicator.equals("A")) {
					tracker = "Arrived";
				} else {
					tracker = "Departed";
				}
				// Declare Time Stamp
				String DateStr = rs.getString(2).substring(0, 10);
				LocalDateTime time;
				try {
					String timeStr = rs.getString(2).substring(11, 19);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					time = LocalDateTime.parse(DateStr + " " + timeStr, formatter);
				} catch (StringIndexOutOfBoundsException e) {
					String timeStr = rs.getString(2).substring(11, 16);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					time = LocalDateTime.parse(DateStr + " " + timeStr, formatter);
				}
				// Create trip and add to travel history
				Trip trip = new Trip(tracker, time);
				history.add(trip);
			}
			return history;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return history;
	}
			
	
	public Person getPerson(String ID) {
		Person person = null;
		try {
			Connection con = ConnectionPool.getInstance().getConnection();
			PreparedStatement query = con.prepareStatement(
					"SELECT DISTINCT UUID, TITLE, FIRSTNAME, LASTNAME, COB, POB, DOB, PASSPORTNO FROM TESTDATA WHERE " +
							"FIRSTNAME = ? OR " +
							"LASTNAME = ? OR " +
							"UUID = ? OR " +
							"PASSPORTNO = ?;"
					);
			for (int i=1; i<=4; i++) {
				query.setString(i, ID);
			}
			ResultSet rs = query.executeQuery();
			rs.next();
			person = new Person(rs);
			return person;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return person;
	}
}