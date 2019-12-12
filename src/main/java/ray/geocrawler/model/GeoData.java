package ray.geocrawler.model;

import org.json.JSONObject;
import org.json.JSONString;

public abstract class GeoData {
	protected int id;
	protected String geoType;
	protected String link;
	
	public GeoData() {
		
	}

	public GeoData(JSONObject jsonObj) {
		this.link=jsonObj.getString("link");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGeoType() {
		return geoType;
	}

	public void setGeoType(String geoType) {
		this.geoType = geoType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public abstract String toString() ;
	
	public abstract String toJsonString() ;
	
	
	
}
