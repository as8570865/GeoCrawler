package ray.geocrawler.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ray.geocrawler.model.Resource;
import ray.geocrawler.model.Task;;

public class TaskDaoImpl extends TaskDao {

	private final static String dataType = "task";

	public TaskDaoImpl(DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
		this.rowMapper=new TaskMapper();
		this.geoDataType=dataType;
		
	}
	
	@Override
	public void insert(Task task) {
		String sql = "insert into " + tableName + "(link,level)value('" + task.getLink() + "','"+task.getLevel()+"')";
		jdbcTemplate.execute(sql);
	}

	//haven't tested
	@Override
	public void updateStatus(Task task) {
		String sql ="UPDATE "+this.tableName+" SET status="+task.isRunning()+" WHERE id="+task.getId()+";";		
	}

}
