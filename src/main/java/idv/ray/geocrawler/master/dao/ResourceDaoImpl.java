package idv.ray.geocrawler.master.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import idv.ray.geocrawler.util.javabean.geodata.Resource;

@Configuration
public class ResourceDaoImpl extends ResourceDao {

	@Bean
	public ResourceDaoImpl rDao(DataSource dataSource) {
		return new ResourceDaoImpl(dataSource);
	}

	public ResourceDaoImpl(DataSource ds) {
		super(ds);	
		this.dataType = Resource.TYPE_NAME;
	}

	public Resource get(int id) {
		String sql = "select* from resource where id=?";
		Resource resource = jdbcTemplate.queryForObject(sql, new Object[] { id }, new ResourceMapper());
		return resource;
	}

	public List<Resource> getAll() {
		String sql = "select* from resource";
		return jdbcTemplate.query(sql, new ResourceMapper());
	}

	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	public void insert(Resource rs) {
		String sql = "insert into resource (link,level,srctaskid,geotype)values('" + rs.getLink() + "'," + rs.getLevel()
				+ "," + rs.getSrcTaskId() + ",'" + rs.getGeoType() + "')";
		jdbcTemplate.execute(sql);
		// System.out.println("inserting resource: " + rs.toString());
	}

}
