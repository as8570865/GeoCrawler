package idv.ray.geocrawler.javabean.geodata;

public class Resource extends GeoData {

	public Resource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Resource(String link) {

		this.link = link;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", level=" + level + ", link=" + link +", geoType=" + geoType+ "]";
	}


}
