package dtu.se1.softwarehuset;

import java.nio.file.AccessDeniedException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class UserInterface {

	private Scanner sc;
	private Master m;
	
	private static final String REGEX_TITLE = "^(\\w|\\d){1,}(\\w|\\d| ){1,}$";
	private static final String REGEX_DATE = "^\\d{4}/\\d{2}/\\d{2}$";

	public static void main(String[] args) {
		new UserInterface();
	}

	public UserInterface() {
		m = new Master();
		login();
		mainMenu();
		
	}

	private void login() {
		System.out.println("To close the program, enter id \"-1\"");
		System.out.println("Enter your ID:");
		Developer loginDev;
		int id = userInputInt();

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
			id = userInputInt();

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
		int choice = userInputInt();
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
		Developer user = m.getLogin();
		
		if (user != m.getAdmin()) {
			System.out.println("Access denied!\nReturning to main menu");
			mainMenu();
		}
		
		System.out.println("Creating new project..");
		System.out.println("Enter project title:");
		String title = userInputString(REGEX_TITLE);

		System.out.println("Enter start date for the project");
		System.out.println("Format: \"yyyy/mm/dd\"");
		String date = userInputString(REGEX_DATE);

		Calendar startDate = createCalendarFromString(date);

		try {
			Project p = m.createProject(title, startDate);
			String dateString = new Date(startDate.getTimeInMillis()).toString();
			
			
			System.out.println("Success!");
			System.out.println("Created project \"" + p.getTitle()
					+ "\", with id " + p.getId());
			System.out.println("and start date at: "+dateString);
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}

		mainMenu();
	}

	private Calendar createCalendarFromString(String str) {
		String[] arr = str.split("/");

		int year = Integer.parseInt(arr[0]);
		int month = Integer.parseInt(arr[1]) - 1; // Offset for zero-indexing
		int day = Integer.parseInt(arr[2]);

		return new GregorianCalendar(year, month, day);
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
			System.out.println("\"" + p.getTitle() + "\" - Id: " + p.getId());
		}
		System.out.println("--");

		System.out.println("Enter \"0\" to return to main menu");
		System.out.println("Enter project ID, to manage project");
		int projectId = userInputInt();

		if (projectId == 0) {
			mainMenu();
		}

		Project p = m.getProjectById(projectId);
		while (!m.getProjects().contains(p)) {
			System.out.println("Project does not exist\nTry again");
			p = m.getProjectById(userInputInt());
		}

		manageProject(p);
	}

	private void manageProject(Project p) {

		System.out.println("Accessing Project \"" + p.getTitle() + "\"..");

		System.out.println("Options:");
		System.out.println("Create activity - 1");
		System.out.println("Return to main menu -2");

		int choice = userInputInt();
		switch (choice) {
		case(1):
			createActivity(p);
			break;
		case(2):
			mainMenu();
			break;
		default:
			System.out.println("Invalid choice:\nReturning to main menu");
			mainMenu();
		}
		
	}

	private void createActivity(Project p) {
		
		if (m.getLogin() != p.getProjectLeader()) {
			System.out.println("Access denied!\nOnly the project leader may create activities!");
			System.out.println("Returning to main menu");
			mainMenu();
		}
		
		System.out.println("Creating new activity for project \""+p.getTitle()+"\":");
		
		System.out.println("Enter title:");
		String title = userInputString(REGEX_TITLE);
		
		System.out.println("Enter expected work hours");
		int ewh = userInputInt();

		System.out.println("Enter start date:");
		System.out.println("Format: \"yyyy/mm/dd\"");
		Calendar startDate = createCalendarFromString(userInputString(REGEX_DATE));

		System.out.println("Enter end date:");
		System.out.println("Format: \"yyyy/mm/dd\"");
		Calendar endDate = createCalendarFromString(userInputString(REGEX_DATE));
		
		Activity a = p.createActivity(title, ewh, startDate, endDate);
		
		System.out.println("---\nSuccess!");
		System.out.println("Created activity \""+a.getTitle()+"\"");
		System.out.println("Start date: "+new Date(startDate.getTimeInMillis()).toString());
		System.out.println("End date: "+new Date(endDate.getTimeInMillis()).toString());
		System.out.println("---");
		
		mainMenu();
	}

	private void closeApp() {
		System.out.println("Closing Aplication...");
		System.exit(0);
	}

	private int userInputInt() {
		sc = new Scanner(System.in);
		String inp = sc.nextLine();
		int ret;
		try {
			ret = Integer.parseInt(inp);
			return ret;
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Try again:");
			return userInputInt();
		}

	}

	private String userInputString(String regex) {
		sc = new Scanner(System.in);
		String str = sc.nextLine();
		if (regex == null)
			return str;

		if (str.matches(regex)) {
			return str;
		} else {
			System.out.println("Invalid input. Try again:");
			return userInputString(regex);
		}

	}

}
