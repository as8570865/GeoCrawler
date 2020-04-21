package idv.ray.geocrawler.master.monitor.service;

import java.awt.List;

import idv.ray.geocrawler.util.javabean.geodata.GeoData;

public interface GeoDataService<T extends GeoData> {
	
	public List getAll(int a);

	public T getById(int id);

	public void delete(T geoData);
}
