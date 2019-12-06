package ray.geocrawler.dao;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jndi.JndiTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

public class TaskDao {
	private DataSource dataSource;

	public TaskDao() {

	}

	public String getTask() throws NamingException {
		JndiTemplate jndiTemplate = new JndiTemplate();
		dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/UsersDB");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT link from task where id=1";
        String result=jdbcTemplate.queryForObject(sql, String.class);
		
        return result;
	}

}
