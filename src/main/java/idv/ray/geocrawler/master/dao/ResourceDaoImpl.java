package idv.ray.geocrawler.master.dao;

import java.util.List;

import javax.sql.DataSource;

import idv.ray.geocrawler.javabean.geodata.Resource;

public class ResourceDaoImpl extends ResourceDao {

	public ResourceDaoImpl(DataSource ds, String dataType) {
		super(ds, dataType);
		// TODO Auto-generated constructor stub
	}

	public Resource get(int id) {
		String sql = "select* from " + tableName + " where id=?";
		Resource resource = jdbcTemplate.queryForObject(sql, new Object[] { id }, new ResourceMapper());
		return resource;
	}

	public List<Resource> getAll() {
		String sql = "select* from " + tableName;
		return jdbcTemplate.query(sql, new ResourceMapper());
	}

	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	public void init(String tableSchema) {
		// System.out.println("checking " + geoType + " resource table...");
		// String checkTableExists = "show tables like '" + this.tableName + "'";
		String sql = "CREATE TABLE IF NOT EXISTS " + this.tableName + "(" + tableSchema + ");";
		jdbcTemplate.execute(sql);

	}

	public void insert(Resource rs) {
		String sql = "insert into " + tableName + "(link,level)values('" + rs.getLink() + "',"+rs.getLevel()+")";
		jdbcTemplate.execute(sql);
		// System.out.println("inserting resource: " + rs.toString());
	}

}
