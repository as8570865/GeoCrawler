package ray.geocrawler.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ray.geocrawler.model.GeoData;

public abstract class GeoDataDao<T extends GeoData> {

	// set in constructor
	protected RowMapper<T> rowMapper;
	protected String geoDataType;
	protected JdbcTemplate jdbcTemplate;

	// set in setGeoType
	protected String geoType;
	protected String tableName;

	//abstract methods
	abstract public void insert(T GeoData) ;
	
	public void setGeoType(String geoType) {
		this.geoType = geoType;
		tableName = geoDataType + "_" + geoType;
		// System.out.println("setting geoType in resourceDaoImpl ");
	}

	public T get(int id) {
		String sql = "select* from " + tableName + " where id=?";
		T geoData = jdbcTemplate.queryForObject(sql, new Object[] { id }, rowMapper);
		geoData.setGeoType(geoType);
		return geoData;
	}

	public List<T> getAll() {
		String sql = "select* from " + tableName;
		return jdbcTemplate.query(sql, rowMapper);
	}

	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	public void init(String tableSchema) {
		// System.out.println("checking " + geoType + " resource table...");
		//String checkTableExists = "show tables like '" + this.tableName + "'";
		String sql = "CREATE TABLE IF NOT EXISTS " + this.tableName
				+ "("+tableSchema+");";
		jdbcTemplate.execute(sql);

	}
}
