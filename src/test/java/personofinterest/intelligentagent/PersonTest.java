package personofinterest.intelligentagent;

//import java.time.Duration;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import personofinterest.intelligentagent.model.Person;
import personofinterest.intelligentagent.model.IntervalIdentifier;
import personofinterest.intelligentagent.model.TimeInterval;
import personofinterest.intelligentagent.model.TravelHistory;


public class PersonTest {
	
//	private String ID1;
//	private String ID2;
//	private String ID3;
	private ArrayList<Person> people;
	private PersonDAO dao;
	
	@Before
	public void beforeTest() {
		dao = new PersonDAOImpl();
//		ID1 = "Yasmin";
//		ID2 = "6f22bd61-0df0-33e0-adcf-bb0aa2ae9a1a";
//		ID3 = "David";
//		ArrayList<String> IDs = new ArrayList<String>(Arrays.asList(ID1, ID3));
		people = dao.getAllPersons();
	}
//	@Test
//	public void detailsTest() {
//		for (Person p : people) {
//			System.out.println(p.toString());
//		}
//	}
	
	@Test
	public void collectionIntersectionTest() {
		ArrayList<TravelHistory> histories = new ArrayList<TravelHistory>();
		for (Person p : people) {
			histories.add(p.getTravelHistory());
		}
		ArrayList<ArrayList<IntervalIdentifier>> awayIdentifiers = new ArrayList<ArrayList<IntervalIdentifier>>();
		for (TravelHistory h : histories) {
			awayIdentifiers.add(h.getIntervalDataHere());
			System.out.println(h.PERSON.toString());
			System.out.println(h.getDurationsHere().toString());
		}
		Map<Set<String>, List<TimeInterval>> intersection = Intersection.getIntersection(awayIdentifiers);
		System.out.println(intersection.toString());
		Map<Set<String>, Map<String, String>> analysis = Intersection.doAnalysis(intersection);
		System.out.println(analysis.toString());
		
	}
//	
//	@Test
//	public void showTHTest() {
//		TravelHistory hist1 = person1.getTravelHistory();
//		hist1.showTravelHistory();
//	}
}