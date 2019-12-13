package ray.geocrawler.dao;

import ray.geocrawler.model.Task;

public abstract class TaskDao extends GeoDataDao<Task>{
	
	@Override
	abstract public void insert(Task task);
	
	abstract public void updateStatus(Task task);
	
}
