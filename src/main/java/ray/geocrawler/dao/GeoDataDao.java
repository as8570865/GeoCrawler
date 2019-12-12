package ray.geocrawler.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ray.geocrawler.model.GeoData;

public abstract class GeoDataDao <T extends GeoData> {
	
	//set in constructor
	protected RowMapper<T> rowMapper;
	protected String geoDataType;
	protected JdbcTemplate jdbcTemplate;
	
	//set in setGeoType
	protected String geoType;
	protected String tableName;

	public void setGeoType(String geoType) {
		this.geoType = geoType;
		tableName=geoDataType+"_"+geoType;
		//System.out.println("setting geoType in resourceDaoImpl ");
	}

	public void insert(T rs) {
		String sql = "insert into " + tableName + "(link)value('" + rs.getLink() + "')";
		jdbcTemplate.execute(sql);
		//System.out.println("inserting resource: " + rs.toString());
	}

	public T get(int id) {
		String sql = "select* from " + tableName + " where id=?";
		T geoData=jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
		geoData.setGeoType(geoType);
		return geoData;
	}

	public List<T> getAll() {
		String sql = "select* from " + tableName ;		
		return jdbcTemplate.query(sql,rowMapper);
	}

	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	public void init() {

		//System.out.println("checking " + geoType + " resource table...");
		String sql = "create table if not exists " + tableName
				+ "( id	int not null auto_increment primary key, link text not null)";
		jdbcTemplate.execute(sql);
		

	}
}
