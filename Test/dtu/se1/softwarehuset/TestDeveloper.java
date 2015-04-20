package dtu.se1.softwarehuset;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestDeveloper  extends SampleDataSetup{
	
	@Test
	public void testCreateDeveloper() {
		
		Developer d = m.createDev();
		assertEquals(1, m.getDevs().size());
		assertEquals(1, d.getId());
	}
	
	@Test
	public void testRegisterHours() {
		
		Developer d = m.createDev();
		
		a.registerHours(d, 10);
		assertEquals(10, d.getRegisteredHours());
		assertEquals(10, a.getRegisteredHours());
		
	}
	
	@Test
	public void testRegisterHoursMultiple() {		
		Developer d = m.createDev();

		a.registerHours(d, 10);
		a.registerHours(d, 5);
		
		assertEquals(15, d.getRegisteredHours());
		assertEquals(15, a.getRegisteredHours());
	}

}
