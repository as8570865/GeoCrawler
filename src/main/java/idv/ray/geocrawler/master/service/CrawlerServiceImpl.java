package idv.ray.geocrawler.master.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import idv.ray.geocrawler.javabean.circularlist.CircularList;
import idv.ray.geocrawler.javabean.geodata.Resource;
import idv.ray.geocrawler.javabean.geodata.Task;
import idv.ray.geocrawler.javabean.httpbody.HttpBody;
import idv.ray.geocrawler.master.dao.ResourceDao;
import idv.ray.geocrawler.master.dao.TaskDao;

public class CrawlerServiceImpl implements CrawlerService {

	private TaskDao tDao;
	private ResourceDao rDao;

	private Task srcTask;

	// used to evenly distributed task to worker between different geotype
	private CircularList<String> geoTypeList;
	private int iteratingTimes = 0;

	// init seeds
	private Map<String, List<String>> seedMap;

	// for init table
	private Map<String, String> tableSchemaMap;

	private void setSrcTask(Task prevTask) {
		this.srcTask = prevTask;
	}

	private void insertUrlkSet(String goeType, Set<String> urlSet) {
		tDao.setGeoType(goeType);
		for (String url : urlSet) {
			if (!tDao.containsLink(url)) {
				tDao.insert(new Task(url, srcTask.getLevel() + 1));
				System.out.println("inserting url: " + url);
			} else {
				System.out.println("url: " + url + " already exists");
			}
		}
	}

	private void insertResource(String geoType) {
		rDao.setGeoType(geoType);
		Resource r = new Resource(srcTask.getLink());
		if (!rDao.containsLink(r.getLink())) {
			r.setLevel(srcTask.getLevel());
			rDao.insert(r);
			System.out.println("inserting resource: " + r.toString());
		} else {
			System.out.println("resource: " + r.toString() + " already exists");
		}
	}

	// check if this geotype in the seed map
	public boolean isValidGeotype(String geoType) {
		return seedMap.containsKey(geoType);
	}

	public CrawlerServiceImpl(Map<String, List<String>> seedMap, Map<String, String> tableSchemaMap, TaskDao tDao,
			ResourceDao rDao) {
		// this.daoMap = daoMap;
		this.rDao = rDao;
		this.tDao = tDao;
		this.seedMap = seedMap;
		this.tableSchemaMap = tableSchemaMap;
		geoTypeList = new CircularList<String>(seedMap.keySet());
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
				Task task = new Task(seed, 0);// level=0
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
			System.out.println("get next task with null status");
		} else {
			// call get next running task if there is no null status task
			System.out.println("get next task with running status");
			returnTask = tDao.getNextRunningStatus();
		}

		// for the purpose of distinguishing task with different geotype
		returnTask.setGeoType(geoType);
		return returnTask;

	}

	public String getNextGeoType() {
		String geoType = geoTypeList.get(Math.abs(iteratingTimes % geoTypeList.size()));
		iteratingTimes++;
		return geoType;
	}

	public void post(String geoType, HttpBody reqBody) {
		// set dao
		tDao.setGeoType(geoType);
		rDao.setGeoType(geoType);

		Task st = reqBody.getSrcTask();
		if (st.isValid()) {
			setSrcTask(st);

			// set previous task finished
			srcTask.setRunning(false);
			tDao.update(srcTask);

			if (!reqBody.isResource()) {
				Set<String> urlSet = reqBody.getUrlSet();
				if (!urlSet.isEmpty()) {
					insertUrlkSet(geoType, urlSet);
				} else {
					System.out.println("empty url set, not posting any task");
				}
			} else {
				insertResource(geoType);
			}
		} else {
			System.out.println("invalid source task, no posting");
		}
	}

}
