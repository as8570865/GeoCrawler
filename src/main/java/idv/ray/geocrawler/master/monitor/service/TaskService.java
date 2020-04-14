package idv.ray.geocrawler.master.monitor.service;

import idv.ray.geocrawler.util.javabean.geodata.Task;

public interface TaskService extends GeoDataService<Task> {

	public void insert(Task task);
}
