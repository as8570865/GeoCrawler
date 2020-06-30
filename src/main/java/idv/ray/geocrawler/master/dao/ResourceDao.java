package idv.ray.geocrawler.master.dao;

import java.util.List;

import idv.ray.geocrawler.util.javabean.geodata.Resource;

public interface ResourceDao extends GeoDataDao<Resource> {

	public List<Resource> get(int id, String geoType, String processorIp, String workerName, int minLevel,
			int maxLevel);
}
