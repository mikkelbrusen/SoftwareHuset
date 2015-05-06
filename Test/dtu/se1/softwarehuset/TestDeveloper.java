package dtu.se1.softwarehuset;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestDeveloper  extends SampleDataSetup{
	
	@Test
	public void testCreateDeveloper() throws Exception {
		
		Developer d = m.createDev();
		assertEquals(1, m.getDevs().size());
	}
	
	@Test
	public void testRegisterHours() throws Exception {
		Developer d = m.createDev();
		Developer pl = m.createDev();
		m.logout();
		m.login(pl);
		p.becomeProjectLeader();
		a.addStaff(d);
		
		a.registerHours(d, 10);
		assertEquals(10, d.getRegisteredHours());
		assertEquals(10, a.getRegisteredHours());
		
	}
	
	@Test
	public void testRegisterHoursMultiple() throws Exception {		
		Developer d = m.createDev();
		Developer pl = m.createDev();
		m.logout();
		m.login(pl);
		p.becomeProjectLeader();
		a.addStaff(d);

		a.registerHours(d, 10);
		a.registerHours(d, 5);
		
		assertEquals(15, d.getRegisteredHours());
		assertEquals(15, a.getRegisteredHours());
	}
	
	@Test
	public void testRegisterHoursMultipleDevs() throws Exception {
		Developer pl = m.createDev();
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		m.logout();
		m.login(pl);
		p.becomeProjectLeader();
		a.addStaff(d1);
		a.addStaff(d2);
		
		a.registerHours(d1, 10);
		a.registerHours(d2, 10);
		
		assertEquals(20, a.getRegisteredHours());
		
	}

}
