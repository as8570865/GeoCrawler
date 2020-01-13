package idv.ray.geocrawler.dao;

import java.util.List;

import idv.ray.geodata.GeoData;

public interface GeoDataDao <T extends GeoData>{
	
	public void setGeoType(String geoType);
	
	public T get(int id);
	
	public List<T>getAll();
	
	public void insert(T geoData);
	
	public void delete(int id);
	
	public void init(String tableSchema);
	
	public boolean containsLink(String link);
}
