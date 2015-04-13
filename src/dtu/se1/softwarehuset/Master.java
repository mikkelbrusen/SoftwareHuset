package dtu.se1.softwarehuset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Master {
	private List<Project> projectList;
	
	public Master() {
		projectList = new ArrayList<Project>();
	}
	
	public void createProject(String title, Calendar startDate) {
		projectList.add(new Project(title, startDate));
		System.out.print("The project has been created");
	}
	
	public List<Project> getProjects() {
		return projectList;
	}
	
}
