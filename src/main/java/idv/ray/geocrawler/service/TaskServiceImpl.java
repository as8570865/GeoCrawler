package idv.ray.geocrawler.service;

import java.util.List;
import java.util.Map;

import idv.ray.geocrawler.dao.GeoDataDao;
import idv.ray.geocrawler.dao.ResourceDaoImpl;
import idv.ray.geocrawler.dao.TaskDao;
import idv.ray.geocrawler.dao.TaskDaoImpl;
import idv.ray.geodata.GeoData;
import idv.ray.geodata.Task;

public class TaskServiceImpl implements TaskService {

	// store task and resource dao
	private Map<String, GeoDataDao> daoMap;

	// init seeds
	private Map<String, List<String>> seedMap;

	// for init table
	private Map<String, String> tableSchemaMap;

	public boolean isValidGeotype(String geoType) {
		return seedMap.containsKey(geoType);
	}

	public TaskServiceImpl(Map<String, GeoDataDao> daoMap, Map<String, List<String>> seedMap,
			Map<String, String> tableSchemaMap) {
		this.daoMap = daoMap;
		this.seedMap = seedMap;
		this.tableSchemaMap = tableSchemaMap;
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

	public Task getNext(String geoType) {
		// System.out.println("calling getNext in taskService");
		TaskDao tDao = (TaskDao) daoMap.get("task");
		tDao.setGeoType(geoType);

		// task.getId()=0 when first calling
		Task returnTask = tDao.getNextNullStatus();
		// System.out.println("null status task in service: "+returnTask.toString());
		if (returnTask.isValid()) {
			// set task running
			returnTask.setRunning(true);
			tDao.update(returnTask);
			System.out.println("return a null task");
		} else {
			System.out.println("return a running task");
			returnTask = tDao.getNextRunningStatus();
			// System.out.println("running task in service: "+returnTask.toString());
		}

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
			GeoDataDao dao = daoMap.get(data.getDataType());
			dao.setGeoType(geoType);

			if (!dao.containsLink(data.getLink())) {
				data.setLevel(task.getLevel() + 1);
				dao.insert(data);
				System.out.println("inserting: " + data.toString());
			} else {
				System.out.println(data.toString() + " already exists");
			}
		}

	}

}
