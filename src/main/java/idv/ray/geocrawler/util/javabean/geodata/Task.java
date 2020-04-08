package idv.ray.geocrawler.util.javabean.geodata;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Task extends GeoData {
	
	public static final String TYPE_NAME="task";
	
	@JsonProperty("running")
	boolean running;

	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Task(String link, int level, String geoType, int srcTaskId) {
		super(link, level, geoType, srcTaskId);
	}

	public Task(int id, String link, int level, String geoType, int srcTaskId) {
		super(id, link, level, geoType, srcTaskId);
	}

	@Override
	public String toString() {
		return "Task [running=" + running + ", id=" + id + ", level=" + level + ", link=" + link + ", geoType="
				+ geoType + "]";
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
