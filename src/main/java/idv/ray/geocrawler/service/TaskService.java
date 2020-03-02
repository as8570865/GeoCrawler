package idv.ray.geocrawler.service;

import org.json.JSONObject;

import idv.ray.geodata.Task;

public interface TaskService {

	public void init();

	public boolean isValidGeotype(String geoType);

	public Task getNext(String geoType);
	
	public String getNextGeoType();

	public void post(String geoType, JSONObject reqJson);

}
