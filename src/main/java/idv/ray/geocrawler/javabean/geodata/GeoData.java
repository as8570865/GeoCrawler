package idv.ray.geocrawler.javabean.geodata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GeoData {

	@JsonProperty("id")
	protected int id;
	@JsonProperty("level")
	protected int level;
	@JsonProperty("link")
	protected String link;
	@JsonProperty("time")
	protected long time;
	@JsonProperty("geoType")
	protected String geoType;
	@JsonProperty("srcTaskId")
	protected int srcTaskId;

	public GeoData() {

	}
	
	public int getSrcTaskId() {
		return srcTaskId;
	}

	public void setSrcTaskId(int srcTaskId) {
		this.srcTaskId = srcTaskId;
	}

	public String getGeoType() {
		return geoType;
	}

	public void setGeoType(String geoType) {
		this.geoType = geoType;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@JsonIgnore
	public boolean isValid() {
		if (id == 0 && link == null)
			return false;
		else
			return true;
	}

	@Override
	public abstract String toString();

}
