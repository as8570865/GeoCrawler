package ray.geocrawler.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ray.geocrawler.model.Resource;

public class ResourceDaoImpl extends GeoDataDao<Resource> {

	private final static String dataType = "resource";

	public ResourceDaoImpl(DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
		this.rowMapper = new ResourceMapper();
		this.geoDataType = dataType;
	}

	public void init() {
		// System.out.println("checking " + geoType + " resource table...");
		String sql = "create table if not exists " + this.tableName
				+ "( id	int not null auto_increment primary key, link text not null)";
		jdbcTemplate.execute(sql);

	}
}
