package dtu.se1.softwarehuset;

import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Master {
	private List<Project> projectList;
	private List<Developer> developerList;
	private Developer admin;
	private Developer devLoggedIn;
	
	public Master() {
		projectList = new ArrayList<Project>();
		developerList = new ArrayList<Developer>();
		admin = new Admin(this);
		devLoggedIn = null;
	}

	public Project createProject(String title, Calendar startDate) throws AccessDeniedException, AlreadyExistingException {
		if (!isAdmin()) {
			throw new AccessDeniedException("You are not an administrator");
		}
		
		Project project = new Project(this, title, startDate);
		if (alreadyExists(project)){
			throw new AlreadyExistingException("The project already exists");
		}
		
		if (nameIsEmpty(project)){
			throw new InvalidParameterException("Project must have a non-empty name");
		}
		
		projectList.add(project);
		return projectList.get(projectList.size()-1);
	}
	
	private boolean nameIsEmpty(Project project) {
		return project.getTitle().equals("");
	}

	private boolean alreadyExists(Project newProject) {
		for (Project p: projectList){
			if (newProject.getTitle().equals(p.getTitle())){
				return true;
			}
		}
		return false;
	}

	public boolean isAdmin() {
		return getAdmin().equals(getLogin());
	}
	
	public Developer createDev() throws AccessDeniedException {
		if (getLoginId() != 0){
			throw new AccessDeniedException("You are not an administrator");
		}
		developerList.add(new Developer(this));
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
		devLoggedIn = dev;
	}
	
	public void logout() {
		devLoggedIn = null;
	}
	
	public int getLoginId() {
		return (devLoggedIn==null) ? -1: devLoggedIn.getId();
	}
	
	public Developer getLogin() {
		return devLoggedIn;
	}
	
	public Developer getDevById(int id) {
		for (Developer dev : developerList){
			if (dev.getId() == id){
				return dev;
			}
		}
		return null;
	}

	public Project getProjectById(int id) {
		for (Project p : projectList){
			if (p.getId() == id){
				return p;
			}
		}
		return null;
	}
	
}
