package idv.ray.geocrawler.util.javabean.geodata;

import javax.persistence.Entity;

@Entity
public class Resource extends GeoData {
	
	public static final String TYPE_NAME="resource";

	public Resource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Resource(String link, int level, String geoType, int srcTaskId) {
		super(link,level,geoType,srcTaskId);
	}
	
	public Resource(int id, String link, int level, String geoType,int srcTaskId) {
		super(id,link, level, geoType,srcTaskId);
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", level=" + level + ", link=" + link +", geoType=" + geoType+ "]";
	}


}
