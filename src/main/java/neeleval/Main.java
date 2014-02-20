package neeleval;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main {
    
    private int no_tweets = 0;
    private int no_pairs = 0;
    
    private int no_correct = 0;
    private int no_found = 0;
    
    HashMap<String, List<Pair>> readSet(String filename) throws Exception 
    {
        HashMap<String, List<Pair>> result = new HashMap<String, List<Pair>>();
        List<String> lines = FileUtils.readLines(new File(filename), "utf8");
        
        for (String line : lines) {
            String[] tokens = line.split("\t");
        
            if ( (tokens.length -1) % 2  == 1 ) throw new Exception("Malformed File " + filename);
                
            String tweetid = tokens[0];
            ArrayList<Pair> pairs = new ArrayList<Pair>();
            for (int i=1; i<tokens.length; i++) {
                Pair p = new Pair(tokens[i], tokens[i+1]);
                i++;
                pairs.add(p);
            }
            result.put(tweetid, pairs);
        }
            
        return result;
    }

    void eval(Map<String, List<Pair>> gs, Map<String, List<Pair>> ts) 
    {
        no_tweets = gs.size();

        for(Entry<String,List<Pair>> e : gs.entrySet() ) 
        {
            String gs_tweetid = e.getKey();
            List<Pair> gs_pairs = e.getValue(); 
            
            no_pairs += gs_pairs.size();
            
            if ( ts.containsKey(gs_tweetid) )
            {
                List<Pair> ts_pairs = ts.get(gs_tweetid);
                no_found += ts_pairs.size();
                no_correct += longestCommonSubsequence(gs_pairs, ts_pairs);
            }
        }
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
        return no_correct / (1.0 * no_found);
    }
    
    double recall () {
        return no_correct / (1.0 * no_pairs);
    }
    
    double F1 () {
        return (2 * precision() * recall() )/ (1.0 * (precision() + recall()) );
    }
    
    void print() 
    {
        System.out.printf("processed %d tweets with %d pairs; "+
                          "found: %d; correct: %d.\n", no_tweets, no_pairs, no_found, no_correct);
        System.out.printf("precision:  %.3f; recal: %.3f; F1:  %.3f\n", precision(), recall(), F1());
    }
    
    public static void main(String[] args) 
    {
        Main main = new Main();

        try {
            if(args.length < 2)
                throw new Exception("Missing inputs");
            
            // read gold set and test set
            Map<String, List<Pair>> gs = main.readSet (args[0]);        
            Map<String, List<Pair>> ts = main.readSet (args[1]);
                
            main.eval(gs,ts);
            main.print();            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
