package ray.geocrawler.model;

public class Resource extends GeoData {

	public Resource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Resource(String geoType,String link) {
		this.geoType=geoType;
		this.link=link;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", geoType=" + geoType + ", link=" + link + "]";
	}
	

}
