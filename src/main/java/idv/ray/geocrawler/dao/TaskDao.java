package idv.ray.geocrawler.dao;

import javax.sql.DataSource;

import idv.ray.geocrawler.model.Task;

public abstract class TaskDao extends GeoDataDaoAbst<Task> {

	public TaskDao(DataSource ds, String geoDataType) {
		super(ds, geoDataType);
		// TODO Auto-generated constructor stub
	}

	abstract public void update(Task task);

	abstract public Task getNext(int id);

}