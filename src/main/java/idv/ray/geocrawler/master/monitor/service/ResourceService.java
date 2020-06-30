package idv.ray.geocrawler.master.monitor.service;

import java.util.List;

import idv.ray.geocrawler.util.javabean.geodata.Resource;

public interface ResourceService extends GeoDataService<Resource> {

	public void insert(Resource resource, String capabilitiesUrl);

	public List<Resource> get(int id, String geoType, String processorIp, String workerName, int minLevel,
			int maxLevel);
}
