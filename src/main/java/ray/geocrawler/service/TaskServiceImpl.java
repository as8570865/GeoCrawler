package ray.geocrawler.service;

import java.util.List;
import java.util.Map;

import ray.geocrawler.dao.*;
import ray.geocrawler.model.*;

public class TaskServiceImpl implements TaskService {

	// store task and resource dao
	private Map<String, GeoDataDao> daoMap;

	// init seeds
	private Map<String, List<String>> seedMap;

	// for init table
	private Map<String, String> tableSchemaMap;

	private String geoType;
	
	public TaskServiceImpl(Map<String, GeoDataDao> daoMap,Map<String, List<String>> seedMap,Map<String, String> tableSchemaMap) {
		this.daoMap = daoMap;
		this.seedMap = seedMap;
		this.tableSchemaMap = tableSchemaMap;
	}

	public void setGeoType(String geoType) {
		this.geoType = geoType;

	}

	public void init() {
		
		ResourceDaoImpl rDao = (ResourceDaoImpl) this.daoMap.get("resource");
		TaskDaoImpl tDao = (TaskDaoImpl) this.daoMap.get("task");

		
		for (String geoType : seedMap.keySet()) {
			// init table
			rDao.setGeoType(geoType);
			tDao.setGeoType(geoType);
			rDao.init(tableSchemaMap.get("resource"));
			tDao.init(tableSchemaMap.get("task"));
			
			// init seed
			tDao.setGeoType(geoType);
			List<String> seeds = seedMap.get(geoType);
			for (String seed : seeds) {
				Task task = new Task(seed, 0);
				tDao.insert(task);
			}
		}

	}

	public Task getNext(String geoType, Task task) {
		System.out.println("calling getNext in taskService");
		TaskDao tDao = (TaskDao) daoMap.get("task");
		tDao.setGeoType(geoType);

		// task.getId()=0 when first calling
		Task returnTask = tDao.getNext(task.getId());

		// last one situation
		if (returnTask == null)
			return null;

		// set task running
		returnTask.setRunning(true);
		tDao.update(returnTask);
		return returnTask;

	}

	public void post(String geoType, Task task, List<GeoData> geoDataList) {
		TaskDao tDao = (TaskDao) daoMap.get("task");
		tDao.setGeoType(geoType);

		// set previous task finished
		task.setRunning(false);
		tDao.update(task);

		// insert task list
		
		for (GeoData data : geoDataList) {
			GeoDataDao dao=daoMap.get(data.getDataType());
			dao.setGeoType(geoType);
			if (!dao.containsLink(data.getLink()))
				data.setLevel(task.getLevel()+1);
				dao.insert(data);
		}
		
	}

}
