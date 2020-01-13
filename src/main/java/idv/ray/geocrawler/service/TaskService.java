package idv.ray.geocrawler.service;

import java.util.List;

import idv.ray.geodata.GeoData;
import idv.ray.geodata.Task;

public interface TaskService {
	
	public void setGeoType(String geoType);

	public void init() ;
	
	public Task getNext(String geoType);
	
	public void post(String geoType,Task task,List<GeoData> geoDataList);
	
}
