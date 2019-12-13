package ray.geocrawler.service;

import java.util.List;
import java.util.Map;

import ray.geocrawler.dao.GeoDataDao;

public interface TaskService {
	
	public void setDaoMap(Map<String, GeoDataDao> daoMap) ;

	public void setSeedMap( Map<String, List<String>> seedMap) ;

	public void setTableSchemaMap(Map<String, String> tableSchemaMap) ;

	public void setGeoTypeList(List<String> geoTypeList) ;

	public void init() ;
}
