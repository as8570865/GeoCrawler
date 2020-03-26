package idv.ray.geocrawler.master.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import idv.ray.geocrawler.javabean.geodata.GeoData;

public abstract class GeoDataDaoAbst<T extends GeoData> implements GeoDataDao<T> {

	// set in constructor
	protected JdbcTemplate jdbcTemplate;

	protected String dataType;

	public GeoDataDaoAbst(DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
	}

	public void init(String tableSchema) {
		// System.out.println("checking " + geoType + " resource table...");
		// String checkTableExists = "show tables like '" + this.tableName + "'";
		String sql = "CREATE TABLE IF NOT EXISTS " + this.dataType + "(" + tableSchema + ");";
		jdbcTemplate.execute(sql);

	}

	public boolean containsLink(String link) {
		String sql = "select exists (select 1 from " + this.dataType + " where link = '" + link + "')";
		return jdbcTemplate.queryForObject(sql, boolean.class);
	}
}
