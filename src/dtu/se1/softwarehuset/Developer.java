package dtu.se1.softwarehuset;

import java.nio.file.AccessDeniedException;
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
	private Master m;
	
	public Developer(Master m) {
		this.id += Developer.index++;
		activityList = new ArrayList<Activity>();
		this.available = true;
		requests = new HashMap<Activity, Developer>();
		this.m = m;
	}
	
	public boolean isAvailable() {
		return available && activityList.size() < 10;
	}
	
	public void setAvailable(boolean bool) throws AccessDeniedException {
		if (!isLoggedIn())
			throw new AccessDeniedException("Must be logged in to change available status");
		
		available = bool;
	}
	
	private boolean isLoggedIn() {
		return id == m.getLoginId();
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
	
	public void acceptRequest(Activity a, boolean b) {
		requests.remove(a);

		if (b) {
			a.addStaffRequest(this);
		}
	}

}
