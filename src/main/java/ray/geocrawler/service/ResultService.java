package ray.geocrawler.service;

import java.util.List;

import ray.geocrawler.model.*;

public interface ResultService {
	
	public void postTask(Task task,List<Task> taskList);
	
	public void postResource(Task task,Resource resource);
	
}
