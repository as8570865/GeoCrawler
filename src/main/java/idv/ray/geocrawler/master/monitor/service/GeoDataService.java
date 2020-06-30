package idv.ray.geocrawler.master.monitor.service;

import idv.ray.geocrawler.util.javabean.geodata.GeoData;

public interface GeoDataService<T extends GeoData> {

	public T getById(int id);

	public void delete(T geoData);

}
