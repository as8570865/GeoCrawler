package ray.geocrawler.model;

import org.json.JSONObject;

public class Task extends GeoData {
	boolean running;
	int level;

	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Task(JSONObject jsonObj) {
		super(jsonObj);
		this.level = jsonObj.getInt("level");
	}

	public Task(int id, String geoType, String link, boolean running, int level) {
		this.id = id;
		this.geoType = geoType;
		this.link = link;
		this.running = running;
		this.level = level;
	}

	public Task(String link,int level) {
		this.link=link;
		this.level=level;
		
	}

	@Override
	public String toString() {
		if (id == 0)
			return "Task [running=" + running + ", level=" + level + ", geoType=" + geoType + ", link=" + link + "]";
		return "Task [running=" + running + ", level=" + level + ", id=" + id + ", geoType=" + geoType + ", link="
				+ link + "]";
	}

	@Override
	public String toJsonString() {
		return null;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
