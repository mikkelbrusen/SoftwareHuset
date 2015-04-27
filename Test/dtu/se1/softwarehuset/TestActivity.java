package dtu.se1.softwarehuset;


import static org.junit.Assert.assertEquals;

import java.nio.file.AccessDeniedException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class TestActivity extends SampleDataSetup{
	
	@Test
	public void testAddStaff() throws Exception {
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		m.logout();
		m.login(d1);
		p.becomeProjectLeader(1);
		
		a.addStaff(d2);
		
		assertEquals(1,a.getStaff().size());
	}
	
//	@Test
//	public void testAddStaffNotProjectLeader() {
//		
//	}
	
}
