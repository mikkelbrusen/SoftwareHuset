package dtu.se1.softwarehuset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Developer {
	
	private static int index = 0;
	protected int id;
	private List<Activity> activityList;
	private boolean available;
	private Map<Activity, Developer> requests;
	
	public Developer() {
		this.id += Developer.index++;
		activityList = new ArrayList<Activity>();
		this.available = true;
		requests = new HashMap<Activity, Developer>();
	}
	
	public boolean isAvailable() {
		return available && activityList.size() < 10;
	}
	
	public void setAvailable(boolean bool) {
		available = bool;
	}
	
	public int getRegisteredHours() {
		int hours = 0;
		for (Activity a: activityList) {
			hours += a.getDevRegisteredHours(this);
		}
		return hours;
	}
	
	public void registerActivity(Activity a) {
		activityList.add(a);
	}
	
	public int getId() {
		return id;
	}
	
	public void addRequest(Activity a, Developer d) {
		requests.put(a, d);
	}
	
	public Map<Activity, Developer> getRequests() {
		return requests;
	}

}
