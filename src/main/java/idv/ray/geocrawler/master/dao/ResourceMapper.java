package idv.ray.geocrawler.master.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import idv.ray.geocrawler.javabean.geodata.Resource;

public class ResourceMapper implements RowMapper<Resource> {

	public Resource mapRow(ResultSet rs, int rowNum) throws SQLException {

		if (rs.next()) {
			Resource resource = new Resource(rs.getInt("id"),rs.getString("link"),rs.getInt("level"),rs.getString("geoType"),rs.getInt("srcTaskId"));
			return resource;
		} else
			return null;

	}

}
