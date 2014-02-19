package neeleval;

import java.net.MalformedURLException;
import java.net.URL;

public class Pair implements Comparable<Pair>{
	
	private String entityMention;
	private URL URL;
	
	public int compareTo(Pair o) {
		return (this.entityMention.equals(o.entityMention) && this.URL.equals(o.URL) ) ? 
				1 : 0;
	}
	
	public Pair(String entityMention, String URL) {
		this.entityMention = entityMention;
		try {
			this.URL = new URL (URL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
}
