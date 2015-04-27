package dtu.se1.softwarehuset;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Master {
	private List<Project> projectList;
	private List<Developer> developerList;
	private Developer admin;
	private int loginId;
	
	public Master() {
		projectList = new ArrayList<Project>();
		developerList = new ArrayList<Developer>();
		admin = new Admin();
		loginId = -1;
	}

	public Project createProject(String title, Calendar startDate) throws AccessDeniedException {
		if (getLoginId() != 0) {
			throw new AccessDeniedException("You are not an administrator");
		}
		Project project = new Project(title, startDate);
		projectList.add(project);

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
	
	public Developer getAdmin() {
		return admin;
	}

	public void login(Developer dev) {
		loginId = dev.getId();
	}
	
	public void logout() {
		loginId = -1;
	}
	
	public Integer getLoginId() {
		return loginId;
	}
	
}
