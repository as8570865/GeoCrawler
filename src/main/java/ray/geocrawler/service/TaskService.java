package ray.geocrawler.service;

import java.util.List;
import java.util.Map;

import ray.geocrawler.dao.GeoDataDao;
import ray.geocrawler.model.Resource;
import ray.geocrawler.model.Task;

public interface TaskService {
	
	public void setDaoMap(Map<String, GeoDataDao> daoMap) ;

	public void setSeedMap( Map<String, List<String>> seedMap) ;

	public void setTableSchemaMap(Map<String, String> tableSchemaMap) ;

	public void setGeoTypeList(List<String> geoTypeList) ;

	public void init() ;
	
	public Task getNext(String geoType,int id);
	
	public void postTask(List<Task> taskList);
	
	public void postResource(Resource resource);
}
