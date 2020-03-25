package idv.ray.geocrawler.master.dao;

import javax.sql.DataSource;

import idv.ray.geocrawler.javabean.geodata.Resource;

public abstract class ResourceDao extends  GeoDataDaoAbst<Resource>{

	public ResourceDao(DataSource ds, String geoDataType) {
		super(ds, geoDataType);
		// TODO Auto-generated constructor stub
	}

}
