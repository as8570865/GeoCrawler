package ray.geocrawler.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ray.geocrawler.model.Resource;

public class ResourceDaoImpl implements ResourceDao {

	private JdbcTemplate jdbcTemplate;
	private String geoType;
	
	public ResourceDaoImpl(DataSource ds,String geoType) {
		this.jdbcTemplate = new JdbcTemplate(ds);
		this.geoType=geoType;
		System.out.println("in resourceDaoImpl constructor");
	}

	public void insertResource(Resource rs) {
		// TODO Auto-generated method stub

	}

	public Resource getResource(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Resource> getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteResource(int id) {
		// TODO Auto-generated method stub

	}
	
	public void init() {
		
		System.out.println("starting init...");
        String sql = "create table if not exists resource_"+geoType
        		+"( id	int not null auto_increment primary key,"
        		+ "url text not null)";
        jdbcTemplate.execute(sql);
		
	}

}
