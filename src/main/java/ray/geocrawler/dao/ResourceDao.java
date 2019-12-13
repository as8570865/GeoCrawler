package ray.geocrawler.dao;

import ray.geocrawler.model.Resource;

public abstract class ResourceDao extends GeoDataDao<Resource>{
	abstract public void insert(Resource Resource);
}
