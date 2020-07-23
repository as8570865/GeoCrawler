package idv.ray.geocrawler.master.dao;

import idv.ray.geocrawler.util.javabean.geodata.GeoData;

public interface GeoDataDao<T extends GeoData> {

	public T getById(int id);

	public T insert(T geoData);

	public void delete(int id);

	public void init(String tableSchema);

	public boolean isInitialized();

	public boolean contains(T geoData);
	
	public int getMaxLevel();
}