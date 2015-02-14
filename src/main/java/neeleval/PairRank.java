package neeleval;

import java.util.Vector;

public class PairRank {

	private String entityMention;
	private Vector<String> urls;

	public PairRank(String entityMention, Vector<String> urls) {
		this.entityMention = entityMention;
		this.urls = urls;
	}
	
	public String getEntityMention() {
		return entityMention;
	}

	public void setEntityMention(String entityMention) {
		this.entityMention = entityMention;
	}

	public Vector<String> getUrls() {
		return urls;
	}

	public void setUrls(Vector<String> urls) {
		this.urls = urls;
	}	
	
}
