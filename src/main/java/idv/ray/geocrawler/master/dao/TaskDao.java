package idv.ray.geocrawler.master.dao;

import idv.ray.geocrawler.util.javabean.geodata.Task;

public interface TaskDao extends GeoDataDao<Task> {

	public void update(Task task);

	public Task getReady(String geoType);

	public Task getRunning(String geoType);
	
	public boolean tableIsEmpty();
}
