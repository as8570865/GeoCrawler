package idv.ray.geocrawler.master.monitor.service;

import java.util.List;

import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.geodata.Task.Status;

public interface TaskService extends GeoDataService<Task> {

	public void insert(Task task);

	public List<Task> get(int id,String geoType, String processorIp, String workerName, int minLevel, int maxLevel, Status status);
}
