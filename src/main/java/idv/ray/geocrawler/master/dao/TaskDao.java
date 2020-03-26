package idv.ray.geocrawler.master.dao;

import javax.sql.DataSource;

import idv.ray.geocrawler.javabean.geodata.Task;

public abstract class TaskDao extends GeoDataDaoAbst<Task> {

	public TaskDao(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	abstract public void update(Task task);

	abstract public Task getNextNullStatus(String geoType);
	
	abstract public Task getNextRunningStatus(String geoType);

}
