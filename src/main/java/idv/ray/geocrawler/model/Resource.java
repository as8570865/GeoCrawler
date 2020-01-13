package idv.ray.geocrawler.model;

import org.json.JSONObject;

public class Resource extends GeoData {

	public Resource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Resource(JSONObject jsonObj) {
		super(jsonObj);
	}

	public Resource( String link) {
		
		this.link = link;
	}

	@Override
	public String toString() {
		if (id == 0)
			return "Resource [ dataType= "+this.getDataType()+", geoType=" + geoType + ", link=" + link + "]";
		return "Resource [dataType= "+this.getDataType()+", id=" + id + ", geoType=" + geoType + ", link=" + link + "]";
	}

	@Override
	public String toJsonString() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", this.id);
		jsonObj.put("geoType", this.geoType);
		jsonObj.put("link", this.link);
		jsonObj.put("level", this.level);

		return jsonObj.toString();
	}

	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return "resource";
	}

}
