package neeleval;

import java.net.MalformedURLException;
import java.net.URL;

public class Pair{
	
	private String entityMention;
	private URL URL;

	public boolean equals(Object object) {
        return object != null
                && object instanceof Pair
                && this.entityMention.equals(((Pair) object).entityMention)
                && this.URL.equals(((Pair) object).URL);
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
