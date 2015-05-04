package dtu.se1.softwarehuset;

import java.util.ArrayList;
import java.util.List;

public class Developer {
	
	private static int index = 0;
	protected int id;
	private List<Activity> activityList;
	
	public Developer() {
		this.id += Developer.index++;
		activityList = new ArrayList<Activity>();
	}
	
	public int getRegisteredHours() {
		int hours = 0;
		for (Activity a: activityList) {
			hours += a.getRegisteredHours();
		}
		return hours;
	}
	
	public void registerActivity(Activity a) {
		activityList.add(a);
	}
	
	public int getId() {
		return id;
	}

}
