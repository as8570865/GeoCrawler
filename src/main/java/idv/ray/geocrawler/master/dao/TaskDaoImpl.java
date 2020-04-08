package idv.ray.geocrawler.master.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import idv.ray.geocrawler.util.javabean.geodata.Task;

@Configuration
public class TaskDaoImpl extends TaskDao {
	
	@Bean
	public TaskDaoImpl tDao(DataSource dataSource) {
		return new TaskDaoImpl(dataSource);
	}

	public TaskDaoImpl(DataSource ds) {
		super(ds);
		this.dataType = Task.TYPE_NAME;
	}

	public void insert(Task task) {
		String sql = "insert into task (link,level,srctaskid,geotype)values('" + task.getLink() + "',"
				+ task.getLevel() + "," + task.getSrcTaskId() + ",'" + task.getGeoType() + "')";
		jdbcTemplate.execute(sql);
	}

	public Task get(int id) {
		String sql = "select* from task where id=?";
		Task task = jdbcTemplate.queryForObject(sql, new Object[] { id }, new TaskMapper());

		return task;
	}

	public List<Task> getAll() {
		String sql = "select* from task";
		return jdbcTemplate.query(sql, new TaskMapper());
	}

	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	// haven't tested
	// only update status in task
	@Override
	public void update(Task task) {
		String sql = "UPDATE task SET status=" + task.isRunning() + " WHERE id=" + task.getId() + ";";
		jdbcTemplate.execute(sql);
	}

	// haven't tested
	@Override
	public Task getNextNullStatus(String geoType) {
		String sql = "select*from task where status is null and geotype='" + geoType + "'  order by level asc limit 1;";
		List<Task> taskList = jdbcTemplate.query(sql, new TaskMapper());
		if (taskList.isEmpty())
			return new Task();

		return taskList.get(0);
	}

	@Override
	public Task getNextRunningStatus(String geoType) {
		String sql = "select*from task where status is true and geotype='" + geoType + "' order by level asc limit 1;";
		List<Task> taskList = jdbcTemplate.query(sql, new TaskMapper());
		if (taskList.isEmpty())
			return new Task();
		return taskList.get(0);
	}

}
