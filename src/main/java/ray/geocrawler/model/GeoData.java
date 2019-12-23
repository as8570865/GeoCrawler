package ray.geocrawler.model;

import org.json.JSONObject;

public abstract class GeoData {
	protected int id;
	protected int level;
	protected String geoType;
	protected String link;

	protected static String DATA_TYPE;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public GeoData() {

	}

	public GeoData(JSONObject jsonObj) {
		if (jsonObj.has("id"))
			this.id = jsonObj.getInt("id");
		if (jsonObj.has("link"))
			this.link = jsonObj.getString("link");
		if (jsonObj.has("level"))
			this.level = jsonObj.getInt("level");

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

	public abstract String toJsonString();
	
	@Override
	public abstract String toString();

	public abstract String getDataType();

}
