package idv.ray.geocrawler.master.dao;

import java.util.List;
import java.util.Optional;

import idv.ray.geocrawler.util.javabean.geodata.Task;

public interface TaskDao extends GeoDataDao<Task> {

	public void update(Task task);

	public Optional<Task> getReady(String geoType);

	public Optional<Task> getRunning(String geoType);

	public boolean tableIsEmpty();

	public List<Task> get(int id, String geoType, String processorIp, String workerName, int minLevel, int maxLevel,
			Task.Status status);
}
