package ray.geocrawler.model;

import org.json.JSONObject;

public class Task extends GeoData {
	boolean status;
	int level;

	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Task(int id, String geoType, String link, boolean status, int level) {
		this.id = id;
		this.geoType = geoType;
		this.link = link;
		this.status = status;
		this.level = level;
	}

	public Task(String geoType, String link, int level) {
		this.geoType = geoType;
		this.link = link;
		this.level = level;
	}

	@Override
	public String toString() {
		if (id == 0)
			return "Task [status=" + status + ", level=" + level + ", geoType=" + geoType + ", link="
					+ link + "]";
		return "Task [status=" + status + ", level=" + level + ", id=" + id + ", geoType=" + geoType + ", link=" + link
				+ "]";
	}
	
	@Override
	public String toJsonString() {
		return null;
	}

	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
