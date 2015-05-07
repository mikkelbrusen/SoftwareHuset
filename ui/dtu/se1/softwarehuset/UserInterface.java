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
		System.out.println("Personal menu - 4");
		System.out.println("Logout - 5");
		System.out.println("Close application - 6");
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
			addDeveloper();
			break;
		case (4):
			personalMenu();
			break;
		case (5):
			m.logout();
			login();
			mainMenu();
			break;
		case (6):
			closeApp();
			break;
		default:
			System.out.println("Choice unavailable.");
			mainMenu();
		}
	}

	private void addDeveloper() {

		if (!m.getLogin().equals(m.getAdmin())) {
			System.out
					.println("Access denied!\nOnly admin is allowed to add users");
			mainMenu();
		}

		try {
			m.createDev();
			System.out.println("Successfully created developer");
			System.out.println("Total number of devlopers: "
					+ m.getDevs().size());
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}

		mainMenu();
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
			String dateString = new Date(startDate.getTimeInMillis())
					.toString();

			System.out.println("Success!");
			System.out.println("Created project \"" + p.getTitle()
					+ "\", with id " + p.getId());
			System.out.println("and start date at: " + dateString);
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
		System.out.println("Manage activities - 2");
		System.out.println("Become project leader - 3");
		System.out.println("Return to main menu - 4");

		int choice = userInputInt();
		switch (choice) {
		case (1):
			createActivity(p);
			break;
		case (2):
			accessActivities(p);
			break;
		case (3):
			becomeProjectLeader(p);
			break;
		case (4):
			mainMenu();
			break;
		default:
			System.out.println("Invalid choice:\nReturning to main menu");
			mainMenu();
		}

	}

	private void becomeProjectLeader(Project p) {

		if (p.getProjectLeader() != null) {
			System.out.println("A project leader is already assigned");
			manageProject(p);
		}

		try {
			p.becomeProjectLeader();
			System.out.println("Successfully assigned developer with id \""
					+ m.getLoginId() + "\" as project leader");
			manageProject(p);
		} catch (ActivityStaffException e) {
			e.printStackTrace();
		}

	}

	private void createActivity(Project p) {

		if (m.getLogin() != p.getProjectLeader()) {
			System.out
					.println("Access denied!\nOnly the project leader may create activities!");
			System.out.println("Returning to main menu");
			mainMenu();
		}

		System.out.println("Creating new activity for project \""
				+ p.getTitle() + "\":");

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

		Activity a;
		try {
			a = p.createActivity(title, ewh, startDate, endDate);

			System.out.println("---\nSuccess!");
			System.out.println("Created activity \"" + a.getTitle() + "\"");
			System.out.println("Start date: "
					+ new Date(startDate.getTimeInMillis()).toString());
			System.out.println("End date: "
					+ new Date(endDate.getTimeInMillis()).toString());
			System.out.println("---");

			manageActivity(a);
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
	}

	private void accessActivities(Project p) {

		System.out.println("Available activities:");
		System.out.println("--");

		for (Activity a : p.getActivities()) {
			System.out.println("\"" + a.getTitle() + "\" - Id: " + a.getId());
		}
		System.out.println("--");

		System.out.println("Enter \"0\" to return to main menu");
		System.out.println("Enter activity ID, to manage project");
		int activityId = userInputInt();

		if (activityId == 0) {
			mainMenu();
		}

		Activity a = p.getActivityById(activityId);
		while (!p.getActivities().contains(a)) {
			System.out.println("Activity does not exist\nTry again");
			a = p.getActivityById(userInputInt());
		}

		manageActivity(a);

	}

	private void manageActivity(Activity a) {
		System.out.println("Managing activity \"" + a.getTitle() + "\":");

		System.out.println("Options:");
		System.out.println("Register hours - 1");
		System.out.println("List staff - 2");
		System.out.println("Add staff (Project leader only) - 3");
		System.out.println("Request assistance (Staff only) - 4");
		System.out.println("Return to main menu - 5");

		int choice = userInputInt();
		switch (choice) {
		case (1):
			registerHours(a);
			break;
		case (2):
			listStaff(a);
			break;
		case (3):
			addStaff(a);
			break;
		case (4):
			// requestAssistance(a);
			break;
		case (5):
			mainMenu();
			break;
		default:
			System.out.println("Invalid choice:\nReturning to main menu");
			mainMenu();
		}

	}

	private void listStaff(Activity a) {

		System.out.println("Developers assigned to activity \"" + a.getTitle()
				+ "\":");
		System.out.println("---");
		for (Developer d : a.getStaff()) {
			System.out.println("Developer id: " + d.getId());
		}
		System.out.println("---");
		manageActivity(a);
	}

	private void addStaff(Activity a) {

		if (!m.getLogin().equals(a.getProject().getProjectLeader())) {
			System.out.println("Access denied");
			manageActivity(a);
		}

		System.out.println("List of all developers:\n---");
		for (Developer d : m.getDevs()) {
			System.out.println("Dev id: " + d.getId());
		}
		System.out.println("---");

		System.out.println("Choose developer by id");
		Developer d = m.getDevById(userInputInt());

		if (d == null) {
			System.out.println("Invalid developer id\nReturning to main menu");
			mainMenu();
		}
		while (!validateDev(d, a)) {
			System.out.println("Developer is not available"
					+ " or already assigned to this activity\nTry again:");
			d = m.getDevById(userInputInt());
		}

		try {
			a.addStaff(d);
			System.out.println("Successfully added developer with id \""
					+ d.getId() + "\"" + " to activity \"" + a.getTitle()
					+ "\"");
		} catch (ActivityStaffException e) {
			e.printStackTrace();
		}

		manageActivity(a);

	}

	private boolean validateDev(Developer d, Activity a) {
		return d.isAvailable() && !a.getStaff().contains(d);
	}

	private void registerHours(Activity a) {

		Developer user = m.getLogin();

		if (!a.getStaff().contains(user)) {
			System.out.println("You are not working on this activity");
			manageActivity(a);
		}

		System.out.println("How many hours do you wish to regiser?");
		int hours = userInputInt();

		try {
			a.registerHours(m.getLogin(), hours);
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
		System.out
				.println("Total number of hours on activity \"" + a.getTitle()
						+ "\": " + a.getDevRegisteredHours(m.getLogin()));

		manageActivity(a);

	}

	private void personalMenu() {
		Developer d = m.getLogin();

		System.out.println("Menu for developer with id \"" + d.getId()
				+ "\"\nOptions:");
		System.out.println("See registered hours - 1");
		System.out.println("See availablity status - 2");
		System.out.println("Change availability - 3");
		System.out.println("Main menu - 4");

		int choice = userInputInt();
		switch (choice) {
		case (1):
			devRegisteredHours(d);
			break;
		case (2):
			devAvail(d);
			break;
		case (3):
			changeAvail(d);
			break;
		case (4):
		default:
			System.out.println("Returning to main menu..");
			mainMenu();
		}

	}

	private void changeAvail(Developer d) {
		System.out.println("Choose new status:");
		System.out.println("Available - 1");
		System.out.println("Unavailable - 2");
		
		int choice = userInputInt();
		switch (choice) {
		case(1):
			devSetAvail(d, true);
			break;
		case(2):
			devSetAvail(d, false);
			break;
		default:
			System.out.println("Invalid input. Try again:");
			changeAvail(d);
		}
		
	}

	private void devSetAvail(Developer d, boolean b) {
		String status = (b) ? "Available": "Unavailable";
		if (d.isAvailable() == b) {
			System.out.println("Status is already set to "+status);
			personalMenu();
		}
		
		d.setAvailable(b);
		System.out.println("Successfully set status to: "+status);
		personalMenu();
		
	}

	private void devAvail(Developer d) {
		System.out.print("Status for developer with id \""+d.getId()+"\": ");
		
		String status = (d.isAvailable()) ? "Available": "Unavailable";
		
		System.out.print(status+" \n");
		personalMenu();
	}

	private void devRegisteredHours(Developer d) {
		System.out.println("Total number of registered hours for developer\n"
				+ "with id \"" + d.getId() + "\"");

		System.out.println("Hours: " + d.getRegisteredHours());
		personalMenu();
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
