package idv.ray.geocrawler.master.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import idv.ray.geocrawler.javabean.geodata.GeoData;

public abstract class GeoDataDaoAbst<T extends GeoData> implements GeoDataDao<T> {

	// set in constructor
	protected String geoDataType;
	protected JdbcTemplate jdbcTemplate;

	// set in setGeoType
	protected String geoType;
	protected String tableName;

	public GeoDataDaoAbst(DataSource ds,String geoDataType) {
		this.jdbcTemplate = new JdbcTemplate(ds);
		this.geoDataType = geoDataType;
	}
	
	public void setGeoType(String geoType) {
		this.geoType = geoType;
		tableName = geoDataType + "_" + geoType;
		// System.out.println("setting geoType in resourceDaoImpl ");
	}
	
	public boolean containsLink(String link) {
		String sql = "select exists (select 1 from " + this.tableName + " where link = '" + link + "')";
		return jdbcTemplate.queryForObject(sql, boolean.class);
	}
}
