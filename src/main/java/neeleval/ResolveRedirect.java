package neeleval;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;

public class ResolveRedirect {
	
	private int SLEEP_TIME = 500;

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
    
	private Map<String, List<Pair>> resolve(Map<String, List<Pair>> gs) 
	{

		Map<String, List<Pair>> result = new HashMap<String, List<Pair>>();
		
		System.out.printf("* Check redirects for:\n");
		for(Entry<String, List<Pair>> entry : gs.entrySet()) 
		{
			List<Pair> pairs = new LinkedList<Pair>();
			
			String tweet_id = entry.getKey();
			for (Pair p : entry.getValue())  {
				pairs.add(resolveRedirect(p));
				try {
					Thread.sleep(SLEEP_TIME); //less demanding to DBpedia
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
			
			result.put(tweet_id, pairs);
		}
		
		return result;
	}
	
    protected Pair resolveRedirect(Pair p) 
    {
        String urlResolved = p.getURL().toString();

    	String arrSQL =
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX dbo: <http://dbpedia.org/ontology/>" + "\n"
                + "SELECT ?redirectsTo WHERE {"
                + "<" + urlResolved + "> dbo:wikiPageRedirects ?redirectsTo" + ".}"; 	    	

        try{
	    	Query query = QueryFactory.create(arrSQL);
	        QueryExecution qExe =
	                QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

	        com.hp.hpl.jena.query.ResultSet results2 = qExe.execSelect();
			for ( ;results2.hasNext(); ) {
			    QuerySolution soln = results2.nextSolution();
			    urlResolved = soln.getResource("redirectsTo").getURI();
			}
			
			qExe.close();
        }catch(Exception e) {	
        	e.printStackTrace();
        }
        
	    System.out.printf("\t%s -> %s\n",p.getURL().toString(), urlResolved);

    	return new Pair(p.getEntityMention(), urlResolved);
	}

	private void save(Map<String, List<Pair>> rs, String fileName) 
	{
		File file = new File(fileName);
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
					
			for(Entry<String,List<Pair>> entry : rs.entrySet()) 
			{
				output.write(entry.getKey());
				for(Pair p : entry.getValue()) 
					output.write(p.serialize());
				
				output.write("\n");
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {

        ResolveRedirect redirect = new ResolveRedirect();

        String inFileName = args[0];
        String outFileName = args[1];
        
        if(args.length == 3) redirect.SLEEP_TIME=Integer.parseInt(args[2]);
        
        try {	
            //if(args.length < 2) throw new Exception("Missing inputs");
            System.out.printf("* Loading the GS\n");
            Map<String, List<Pair>> gs = redirect.readSet (inFileName);       
            
            System.out.printf("* Resolving the URIs (it may take time, usually 1 sec per each URI)\n");
            Map<String, List<Pair>> rs = redirect.resolve (gs);
            
            System.out.printf("* Saving the new GS\n");
            redirect.save(rs, outFileName);
             
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}


}
