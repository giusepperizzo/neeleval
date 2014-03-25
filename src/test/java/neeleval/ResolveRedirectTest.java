package neeleval;

import junit.framework.TestCase;

public class ResolveRedirectTest extends TestCase {
	
	public void testRedirect() {
		ResolveRedirect rr = new ResolveRedirect();
		Pair p = rr.resolveRedirect(new Pair("Test", "http://dbpedia.org/resource/1._FC_Cologne"));		
		assertEquals("http://dbpedia.org/resource/1._FC_K%C3%B6ln", p.getURL().toString());
	}
}
