package idv.ray.geocrawler.master.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import idv.ray.geocrawler.util.javabean.geodata.Task;

public class TaskMapper implements RowMapper<Task> {

	public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
		Task task = new Task(rs.getInt("id"),rs.getString("link"),rs.getInt("level"),rs.getString("geoType"),rs.getInt("srcTaskId"));
		task.setRunning(rs.getBoolean("status"));
		return task;
	}

}
