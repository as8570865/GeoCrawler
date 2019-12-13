package ray.geocrawler.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ray.geocrawler.model.Resource;

public class ResourceDaoImpl extends ResourceDao {

	private final static String dataType = "resource";

	public ResourceDaoImpl(DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
		this.rowMapper = new ResourceMapper();
		this.geoDataType = dataType;
	}
	
	@Override
	public void insert(Resource rs) {
		String sql = "insert into " + tableName + "(link)value('" + rs.getLink() + "')";
		jdbcTemplate.execute(sql);
		// System.out.println("inserting resource: " + rs.toString());
	}

}
