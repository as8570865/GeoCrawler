package ray.geocrawler.dao;

import java.util.List;

import javax.sql.DataSource;

import ray.geocrawler.model.Task;

public class TaskDaoImpl extends TaskDao {

	public TaskDaoImpl(DataSource ds, String geoDataType) {
		super(ds, geoDataType);
		System.out.println("geodataType= "+geoDataType);
	}

	public void insert(Task task) {
		String sql = "insert into " + tableName + "(link,level)values('" + task.getLink() + "','" + task.getLevel()
				+ "')";
		jdbcTemplate.execute(sql);
	}

	// haven't tested
	// only update status in task
	@Override
	public void update(Task task) {
		String sql = "UPDATE " + this.tableName + " SET status=" + task.isRunning() + " WHERE id=" + task.getId() + ";";
		jdbcTemplate.execute(sql);
	}

	// haven't tested
	@Override
	public Task getNext(int id) {
		String sql = "select*from " + this.tableName + " where id>" + id + " and status <>true or id>" + id
				+ " and status is null limit 1;";
		Task task = jdbcTemplate.queryForObject(sql, new TaskMapper());
		return task;
	}

	public Task get(int id) {
		String sql = "select* from " + tableName + " where id=?";
		Task task = jdbcTemplate.queryForObject(sql, new Object[] { id }, new TaskMapper());
		task.setGeoType(geoType);
		return task;
	}

	public List<Task> getAll() {
		String sql = "select* from " + tableName;
		return jdbcTemplate.query(sql, new TaskMapper());
	}

	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	public void init(String tableSchema) {
		String sql = "CREATE TABLE IF NOT EXISTS " + this.tableName + "(" + tableSchema + ");";
		jdbcTemplate.execute(sql);
	}

}
