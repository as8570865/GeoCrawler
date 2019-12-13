package ray.geocrawler.service;

import java.util.List;
import java.util.Map;

import ray.geocrawler.dao.*;
import ray.geocrawler.model.*;

public class TaskServiceImpl implements TaskService,GeoTypeSettable {

	// store task and resource dao
	private Map<String, GeoDataDao> daoMap;

	// init seeds
	private Map<String, List<String>> seedMap;

	// for init table
	private Map<String, String> tableSchemaMap;
	private List<String> geoTypeList;
	
	private String geoType;

	public void setDaoMap(Map<String, GeoDataDao> daoMap) {
		this.daoMap = daoMap;
	}

	public void setSeedMap(Map<String, List<String>> seedMap) {
		this.seedMap = seedMap;
	}

	public void setTableSchemaMap(Map<String, String> tableSchemaMap) {
		this.tableSchemaMap = tableSchemaMap;
	}

	public void setGeoTypeList(List<String> geoTypeList) {
		this.geoTypeList = geoTypeList;
	}
	

	public void setGeoType(String geoType) {
		this.geoType=geoType;
		
	}

	public void init() {
		// init table
		ResourceDaoImpl rDao = (ResourceDaoImpl) this.daoMap.get("resource");
		TaskDaoImpl tDao = (TaskDaoImpl) this.daoMap.get("task");
		for (String geoType : geoTypeList) {
			rDao.setGeoType(geoType);
			tDao.setGeoType(geoType);
			rDao.init(tableSchemaMap.get("resource"));
			tDao.init(tableSchemaMap.get("task"));
		}

		// init seed
		for (String geoType : seedMap.keySet()) {
			tDao.setGeoType(geoType);
			List<String> seeds = seedMap.get(geoType);
			for (String seed : seeds) {
				Task task = new Task(seed, 0);
				tDao.insert(task);
			}
		}

	}


}
