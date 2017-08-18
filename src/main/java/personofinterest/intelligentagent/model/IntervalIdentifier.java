package personofinterest.intelligentagent.model;

import java.time.LocalDateTime;

public class IntervalIdentifier implements Comparable<IntervalIdentifier> {
	
	public Person PERSON;
	public LocalDateTime TIME;
	public String IDENTIFIER;
	
	public IntervalIdentifier(Person person, LocalDateTime time, String id) {
		PERSON = person;
		TIME = time;
		IDENTIFIER = id;
	}
	
	public Person getPerson() {
		return PERSON;
	}
	
	public LocalDateTime getTime() {
		return TIME;
	}
	
	public String getID() {
		return IDENTIFIER;
	}

	public String toString() {
		return "[" + PERSON.getFirstName() + " " + PERSON.getLastName() + ", " + TIME + ", " + IDENTIFIER + "]"; 
	}
	
	public Boolean equals(IntervalIdentifier nextData) {
		LocalDateTime time2 = nextData.getTime();
		Person person = nextData.getPerson();
		return (TIME.equals(time2) && PERSON.equals(person));
	}
	
	public int compareTo(IntervalIdentifier nextData) {
		if (this.equals(nextData)){
			return 0;
		} else {
			LocalDateTime time2 = nextData.getTime();
			String id2 = nextData.getID();
			if (TIME.isBefore(time2)) {
				return -1;
			} else if (TIME.isAfter(time2)){
				return 1;
			} else {
				if (IDENTIFIER.contains("S")) {
					return -1;
				} else if (id2.contains("S")) {
					return 1;
				}
			}
		} 
		return -1;		
	}
}