package idv.ray.geocrawler.service;

import java.util.List;

import idv.ray.geocrawler.model.GeoData;
import idv.ray.geocrawler.model.Task;

public interface TaskService {
	
	public void setGeoType(String geoType);

	public void init() ;
	
	public Task getNext(String geoType,Task task);
	
	public void post(String geoType,Task task,List<GeoData> geoDataList);
	
}
