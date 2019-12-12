package ray.geocrawler.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ray.geocrawler.dao.GeoDataDao;
import ray.geocrawler.model.GeoData;
import ray.geocrawler.model.Resource;

@Controller
public abstract class GeoDataController {
	protected GeoDataDao geoDataDao;
	
	public abstract void setGeoDataDao(GeoDataDao geoDataDao);

	@RequestMapping(value = "/{geoType}", produces = "application/json")
	public @ResponseBody String get(@RequestParam(value="id",required =false) Integer id, @PathVariable("geoType") String geoType) {
		System.out.println("send get request...");
		geoDataDao.setGeoType(geoType);
		
		JSONArray jsonArr=new JSONArray();
		
		//return all geo data
		if(id==null) {
			List<GeoData> geoDataList=geoDataDao.getAll();
			for(GeoData gd:geoDataList) {
				JSONObject jobj=new JSONObject(gd.toJsonString());
				jsonArr.put(jobj);
			}
			return jsonArr.toString();
		}
		
		//return single geodata by id
		GeoData geoData = geoDataDao.get(id);
		return geoData.toJsonString();

	}
	
	@RequestMapping(value = "/{geoType}", method = RequestMethod.POST)
	public String post(@RequestBody String resourceJsonString, @PathVariable("geoType") String geoType) {

		System.out.println("sending post request...");

		geoDataDao.setGeoType(geoType);

		//JSONObject reqBody = null;
		try {
			//reqBody = new JSONObject(resourceJsonString);
			JSONArray resourceArr = new JSONArray(resourceJsonString);
			for (int i = 0; i < resourceArr.length(); i++) {
				Resource rs = new Resource(resourceArr.getJSONObject(i));
				geoDataDao.insert(rs);
			}
		} catch (Exception e) {
			throw new JSONException(e);
		}
		return resourceJsonString;

	}

}
