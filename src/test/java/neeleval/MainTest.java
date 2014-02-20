package neeleval;

import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTest extends TestCase {
		
	public void testEval1() {
		
		List<Pair> gs_pairs = new ArrayList<Pair>();
		gs_pairs.add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
		gs_pairs.add(new Pair("2012 US Election", "http://dbpedia.org/resource/United_States_presidential_election,_2012"));
		gs_pairs.add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
		gs_pairs.add(new Pair("Harward", "http://dbpedia.org/resource/Harvard_University"));
		
		List<Pair> ts_pairs = new ArrayList<Pair>();
		ts_pairs.add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
		ts_pairs.add(new Pair("2012 US Election", "http://dbpedia.org/resource/United_States_presidential_election,_2012"));
		ts_pairs.add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
		ts_pairs.add(new Pair("Harward", "http://dbpedia.org/resource/Harvard_University"));
		
		
		Map<String, List<Pair>> gs = new HashMap<String, List<Pair>>();
		gs.put("1", gs_pairs);
		Map<String, List<Pair>> ts = new HashMap<String, List<Pair>>();
		ts.put("1", ts_pairs);
		
		Main main = new Main();
		main.eval(gs, ts);
		double expected = 1;
		
		double actual = main.precision();
		assertEquals(expected, actual, 1);
		
		actual = main.recall();
		assertEquals(expected, actual, 1);
		
		actual = main.F1();
		assertEquals(expected, actual, 1);
	}
	
	public void testEval2() {
		
		Map<String, List<Pair>> gs = new HashMap<String, List<Pair>>() {{
            put("1", new ArrayList<Pair>() {{
                add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
                add(new Pair("2012 US Election", "http://dbpedia.org/resource/United_States_presidential_election,_2012"));
                add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
                add(new Pair("Harward", "http://dbpedia.org/resource/Harvard_University"));
            }});
        }};

		Map<String, List<Pair>> ts = new HashMap<String, List<Pair>>() {{
            put("1", new ArrayList<Pair>() {{
                add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
                add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
                add(new Pair("2012 US Election", "http://dbpedia.org/resource/United_States_presidential_election,_2012"));
                add(new Pair("Harward", "http://dbpedia.org/resource/Harvard_University"));
            }});
        }};

		Main main = new Main();
		main.eval(gs, ts);

		assertEquals(0.75, main.precision(), 0.001);
		assertEquals(0.75, main.recall(), 0.001);
		assertEquals(0.75, main.F1(), 0.001);
	}

	public void testEval3() {

        Map<String, List<Pair>> gs = new HashMap<String, List<Pair>>() {{
            put("1", new ArrayList<Pair>() {{
                add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
                add(new Pair("2012 US Election", "http://dbpedia.org/resource/United_States_presidential_election,_2012"));
                add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
                add(new Pair("Harward", "http://dbpedia.org/resource/Harvard_University"));
            }});
        }};

        Map<String, List<Pair>> ts = new HashMap<String, List<Pair>>() {{
            put("1", new ArrayList<Pair>() {{
                add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
                add(new Pair("2012", "http://dbpedia.org/resource/2012"));
                add(new Pair("US", "http://dbpedia.org/page/United_States"));
                add(new Pair("Election", "http://dbpedia.org/resource/Election"));
                add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
                add(new Pair("Harward", "http://dbpedia.org/resource/Harvard_University"));
            }});
        }};
		
		Main main = new Main();
		main.eval(gs, ts);

		assertEquals(0.5, main.precision(), 0.001);
		assertEquals(0.75, main.recall(), 0.001);
		assertEquals(0.6, main.F1(), 0.001);
	}
	
	public void testRead() {
		Main main = new Main();
		try {
			HashMap<String,List<Pair>> set = main.readSet("utils/test.tsv");
			assertEquals(1, set.size());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void testMain() {
		Main main = new Main();
		String filenames[] = {"utils/gs.tsv","utils/test5.tsv"};
		main.main(filenames);
	}
	
	public void testURI () {
		URL actual;
		try {
		  //url1 = URL.parse("http://dbpedia.org/resource/Olivia_\"Bong\"_Coo");
		   actual = new URL("http://dbpedia.org/resource/Olivia_\"Bong\"_Coo");
 		   String expected = "http://dbpedia.org/resource/Olivia_\"Bong\"_Coo";
 		   assertEquals(expected, actual);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
}
