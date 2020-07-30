package idv.ray.geocrawler.util.javabean.geodata;

import javax.persistence.Entity;

import idv.ray.geocrawler.util.javabean.worker.Worker;

@Entity
public class Resource extends GeoData {

	public static final String TYPE_NAME = "resource";

	public Resource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Resource(String link, int level, String geoType, int srcTaskId) {
		super(link, level, geoType, srcTaskId);
	}

	public Resource(int id, String link, int level, String geoType, int srcTaskId) {
		super(id, link, level, geoType, srcTaskId);
	}

	public Resource(int id, String link, int level, String geoType, int srcTaskId, Worker worker) {
		super(id, link, level, geoType, srcTaskId, worker);
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", level=" + level + ", link=" + link + ", time=" + time + ", geoType=" + geoType
				+ ", srcTaskId=" + srcTaskId + "]";
	}

}
