package ray.geocrawler.dao;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ray.geocrawler.model.Resource;

public class ResourceMapper implements RowMapper<Resource>{

	public Resource mapRow(ResultSet rs, int rowNum) throws SQLException {
		Resource resource=new Resource();
		resource.setId(rs.getInt("id"));
		resource.setLink(rs.getString("link"));
		resource.setLevel(rs.getInt("level"));
		return resource;
	}

}
