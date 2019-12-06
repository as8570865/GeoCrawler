package ray.geocrawler.dao;

import java.util.List;

import ray.geocrawler.model.Resource;

public interface ResourceDao {
	void insertResource(Resource rs);

	Resource getResource(int id);

	List<Resource> getResources();
	
	void deleteResource(int id);
	
	void init();
}
