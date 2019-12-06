package ray.geocrawler.model;

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
		return "Task [status=" + status + ", level=" + level + ", id=" + id + ", geoType=" + geoType + ", link=" + link
				+ "]";
	}

}
