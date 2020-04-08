package idv.ray.geocrawler.master.hiberdao;

import idv.ray.geocrawler.util.javabean.geodata.Task;

public interface TaskDao extends GeoDataDao<Task> {

	public void update(Task task);

	public Task getNextNullStatus(String geoType);

	public Task getNextRunningStatus(String geoType);
}
