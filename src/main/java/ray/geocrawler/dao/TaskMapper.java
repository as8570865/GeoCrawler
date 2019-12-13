package ray.geocrawler.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ray.geocrawler.model.Task;

public class TaskMapper implements RowMapper<Task>{

	public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
		Task task=new Task();
		task.setId(rs.getInt("id"));
		task.setLink(rs.getString("link"));
		task.setLevel(rs.getInt("level"));
		task.setRunning(rs.getBoolean("status"));
		return task;
	}

}
