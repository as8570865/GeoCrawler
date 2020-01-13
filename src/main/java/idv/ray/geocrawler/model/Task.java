package idv.ray.geocrawler.model;

import org.json.JSONObject;

public class Task extends GeoData {
	boolean running;

	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Task(JSONObject jsonObj) {
		super(jsonObj);
	}

	public Task(int id) {
		this.id = id;
	}

	public Task(String link, int level) {
		this.link = link;
		this.level = level;

	}

	@Override
	public String toString() {
		if (id == 0)
			return "Task [dataType= " + this.getDataType() + ", running=" + running + ", level=" + level + ", geoType="
					+ geoType + ", link=" + link + "]";
		return "Task [id= " + id + ", dataType= " + this.getDataType() + ", running=" + running + ", level=" + level
				+ ", geoType=" + geoType + ", link=" + link + "]";
	}

	@Override
	public String toJsonString() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", this.id);
		jsonObj.put("geoType", this.geoType);
		jsonObj.put("link", this.link);
		jsonObj.put("level", this.level);
		jsonObj.put("status", this.running);

		return jsonObj.toString();

	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return "task";
	}

}
