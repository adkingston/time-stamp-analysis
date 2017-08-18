package personofinterest.intelligentagent;

import java.util.ArrayList;

import personofinterest.intelligentagent.model.Person;
import personofinterest.intelligentagent.model.TravelHistory;

public interface PersonDAO {
	
	public ArrayList<Person> getAllPersons();
	public ArrayList<Person> getSomePersons(ArrayList<String> ID);
	public TravelHistory getPersonTravelHistory(Person person);
	public Person getPerson(String ID);
	
}