package ray.geocrawler.controller;

import java.util.List;
import java.util.Map;

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
public class GeoDataController {
	
	protected Map<String, GeoDataDao> daoMap;
	protected GeoDataDao geoDataDao;
	
	//for init table
	private Map<String, String> tableSchemaMap;
	private List<String> geoTypeList;

	public void setDaoMap(Map<String, GeoDataDao> daoMap) {
		this.daoMap = daoMap;
	}

	public void setTableSchemaMap(Map<String, String> tableSchemaMap) {
		this.tableSchemaMap = tableSchemaMap;

	}

	public void setGeoTypeList(List<String> ls) {
		this.geoTypeList = ls;
	}
	
	@RequestMapping(value = "/init", produces = "application/json")
	public @ResponseBody String init() {
		GeoDataDao rDao = this.daoMap.get("resource");
		GeoDataDao tDao = this.daoMap.get("task");
		rDao.setTableSchema(tableSchemaMap.get("resource"));
		tDao.setTableSchema(tableSchemaMap.get("task"));
		for (String geoType : geoTypeList) {
			rDao.setGeoType(geoType);
			tDao.setGeoType(geoType);
			rDao.init();
			tDao.init();
		}
		
		return "successfully initialized!";
	}
	
	@RequestMapping(value = "/{geoType}/{geoDataType}", produces = "application/json")
	public @ResponseBody String get(@RequestParam(value = "id", required = false) Integer id,
			@PathVariable("geoType") String geoType,@PathVariable("geoDataType") String geoDataType) {
		System.out.println("send get request...");
		
		//get dao by request urlpath
		geoDataDao=daoMap.get(geoDataType);
		geoDataDao.setGeoType(geoType);

		JSONArray jsonArr = new JSONArray();

		// return all geo data
		if (id == null) {
			List<GeoData> geoDataList = geoDataDao.getAll();
			for (GeoData gd : geoDataList) {
				JSONObject jobj = new JSONObject(gd.toJsonString());
				jsonArr.put(jobj);
			}
			return jsonArr.toString();
		}

		// return single geodata by id
		GeoData geoData = geoDataDao.get(id);
		return geoData.toJsonString();

	}

	@RequestMapping(value = "/{geoType}/{geoDataType}", method = RequestMethod.POST)
	public String post(@RequestBody String resourceJsonString, @PathVariable("geoType") String geoType,@PathVariable("geoDataType") String geoDataType) {

		System.out.println("sending post request...");

		//get dao by request urlpath
		geoDataDao=daoMap.get(geoDataType);
		geoDataDao.setGeoType(geoType);

		// JSONObject reqBody = null;
		try {
			// reqBody = new JSONObject(resourceJsonString);
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
