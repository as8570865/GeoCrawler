package ray.geocrawler.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ray.geocrawler.dao.GeoDataDao;
import ray.geocrawler.model.GeoData;

public abstract class GeoDataService {

	protected GeoDataDao geoDataDao;

	abstract public void setGeoDataDao(GeoDataDao geoDataDao);

	abstract public void post(String reqBody,String geoType);
	
	public GeoData get(int id, String geoType) {

		geoDataDao.setGeoType(geoType);

		// return single geodata by id
		GeoData geoData = geoDataDao.get(id);
		return geoData;
	}
	
	public List<GeoData> getAll(String geoType){
		geoDataDao.setGeoType(geoType);
		List<GeoData> geoDataList = geoDataDao.getAll();
		return geoDataList;
		
	}

}
