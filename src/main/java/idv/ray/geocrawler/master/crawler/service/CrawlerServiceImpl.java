package idv.ray.geocrawler.master.crawler.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import idv.ray.geocrawler.master.dao.ResourceDao;
import idv.ray.geocrawler.master.dao.TaskDao;
import idv.ray.geocrawler.util.circularlist.CircularList;
import idv.ray.geocrawler.util.javabean.geodata.Resource;
import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.httpbody.HttpBody;

@Service
@Transactional
public class CrawlerServiceImpl implements CrawlerService {

	private class GeoTypeAlternater {

		// used to evenly distributed task to worker between different geotype
		private CircularList<String> geoTypeCirList;
		private int iteratingTimes = 0;

		private GeoTypeAlternater(CircularList<String> geoTypeCirList) {
			this.geoTypeCirList = geoTypeCirList;
		}

		private String next() {
			String geoType = geoTypeCirList.get(Math.abs(iteratingTimes % geoTypeCirList.size()));
			iteratingTimes++;

			if (iteratingTimes == geoTypeCirList.size())
				iteratingTimes = 0;

			return geoType;

		}

	}

	@Autowired
	private TaskDao tDao;
	@Autowired
	private ResourceDao rDao;

	@Autowired
	@Qualifier("seedMap")
	private Map<String, List<String>> seedMap;

	private GeoTypeAlternater geoTypeAlternater;

	private Task srcTask;
	
	//initialize the geoTypeAlternater
	@PostConstruct
	public void postConst() {
		geoTypeAlternater=new GeoTypeAlternater(new CircularList<String>(seedMap.keySet()));
	}

	private void insertUrlkSet(Set<String> urlSet) {
		for (String url : urlSet) {
			if (!tDao.containsLink(url)) {

				tDao.insert(new Task(url, srcTask.getLevel() + 1, srcTask.getGeoType(), srcTask.getId()));
				System.out.println("inserting url: " + url);
			} else {
				System.out.println("url: " + url + " already exists");
			}
		}
	}

	private void insertResource() {
		Resource r = new Resource(srcTask.getLink(), srcTask.getLevel(), srcTask.getGeoType(), srcTask.getId());
		if (!rDao.containsLink(r.getLink())) {
			rDao.insert(r);
			System.out.println("inserting resource: " + r.toString());
		} else {
			System.out.println("resource: " + r.toString() + " already exists");
		}
	}

	public void init() {

		if (tDao.tableIsEmpty()) {
			for (String geoType : seedMap.keySet()) {
				List<String> seeds = seedMap.get(geoType);
				for (String seed : seeds) {
					Task task = new Task(seed, 0, geoType, 0);// level=0
					tDao.insert(task);
				}
			}
		}else {
			throw new RuntimeException("crawler service was already initialized");
		}

	}

	public Task getNextTask() {

		String geoType = geoTypeAlternater.next();

		Task returnTask = tDao.getReady(geoType);
		if (returnTask.isValid()) {
			// set task running
			returnTask.setStatus(Task.Status.RUNNING);
			tDao.update(returnTask);
			System.out.println("get next task with null status");
		} else {
			// call get next running task if there is no null status task
			System.out.println("get next task with running status");
			returnTask = tDao.getRunning(geoType);
		}

		return returnTask;

	}

	public void post(String geoType, HttpBody reqBody) {

		Task st = reqBody.getSrcTask();
		if (st.isValid()) {
			setSrcTask(st);

			// set previous task finished
			srcTask.setStatus(Task.Status.FINISHED);
			tDao.update(srcTask);

			if (!reqBody.isResource()) {
				Set<String> urlSet = reqBody.getUrlSet();
				if (!urlSet.isEmpty()) {
					insertUrlkSet(urlSet);
				} else {
					System.out.println("empty url set, not posting any task");
				}
			} else {
				insertResource();
			}
		} else {
			System.out.println("invalid source task, no posting");
		}
	}

	private void setSrcTask(Task prevTask) {
		this.srcTask = prevTask;
	}

	public Set<String> getGeoTypeSet() {
		return seedMap.keySet();
	}

}
