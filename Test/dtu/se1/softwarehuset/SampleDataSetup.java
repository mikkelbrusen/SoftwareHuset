package dtu.se1.softwarehuset;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;

public class SampleDataSetup {
	Master m = new Master();
	Activity a;
	
	@Before
	public void setUp() {
		Project p = m.createProject("Project", new GregorianCalendar(2015, Calendar.JANUARY, 10));
		
		Calendar start = new GregorianCalendar(2015, Calendar.JANUARY, 10);
		Calendar end = new GregorianCalendar(2015, Calendar.JANUARY, 20);
		a = p.createActivity("Temp Act", 0, start, end);

	}

}
