package ray.geocrawler.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ray.geocrawler.model.GeoData;

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
}
