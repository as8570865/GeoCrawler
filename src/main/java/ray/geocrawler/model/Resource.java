package ray.geocrawler.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

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
			return "Resource [ geoType=" + geoType + ", link=" + link + "]";
		return "Resource [id=" + id + ", geoType=" + geoType + ", link=" + link + "]";
	}

	@Override
	public String toJsonString() {
		JSONObject resourceObj = new JSONObject();
		resourceObj.put("id", this.id);
		resourceObj.put("geoType", this.geoType);
		resourceObj.put("url", this.link);

		return resourceObj.toString();
	}

	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return "resource";
	}

}
