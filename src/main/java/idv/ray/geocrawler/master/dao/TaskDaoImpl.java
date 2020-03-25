package idv.ray.geocrawler.master.dao;

import java.util.List;

import javax.sql.DataSource;

import idv.ray.geocrawler.javabean.geodata.Task;

public class TaskDaoImpl extends TaskDao {

	public TaskDaoImpl(DataSource ds, String geoDataType) {
		super(ds, geoDataType);
	}

	public void insert(Task task) {
		String sql = "insert into " + tableName + "(link,level)values('" + task.getLink() + "','" + task.getLevel()
				+ "')";
		jdbcTemplate.execute(sql);
	}

	public Task get(int id) {
		String sql = "select* from " + tableName + " where id=?";
		Task task = jdbcTemplate.queryForObject(sql, new Object[] { id }, new TaskMapper());
		
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

	// haven't tested
	// only update status in task
	@Override
	public void update(Task task) {
		String sql = "UPDATE " + this.tableName + " SET status=" + task.isRunning() + " WHERE id=" + task.getId() + ";";
		jdbcTemplate.execute(sql);
	}

	// haven't tested
	@Override
	public Task getNextNullStatus() {
		String sql = "select*from " + this.tableName + " where status is null  order by level asc limit 1;";
		List<Task>taskList=jdbcTemplate.query(sql, new TaskMapper());
		if(taskList.isEmpty())
			return new Task();
		
		return taskList.get(0);
	}
	
	@Override
	public Task getNextRunningStatus() {
		String sql = "select*from " + this.tableName + " where status is true  order by level asc limit 1;";
		List<Task>taskList=jdbcTemplate.query(sql, new TaskMapper());
		if(taskList.isEmpty())
			return new Task();		
		return taskList.get(0);
	}

	public boolean containsLink(String link) {
		String sql = "select exists (select 1 from " + this.tableName + " where link = '" + link + "')";
		return jdbcTemplate.queryForObject(sql, boolean.class);
	}

	

}
