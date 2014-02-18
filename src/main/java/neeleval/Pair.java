package neeleval;

import java.net.URI;
import java.net.URISyntaxException;

public class Pair implements Comparable<Pair>{
	
	private String entityMention;
	private URI URI;
	
	public int compareTo(Pair o) {
		return (this.entityMention.equals(o.entityMention) && this.URI.equals(o.URI) ) ? 
				1 : 0;
	}
	
	public Pair(String entityMention, String URI) {
		this.entityMention = entityMention;
		try {
			this.URI = new URI (URI);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	
}
