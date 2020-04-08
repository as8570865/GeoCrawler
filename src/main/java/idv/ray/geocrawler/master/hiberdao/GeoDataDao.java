package idv.ray.geocrawler.master.hiberdao;

import java.util.List;

import idv.ray.geocrawler.util.javabean.geodata.GeoData;

public interface GeoDataDao<T extends GeoData> {

	public T get(int id);

	public List<T> getAll();

	public T insert(T geoData);

	public void delete(int id);

	public void init(String tableSchema);

	public boolean isInitialized();

	public boolean containsLink(String link);
}