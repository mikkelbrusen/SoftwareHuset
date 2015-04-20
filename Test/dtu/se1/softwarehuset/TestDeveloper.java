package dtu.se1.softwarehuset;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestDeveloper  extends SampleDataSetup{
	
	@Test
	public void testCreateDeveloper() {
		
		m.createDev();
		assertEquals(1, m.getDevs().size());
	}
	
	@Test
	public void testRegisterHours() {
		
		Developer d = m.createDev();
		
		a.registerHours(d, 10);
		assertEquals(10, d.getRegisteredHours());
		assertEquals(10, a.getRegisteredHours());
		
	}

}
