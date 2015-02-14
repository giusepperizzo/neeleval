package neeleval;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class EvaluateRank {
    
    private int no_tweets = 0;
    private int no_pairs = 0;
    
    private int no_correct = 0;
    private int no_found = 0;
    
    void eval(Map<String, List<Pair>> gs, Map<String, List<PairRank>> ts, int atRank) 
    {
        no_tweets = gs.size();
                
        for(Entry<String,List<Pair>> e : gs.entrySet() ) 
        {       	
            String gs_tweetid = e.getKey();
            List<Pair> gs_pairs = e.getValue(); 
            
            no_pairs += gs_pairs.size();
            
            if ( ts.containsKey(gs_tweetid) )
            {
                List<PairRank> ts_pairs = ts.get(gs_tweetid);
                no_found += ts_pairs.size();
                
                no_correct += longestOrderedCommonSubsequenceRank(gs_pairs, ts_pairs, atRank);   
            }
        }
    }
    
    int longestOrderedCommonSubsequenceRank(List<Pair> gs_pairs, List<PairRank> ts_pairs, int atRank) 
    {
      int order = 0;
      int correct = 0;
      boolean found;
      for (int i = 0; i< ts_pairs.size(); i++) {
      	found = false;
      	for (int j=order; j< gs_pairs.size() && !found; j++) 
      	{
      		if(gs_pairs.get(i).getEntityMention().equals( ts_pairs.get(i).getEntityMention() )
      		   &&
      		   contains(gs_pairs.get(i).getURL().toString(), ts_pairs.get(i).getUrls(), atRank) ) {
      			order = j+1;
	      		found = true;
	      		correct += 1;
      		}
      	}
      }
      return correct;
	}

	private boolean contains(String url, Vector<String> urls, int atRank) {
		for (int i = 0; i<urls.size() && i<atRank; i++){
			System.out.println(urls.get(i));
			
			if(url.equals(urls.get(i)))
				return true;
		}
		return false;
	}


    int longestCommonSubsequence(List<Pair> gs_pairs, List<Pair> ts_pairs) {
        if (gs_pairs.size() == 0 || ts_pairs.size() == 0)
            return 0;
        if (gs_pairs.get(0).equals(ts_pairs.get(0)))
            return 1 + longestCommonSubsequence(gs_pairs.subList(1, gs_pairs.size()), ts_pairs.subList(1, ts_pairs.size()));
        return Math.max(
                longestCommonSubsequence(gs_pairs, ts_pairs.subList(1, ts_pairs.size())),
                longestCommonSubsequence(gs_pairs.subList(1, gs_pairs.size()), ts_pairs)
        );
    }

    double precision () {
        return 100 * no_correct / (1.0 * no_found);
    }
    
    double recall () {
        return 100* no_correct / (1.0 * no_pairs);
    }
    
    double F1 () {
        return (precision() == 0 && recall() ==0) ? 
        		0 : 
        	   (2 * precision() * recall() )/(1.0 * (precision() + recall()) );
    }
    
    void print() 
    {
        System.out.printf("processed %d tweets with %d pairs; "+
                          "found: %d; correct: %d.\n", no_tweets, no_pairs, no_found, no_correct);
        System.out.printf("precision:  %.10f; recal: %.10f; F1:  %.10f\n", precision(), recall(), F1());
    }
    
    public static void main(String[] args) 
    {
        EvaluateRank eR = new EvaluateRank();
        Evaluate e = new Evaluate();
        
        try {
            if(args.length < 3)
                throw new Exception("Missing inputs");
            
            // read gold set and test set
            Map<String, List<Pair>> gs = new Util().readSet (args[0]);        
            Map<String, List<PairRank>> ts = new Util().readSetRank (args[1]);

            int atRank=Integer.parseInt(args[2]);    
            eR.eval(gs,ts,atRank);
            eR.print();            
            
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
