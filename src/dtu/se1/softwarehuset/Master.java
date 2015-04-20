package dtu.se1.softwarehuset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Master {
	private List<Project> projectList;
	private List<Developer> developerList;
	
	public Master() {
		projectList = new ArrayList<Project>();
		developerList = new ArrayList<>();
	}
	
	public Project createProject(String title, Calendar startDate) {
		projectList.add(new Project(title, startDate));
		System.out.print("The project has been created\n");
		return projectList.get(projectList.size()-1);
	}
	
	public Developer createDev() {
		developerList.add(new Developer());
		return developerList.get(developerList.size()-1);	
	}
	
	public List<Developer> getDevs() {
		return developerList;
	}
	
	public List<Project> getProjects() {
		return projectList;
	}
	
}
