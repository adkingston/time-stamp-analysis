package personofinterest.intelligentagent.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import personofinterest.intelligentagent.PersonDAO;
import personofinterest.intelligentagent.PersonDAOImpl;

public class Person {
	
	private String UUID;
	private String FIRST_NAME;
	private String LAST_NAME;
	private String TITLE;
	private String PLACE_OF_BIRTH;
	private String COUNTRY_OF_BIRTH;
	private String DATE_OF_BIRTH;
	private String PASSPORT_NUMBER;
	private TravelHistory TRAVEL_HISTORY;

	public Person(ResultSet details) {
		try {
			this.UUID = details.getString(1);
			this.TITLE = details.getString(2);
			this.FIRST_NAME = details.getString(3);
			this.LAST_NAME = details.getString(4);
			this.COUNTRY_OF_BIRTH = details.getString(5);
			this.PLACE_OF_BIRTH = details.getString(6);
			this.DATE_OF_BIRTH = details.getString(7);
			this.PASSPORT_NUMBER = details.getString(8);
			findTravelHistory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void findTravelHistory() {
		PersonDAO dao = new PersonDAOImpl();
		this.TRAVEL_HISTORY = dao.getPersonTravelHistory(this);
	}
	
	public String getUUID() {
		return UUID;
	}
	
	public String getFirstName() {
		return FIRST_NAME;
	}
	
	public String getLastName() {
		return LAST_NAME;
	}
	
	public String getFullName() {
		return FIRST_NAME + " " + LAST_NAME;
	}
	
	public String getTitle() {
		return TITLE;
	}
	
	public String getPlaceOfBirth() {
		return PLACE_OF_BIRTH;
	}
	
	public String getCountryOfBirth() {
		return COUNTRY_OF_BIRTH;
	}
	
	public String getDateOfBirth() {
		return DATE_OF_BIRTH;
	}
	
	public String getPassportNumber() {
		return PASSPORT_NUMBER;
	}
	
	public TravelHistory getTravelHistory() {
		return TRAVEL_HISTORY;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();		
		sb.append(TITLE + ". " + FIRST_NAME + " " + LAST_NAME + ". ");
		
		if (PLACE_OF_BIRTH.equals(COUNTRY_OF_BIRTH)) {
			sb.append("From " + COUNTRY_OF_BIRTH + ". ");
		} else {
			sb.append("From " + PLACE_OF_BIRTH + ", " + COUNTRY_OF_BIRTH + ". ");
		}
		sb.append("Born on " + DATE_OF_BIRTH + ". ");
		sb.append("Passport Number: " + PASSPORT_NUMBER); 
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((COUNTRY_OF_BIRTH == null) ? 0 : COUNTRY_OF_BIRTH.hashCode());
		result = prime * result + ((DATE_OF_BIRTH == null) ? 0 : DATE_OF_BIRTH.hashCode());
		result = prime * result + ((FIRST_NAME == null) ? 0 : FIRST_NAME.hashCode());
		result = prime * result + ((UUID == null) ? 0 : UUID.hashCode());
		result = prime * result + ((LAST_NAME == null) ? 0 : LAST_NAME.hashCode());
		result = prime * result + ((PASSPORT_NUMBER == null) ? 0 : PASSPORT_NUMBER.hashCode());
		result = prime * result + ((PLACE_OF_BIRTH == null) ? 0 : PLACE_OF_BIRTH.hashCode());
		result = prime * result + ((TITLE == null) ? 0 : TITLE.hashCode());
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
		Person other = (Person) obj;
		if (COUNTRY_OF_BIRTH == null) {
			if (other.COUNTRY_OF_BIRTH != null)
				return false;
		} else if (!COUNTRY_OF_BIRTH.equals(other.COUNTRY_OF_BIRTH))
			return false;
		if (DATE_OF_BIRTH == null) {
			if (other.DATE_OF_BIRTH != null)
				return false;
		} else if (!DATE_OF_BIRTH.equals(other.DATE_OF_BIRTH))
			return false;
		if (FIRST_NAME == null) {
			if (other.FIRST_NAME != null)
				return false;
		} else if (!FIRST_NAME.equals(other.FIRST_NAME))
			return false;
		if (UUID == null) {
			if (other.UUID != null)
				return false;
		} else if (!UUID.equals(other.UUID))
			return false;
		if (LAST_NAME == null) {
			if (other.LAST_NAME != null)
				return false;
		} else if (!LAST_NAME.equals(other.LAST_NAME))
			return false;
		if (PASSPORT_NUMBER == null) {
			if (other.PASSPORT_NUMBER != null)
				return false;
		} else if (!PASSPORT_NUMBER.equals(other.PASSPORT_NUMBER))
			return false;
		if (PLACE_OF_BIRTH == null) {
			if (other.PLACE_OF_BIRTH != null)
				return false;
		} else if (!PLACE_OF_BIRTH.equals(other.PLACE_OF_BIRTH))
			return false;
		if (TITLE == null) {
			if (other.TITLE != null)
				return false;
		} else if (!TITLE.equals(other.TITLE))
			return false;
		return true;
	}	
}