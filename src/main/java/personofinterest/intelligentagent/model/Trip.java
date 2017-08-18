package personofinterest.intelligentagent.model;

import java.time.LocalDateTime;

public class Trip {
	
	public String TRIP_TYPE;
	public LocalDateTime TIME_STAMP;
	
	public Trip(String type, LocalDateTime time) {
		TRIP_TYPE = type;
		TIME_STAMP = time;
	}
	
	public String getTravelType() {
		return TRIP_TYPE;
	}
	
	public LocalDateTime getTimeStamp() {
		return TIME_STAMP;
	}
	
	// not sure if useful. Will see. 
	public void get(int i) {
		if (i==0) {
			getTravelType();
		} else if (i==1) {
			getTimeStamp(); 
		} else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public Boolean equals(Trip trip) {
		return TRIP_TYPE.equals(trip.getTravelType()) && TIME_STAMP.equals(trip.getTimeStamp());
	}
	
	public int direction(Trip trip) {
		String nextType = trip.getTravelType();
		if (TRIP_TYPE.equals("Arrived") && nextType.equals("Departed")) {
			return -1;
		} else if (TRIP_TYPE.equals("Departed") && nextType.equals("Arrived")) {
			return 1; 
		} else {
			return 0;
		}
	}
	
	public String toString() {
		return TRIP_TYPE + " at " + TIME_STAMP;
	}
	
}