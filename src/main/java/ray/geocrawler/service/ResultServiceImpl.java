package ray.geocrawler.service;

import java.util.List;
import java.util.Map;

import ray.geocrawler.dao.GeoDataDao;
import ray.geocrawler.model.Resource;
import ray.geocrawler.model.Task;

public class ResultServiceImpl implements ResultService,GeoTypeSettable{
	
	// store task and resource dao
	private Map<String, GeoDataDao> daoMap;
	
	String geoType;

	public void setDaoMap(Map<String, GeoDataDao> daoMap) {
		this.daoMap = daoMap;
	}
	
	public void setGeoType(String geoType) {
		this.geoType=geoType;
		
	}

	public void postTask(Task task, List<Task> taskList) {
		// TODO Auto-generated method stub
		
	}

	public void postResource(Task task, Resource resource) {
		
		
	}	
	
}
