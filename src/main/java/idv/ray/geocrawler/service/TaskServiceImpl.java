package idv.ray.geocrawler.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import idv.ray.circularlist.CircularList;
import idv.ray.geocrawler.dao.ResourceDao;
import idv.ray.geocrawler.dao.TaskDao;
import idv.ray.geodata.Resource;
import idv.ray.geodata.Task;

public class TaskServiceImpl implements TaskService {

	private TaskDao tDao;
	private ResourceDao rDao;
	
	private Task prevTask;
	
	private CircularList<String> geoTypeList;
	private int iteratingTimes=0;

	// init seeds
	private Map<String, List<String>> seedMap;

	// for init table
	private Map<String, String> tableSchemaMap;
	
	private void setPrevTask(Task prevTask) {
		this.prevTask=prevTask;
	}

	private void insertTask(String geoType, Set<String> taskSet) {
		tDao.setGeoType(geoType);

		for (String taskString:taskSet) {
			Task t = new Task(taskString, prevTask.getLevel() + 1);
			if (!tDao.containsLink(t.getLink())) {
				tDao.insert(t);
				System.out.println("inserting task: " + t.toString());
			} else {
				System.out.println("task: " + t.toString() + " already exists");
			}
		}
	}

	private void insertResource(String geoType) {
		rDao.setGeoType(geoType);
		Resource r = new Resource(prevTask.getLink());
		if (!rDao.containsLink(r.getLink())) {
			r.setLevel(prevTask.getLevel());
			rDao.insert(r);
			System.out.println("inserting resource: " + r.toString());
		} else {
			System.out.println("resource: " + r.toString() + " already exists");
		}
	}

	public boolean isValidGeotype(String geoType) {
		return seedMap.containsKey(geoType);
	}

	public TaskServiceImpl(Map<String, List<String>> seedMap, Map<String, String> tableSchemaMap, TaskDao tDao,
			ResourceDao rDao) {
		// this.daoMap = daoMap;
		this.rDao = rDao;
		this.tDao = tDao;
		this.seedMap = seedMap;
		this.tableSchemaMap = tableSchemaMap;
		geoTypeList=new CircularList<String>(seedMap.keySet());
	}

	public void init() {

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
				Task task = new Task(seed, 0);//level=0
				tDao.insert(task);
			}
		}
		
	}

	public Task getNext(String geoType) {

		tDao.setGeoType(geoType);

		Task returnTask = tDao.getNextNullStatus();
		if (returnTask.isValid()) {
			// set task running
			returnTask.setRunning(true);
			tDao.update(returnTask);
			System.out.println("return a null task");
		} else {
			// call get next running task if there is no null status task
			System.out.println("return a running task");
			returnTask = tDao.getNextRunningStatus();
		}
		
		//for the purpose of distinguishing task with different geotype
		returnTask.setGeoType(geoType);
		return returnTask;

	}
	
	public String getNextGeoType() {
		String geoType=geoTypeList.get(Math.abs(iteratingTimes%geoTypeList.size()));
		iteratingTimes++;
		return geoType;
	}

	public void post(String geoType, JSONObject reqJson) {
		// set dao
		tDao.setGeoType(geoType);
		rDao.setGeoType(geoType);

		setPrevTask(tDao.get(reqJson.getInt("taskId")));

		// set previous task finished
		prevTask.setRunning(false);
		tDao.update(prevTask);

		// insert data
		if (reqJson.getString("type").equals("task")) {
			//insert tasks
			JSONObject resultJson=reqJson.getJSONObject("result");
			JSONArray taskJsonArr = resultJson.getJSONArray("value");
			Set<String>taskSet=new HashSet<String>();
			for(int i=0;i<taskJsonArr.length();i++) {
				taskSet.add(taskJsonArr.getString(i));			
			}
			insertTask(geoType, taskSet);

		} else if (reqJson.getString("type").equals("resource")) {
			//insert resource
			insertResource(geoType);
		} else {
			System.out.println("wrong geoType content!!");
		}
	}

}
