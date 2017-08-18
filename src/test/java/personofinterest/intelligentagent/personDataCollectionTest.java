package personofinterest.intelligentagent;


import org.junit.Before;
import org.junit.Test;

import personofinterest.intelligentagent.model.Person;
import personofinterest.intelligentagent.model.TravelHistory;

public class personDataCollectionTest {
//	private Person person;
//	private TravelHistory travelHistory;
//	private DurationStatisticsCalculator calculator;
	private PersonDAO personDAO;
	
	@Before
	public void beforeTest() {
		personDAO = new PersonDAOImpl(); 
	}
	
	@Test
	public void dataGatheringTest() {
		for (Person person : personDAO.getAllPersons()) {
			System.out.println(person.toString());
			TravelHistory hist = person.getTravelHistory();
			hist.showTravelHistory();
		}
	}
	
//	@Test 
//	public void calculatorTest() {
//		calculator.showAvgTimeAway();
//		calculator.showStDevAway();
//		System.out.println(calculator.awayCount());
//		calculator.showAvgTimeHere();
//		calculator.showStDevHere();
//		System.out.println(calculator.hereCount());
//		calculator.showAvgTimeUnknown();
//		calculator.showStDevUnknown();
//		System.out.println(calculator.unknownCount());
//	}
}

