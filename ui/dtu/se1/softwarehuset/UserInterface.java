package dtu.se1.softwarehuset;

import java.nio.file.AccessDeniedException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class UserInterface {

	private Scanner sc;
	private Master m;

	public static void main(String[] args) {
		new UserInterface();
	}

	public UserInterface() {
		sc = new Scanner(System.in);
		m = new Master();
		login();
		mainMenu();
	}

	private void login() {
		System.out.println("To close the program, enter id \"-1\"");
		System.out.println("Enter your ID:");
		Developer loginDev;
		int id = Integer.parseInt(sc.nextLine());

		if (id == 0) {
			loginDev = m.getAdmin();
		} else {
			loginDev = m.getDevById(id);
		}

		while (loginDev == null) {
			if (id == -1) {
				closeApp();
			}
			System.out.println("User does not exist.\nEnter another ID");
			id = Integer.parseInt(sc.nextLine());

			if (id == 0) {
				loginDev = m.getAdmin();
			} else {
				loginDev = m.getDevById(id);
			}
		}
		m.login(loginDev);
		System.out.println("Logged in as ID: " + loginDev.getId());
	}

	private void mainMenu() {
		System.out.println("-----");
		System.out.println("Main Menu");
		System.out.println("Access projects - 1");
		System.out.println("Create project (Admin only) - 2");
		System.out.println("Add new developer (Admin only) - 3");
		System.out.println("Logout - 4");
		System.out.println("Close application - 5");
		System.out.println("-----");
		int choice = Integer.parseInt(sc.nextLine());
		switch (choice) {
		case (1):
			accessProjects();
			break;
		case (2):
			createProject();
			break;
		case (3):
			break;
		case (4):
			m.logout();
			login();
			mainMenu();
			break;
		case (5):
			closeApp();
			break;
		default:
			System.out.println("Choice unavailable.");
			mainMenu();
		}
	}

	private void createProject() {
		System.out.println("Creating new project..");
		System.out.println("Enter project title:");
		String title = sc.nextLine();
		while (!validateTitle(title)) {
			System.out.println("Title is not valid\nTry again:");
			title = sc.nextLine();
		}
		System.out.println("Enter start date for the project");
		System.out.println("Format: \"yyyy/mm/dd\"");
		String date = sc.nextLine();
		while (!validateDate(date)) {
			System.out.println("Date is not valid\nTry again:");
			date = sc.nextLine();
		};
		
		Calendar startDate = createCalendarFromString(date);
		
		try {
			Project p = m.createProject(title, startDate);
			
			System.out.println("Success!");
			System.out.println("Created project \""+p.getTitle()+"\", with id "+p.getId());
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
		
		mainMenu();		
	}

	private boolean validateTitle(String title) {
		return title.matches("^(\\w|\\d){1,}(\\w|\\d| ){1,}$");
	}

	private boolean validateDate(String date) {
		String regExp = "^\\d{4}/\\d{2}/\\d{2}$";
		return date.matches(regExp);
	}
	
	public Calendar createCalendarFromString(String str) {
		String[] arr = str.split("/");
		return new GregorianCalendar(
					Integer.parseInt(arr[0]),
					Integer.parseInt(arr[1])-1,
					Integer.parseInt(arr[2])
				);
	}

	private void accessProjects() {
		System.out.println("Available projects:");
		if (m.getProjects().size() == 0) {
			System.out.println("No projects registered.");
			System.out.println("Returning to main menu..");
			mainMenu();
			return;
		}
		System.out.println("--");
		for (Project p : m.getProjects()) {
			System.out.println("\""+p.getTitle() + "\" - Id: " + p.getId());
		}
		System.out.println("--");
		
		System.out.println("Enter \"0\" to return to main menu");
		System.out.println("Enter project ID, to manage project");
		int projectId = Integer.parseInt(sc.nextLine());
		
		if (projectId == 0) {
			mainMenu();
		}
		
		Project p = m.getProjectById(projectId);
		while (!m.getProjects().contains(p)) {
			System.out.println("Project does not exist\nTry again");
			p = m.getProjectById(Integer.parseInt(sc.nextLine()));
		}
		
		System.out.println("Accessing Project \""+p.getTitle()+"\"");
//		manageProject();
	}

	private void closeApp() {
		System.out.println("Closing Aplication...");
		System.exit(0);
	}

}
