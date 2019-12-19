package ray.geocrawler.dao;

import javax.sql.DataSource;

import ray.geocrawler.model.Resource;

public abstract class ResourceDao extends  GeoDataDaoAbst<Resource>{

	public ResourceDao(DataSource ds, String geoDataType) {
		super(ds, geoDataType);
		// TODO Auto-generated constructor stub
	}

}
