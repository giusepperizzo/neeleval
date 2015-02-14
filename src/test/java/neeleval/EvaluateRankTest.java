package neeleval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import junit.framework.TestCase;

public class EvaluateRankTest extends TestCase {

	public void testEval1() {		
		Map<String, List<Pair>> gs = new HashMap<String, List<Pair>>() {{
            put("1", new ArrayList<Pair>() {{
            	add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
            	add(new Pair("2012 US Election", "http://dbpedia.org/resource/United_States_presidential_election,_2012"));
        		add(new Pair("Obama", "http://dbpedia.org/resource/Barack_Obama"));
        		add(new Pair("Harward", "http://dbpedia.org/resource/Harvard_University"));
            }});
        }};
		
		Vector<String> urls = new Vector<String>();
		urls.add("nil");
		urls.add("http://dbpedia.org/resource/Barack_Obama");
		List<PairRank> ts_pairs = new ArrayList<PairRank>();
		ts_pairs.add(new PairRank("Obama", urls));
		Map<String, List<PairRank>> ts = new HashMap<String, List<PairRank>>() {{
            put("1", new ArrayList<PairRank>() {{
            	Vector<String> urls = new Vector<String>();
        		urls.add("http://dbpedia.org/resource/Barack_Obama");
        		urls.add("nil");
        		List<PairRank> ts_pairs = new ArrayList<PairRank>();
        		ts_pairs.add(new PairRank("Obama", urls));
        		
                add(new PairRank("Obama", urls));
            }});
        }};

		EvaluateRank main = new EvaluateRank();
		main.eval(gs, ts, 2);

		double actual = main.precision();		
		assertEquals(100.0, actual);
		
		actual = main.recall();
		assertEquals(25.0, actual);
		
		actual = main.F1();
		assertEquals(40.0, actual);
	}
	
	public void testEval2() {
		String[] args = {"utils/gs.tsv", "utils/test6.tsv", "2"};
		EvaluateRank.main(args);
	}
	
}
