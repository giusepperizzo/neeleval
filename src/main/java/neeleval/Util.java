package neeleval;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;

public class Util {
	
    HashMap<String, List<Pair>> readSet(String filename) throws Exception 
    {
        HashMap<String, List<Pair>> result = new HashMap<String, List<Pair>>();
        List<String> lines = FileUtils.readLines(new File(filename), "utf8");
        
        for (String line : lines) {
            String[] tokens = line.split("\t");
        
            if ( (tokens.length -1) % 2  == 1 ) 
            	throw new Exception("Malformed File " + filename + ". id=" + tokens[0]);
                
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
    
    HashMap<String, List<PairRank>> readSetRank(String filename) throws Exception 
    {
        HashMap<String, List<PairRank>> result = new HashMap<String, List<PairRank>>();
        List<String> lines = FileUtils.readLines(new File(filename), "utf8");
        
        for (String line : lines) {
            String[] tokens = line.split("\t");
        
            if ( (tokens.length -1) % 2  == 1 ) 
            	throw new Exception("Malformed File " + filename + ". id=" + tokens[0]);
                
            String tweetid = tokens[0];
            ArrayList<PairRank> pairs = new ArrayList<PairRank>();
            for (int i=1; i<tokens.length; i++) 
            {     
            	// uris included in [] and divided by ;
            	String[] uriRankSet = tokens[i+1].split(";");
            	Vector<String> uris = new Vector<String>();
            	for (int j=0; j<uriRankSet.length; j++ ) uris.add(j, uriRankSet[j]);
                
            	//tokens[i] entity mention
            	PairRank p = new PairRank(tokens[i], uris);
                i++;
                pairs.add(p);
            }
            result.put(tweetid, pairs);
        }
            
        return result;
    }
}
