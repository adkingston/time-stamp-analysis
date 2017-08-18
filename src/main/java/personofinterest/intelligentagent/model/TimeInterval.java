package personofinterest.intelligentagent.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeInterval implements Comparable<TimeInterval>{
	
	public LocalDateTime START;
	public LocalDateTime END;
	
	public TimeInterval(LocalDateTime startTime, LocalDateTime endTime) {
		START = startTime;
		END = endTime;
	}
	
	public LocalDateTime getStartTime() {
		return START;
	}
	
	public LocalDateTime getEndTime() {
		return END;
	}
	
	public Duration length() {
		return Duration.between(START, END);
	}
	
	public String toString() {
		// time intervals are always closed!
		return '[' + START.toString() + ", " + END.toString() +"]";
	}
	
	public Boolean equals(TimeInterval time) {
		LocalDateTime startCompare = time.getStartTime();
		LocalDateTime endCompare = time.getEndTime();
		return START.equals(startCompare) && END.equals(endCompare);
	}
	
	public int compareTo(TimeInterval time) {
		// returns the first of the two intervals
		if (this.equals(time)) {
			return 0;
		} else if (START.isBefore(time.getStartTime())) {
			return 1;
		} else {
			return -1;
		}	
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((END == null) ? 0 : END.hashCode());
		result = prime * result + ((START == null) ? 0 : START.hashCode());
		return result;
	}
}