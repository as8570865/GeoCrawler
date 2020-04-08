package idv.ray.geocrawler.master.dao;

import javax.sql.DataSource;

import idv.ray.geocrawler.util.javabean.geodata.Resource;

public abstract class ResourceDao extends  GeoDataDaoAbst<Resource>{

	public ResourceDao(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

}
