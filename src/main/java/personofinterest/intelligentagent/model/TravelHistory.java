package personofinterest.intelligentagent.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;


public class TravelHistory {
	
	public ArrayList<Trip> TRAVEL_HISTORY;
	public Person PERSON;
	private Map<String, ArrayList<TimeInterval>> DURATIONS = new HashMap<String, ArrayList<TimeInterval>>();
	
	public TravelHistory(Person person) {
		this.PERSON = person;
		this.TRAVEL_HISTORY = new ArrayList<Trip>();
	}
	
	public int size() {
		return this.TRAVEL_HISTORY.size();
	}
	
	public void add(Trip element) {
		this.TRAVEL_HISTORY.add(element);
	}
	
	public void showTravelHistory() {
		for (int i=0; i<this.TRAVEL_HISTORY.size(); i++) {
			System.out.println(this.TRAVEL_HISTORY.get(i).toString());
		}
	}
	
	public Map<String, ArrayList<TimeInterval>> getDurations() {
		ArrayList<TimeInterval> timeAway = new ArrayList<TimeInterval>();
		ArrayList<TimeInterval> timeHere = new ArrayList<TimeInterval>();
		ArrayList<TimeInterval> unknownDurations = new ArrayList<TimeInterval>(); 
		TimeInterval duration;	
		for (int i=0; i<this.TRAVEL_HISTORY.size()-1; i++) {
			Trip trip0 = this.TRAVEL_HISTORY.get(i);
			Trip trip1 = this.TRAVEL_HISTORY.get(i+1);
			int directionOfTravel = trip0.direction(trip1);
			LocalDateTime time0 = trip0.getTimeStamp();		
			LocalDateTime time1 = trip1.getTimeStamp();
			duration =  new TimeInterval(time0, time1);
			if (directionOfTravel == -1) { // -1 => Arrival - Departure
				timeHere.add(duration);
			} else if (directionOfTravel == 1) { // 1 => Departure - Arrival
				timeAway.add(duration);
			} else {
				unknownDurations.add(duration);
			}
		}
		this.DURATIONS.put("Here", timeHere);
		this.DURATIONS.put("Away", timeAway);
		this.DURATIONS.put("Unknown", unknownDurations);
		return this.DURATIONS;
	}
	
	public ArrayList<TimeInterval> getDurationsHere() {
		if (DURATIONS.size() == 0) {
			getDurations();
		}
		return this.DURATIONS.get("Here");
	}
	
	public ArrayList<TimeInterval> getDurationsAway() {
		if (DURATIONS.size() == 0) {
			getDurations();
		}
		return this.DURATIONS.get("Away");
	}
	
	public ArrayList<TimeInterval> getDurationsUnknown() {
		if (DURATIONS.size() == 0) {
			getDurations();
		}
		return this.DURATIONS.get("Unknown");
	}
	
	public TimeInterval getShortestDuration(ArrayList<TimeInterval> times) {
		ArrayList<TimeInterval> newTimes = times; // make copy
		Collections.sort(newTimes);
		return newTimes.get(0);
	}
	
	public TimeInterval getLongestDuration(ArrayList<TimeInterval> times) {
		ArrayList<TimeInterval> newTimes = times; // make copy
		Collections.sort(newTimes);
		return newTimes.get(newTimes.size());
	}
	
	public ArrayList<IntervalIdentifier> getIntervalIdentifier(ArrayList<TimeInterval> times) {
		ArrayList<IntervalIdentifier> triples = new ArrayList<IntervalIdentifier>();
		for (TimeInterval T : times) {
			LocalDateTime start = T.getStartTime();
			LocalDateTime end = T.getEndTime();
			IntervalIdentifier pTI1 = new IntervalIdentifier(this.PERSON, start, "Start");
			IntervalIdentifier pTI2 = new IntervalIdentifier(this.PERSON, end, "End");
			triples.add(pTI1);
			triples.add(pTI2);
		}
		return triples;
	}
	
	public ArrayList<IntervalIdentifier> getIntervalDataHere(){
		ArrayList<TimeInterval> here = getDurationsHere();
		return getIntervalIdentifier(here);
	} 
	
	public ArrayList<IntervalIdentifier> getIntervalDataAway(){
		ArrayList<TimeInterval> away = getDurationsAway();
		return getIntervalIdentifier(away);
	}
	
	public ArrayList<IntervalIdentifier> getIntervalDataUnknown(){
		ArrayList<TimeInterval> unknown = getDurationsUnknown();
		return getIntervalIdentifier(unknown);
	}

}