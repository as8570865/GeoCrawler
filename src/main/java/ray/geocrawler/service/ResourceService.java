package ray.geocrawler.service;

import org.json.JSONObject;

import ray.geocrawler.dao.GeoDataDao;
import ray.geocrawler.model.Resource;

public class ResourceService extends GeoDataService{
	
	@Override
	public void setGeoDataDao(GeoDataDao geoDataDao) {
		this.geoDataDao=geoDataDao;		
	}
	
	@Override
	public void post(String reqBody,String geoType) {
		geoDataDao.setGeoType(geoType);
		Resource resource=new Resource(new JSONObject(reqBody));
		geoDataDao.insert(resource);
	}

}
