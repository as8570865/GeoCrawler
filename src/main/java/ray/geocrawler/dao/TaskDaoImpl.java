package ray.geocrawler.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ray.geocrawler.model.Task;;

public class TaskDaoImpl extends GeoDataDao<Task> {

	private final static String dataType = "task";

	public TaskDaoImpl(DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
		this.rowMapper=new TaskMapper();
		this.geoDataType=dataType;
		
	}

}
