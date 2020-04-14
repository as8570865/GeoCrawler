package idv.ray.geocrawler.master.monitor.service;

import idv.ray.geocrawler.util.javabean.geodata.Resource;

public interface ResourceService extends GeoDataService<Resource>{

	public void insert(Resource resource, String capabilitiesUrl);
}
