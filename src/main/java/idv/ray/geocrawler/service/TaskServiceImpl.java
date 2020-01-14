package idv.ray.geocrawler.service;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import idv.ray.geocrawler.dao.GeoDataDao;
import idv.ray.geocrawler.dao.ResourceDao;
import idv.ray.geocrawler.dao.ResourceDaoImpl;
import idv.ray.geocrawler.dao.TaskDao;
import idv.ray.geocrawler.dao.TaskDaoImpl;
import idv.ray.geodata.GeoData;
import idv.ray.geodata.Resource;
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
		
		TaskDao tDao = (TaskDao) daoMap.get("task");
		tDao.setGeoType(geoType);

		Task returnTask = tDao.getNextNullStatus();
		if (returnTask.isValid()) {
			// set task running
			returnTask.setRunning(true);
			tDao.update(returnTask);
			System.out.println("return a null task");
		} else {
			//call get next running task if there is no null status task
			System.out.println("return a running task");
			returnTask = tDao.getNextRunningStatus();
		}

		return returnTask;

	}

	public void post(String geoType, JSONObject reqJson) {
		// set dao
		TaskDao tDao = (TaskDao) daoMap.get("task");
		ResourceDao rDao = (ResourceDao) daoMap.get("resource");
		tDao.setGeoType(geoType);
		rDao.setGeoType(geoType);

		Task task = tDao.get(reqJson.getInt("taskId"));

		// set previous task finished
		task.setRunning(false);
		tDao.update(task);

		// insert data
		if (reqJson.getString("type").equals("task")) {
			JSONArray taskJsonArr = reqJson.getJSONObject("result").getJSONArray("value");
			for (int i = 0; i < taskJsonArr.length(); i++) {
				Task t = new Task(taskJsonArr.getString(i),task.getLevel()+1);
				if (!tDao.containsLink(t.getLink())) {
					tDao.insert(t);
					System.out.println("inserting task: " + t.toString());
				} else {
					System.out.println("task: " + t.toString() + " already exists");
				}
			}
			
		} else if (reqJson.getString("type").equals("resource")) {
			Resource r = new Resource(task.getLink());
			if (!rDao.containsLink(r.getLink())) {
				r.setLevel(task.getLevel() + 1);
				rDao.insert(r);
				System.out.println("inserting resource: " + r.toString());
			} else {
				System.out.println("resource: " + r.toString() + " already exists");
			}
		} else {
			System.out.println("wrong geoType content!!");
		}
	}
}
