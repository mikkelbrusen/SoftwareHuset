package dtu.se1.softwarehuset;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;

public class SampleDataSetup {
	Master m = new Master();
	Activity a;
	Project p;
	Calendar start, end;
	
	@Before
	public void setUp() throws Exception {
		m.login(m.getAdmin());
		
		p = m.createProject("Project", new GregorianCalendar(2015, Calendar.JANUARY, 10));
		
		start = new GregorianCalendar(2015, Calendar.JANUARY, 10);
		end = new GregorianCalendar(2015, Calendar.JANUARY, 20);
		a = p.createActivity("Temp", 0, start, end);

	}
	
	@After 
	public void cleanUp() {
		m.logout();
	}

}
