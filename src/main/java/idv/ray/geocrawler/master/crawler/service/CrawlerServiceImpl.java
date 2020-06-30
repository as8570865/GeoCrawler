package idv.ray.geocrawler.master.crawler.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import idv.ray.geocrawler.exception.GeoCrawlerException;
import idv.ray.geocrawler.exception.GeoCrawlerException.FailToInitializeException;
import idv.ray.geocrawler.master.dao.ResourceDao;
import idv.ray.geocrawler.master.dao.TaskDao;
import idv.ray.geocrawler.master.dao.WorkerDao;
import idv.ray.geocrawler.util.circularlist.CircularList;
import idv.ray.geocrawler.util.javabean.geodata.Resource;
import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.httpbody.CrawlerHttpBody;
import idv.ray.geocrawler.util.javabean.worker.Worker;

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
	private TaskDao taskDao;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private WorkerDao workerDao;

	@Autowired
	@Qualifier("seedMap")
	private Map<String, List<String>> seedMap;

	private GeoTypeAlternater geoTypeAlternater;

	private Task srcTask;

	// initialize the geoTypeAlternater
	@PostConstruct
	public void postConst() {
		geoTypeAlternater = new GeoTypeAlternater(new CircularList<String>(seedMap.keySet()));
	}

	private void insertUrlkList(List<String> urlList) {
		for (String url : urlList) {
			Task t = new Task(url, srcTask.getLevel() + 1, srcTask.getGeoType(), srcTask.getId());
			t.setTime(java.time.LocalDateTime.now());
			if (!taskDao.contains(t)) {
				taskDao.insert(t);
				System.out.println("inserting url: " + url);
			} else {
				System.out.println("url: " + url + " already exists");
			}
		}
	}

	private void insertResource() {
		Resource r = new Resource(srcTask.getLink(), srcTask.getLevel(), srcTask.getGeoType(), srcTask.getId());
		r.setWorker(srcTask.getWorker());
		r.setTime(java.time.LocalDateTime.now());
		if (!resourceDao.contains(r)) {
			resourceDao.insert(r);
			System.out.println("inserting resource: " + r.toString());
		} else {
			System.out.println("resource: " + r.toString() + " already exists");
		}
	}

	@Override
	public void init() throws FailToInitializeException {

		if (taskDao.tableIsEmpty()) {
			for (String geoType : seedMap.keySet()) {
				List<String> seeds = seedMap.get(geoType);
				for (String seed : seeds) {
					Task task = new Task(seed, 0, geoType, 0);// level=0
					taskDao.insert(task);
				}
			}
		} else {
			throw new GeoCrawlerException.FailToInitializeException("crawler service was already initialized");
		}

	}

	@Override
	public Optional<Task> getNextTask() {

		String geoType = geoTypeAlternater.next();

		Optional<Task> optTask = taskDao.getReady(geoType);
		if (optTask.isPresent()) {
			Task t = optTask.get();
			// set task running
			t.setRunning();
			taskDao.update(t);
			System.out.println("get next ready task");
		} else {

			System.out.println("get next running task");
			optTask = taskDao.getRunning(geoType);

		}

		return optTask;

	}

	@Override
	public void post(String geoType, CrawlerHttpBody reqBody) {

		setSrcTask(reqBody.getSrcTask());

		// set previous task finished
		Worker processingWorker=reqBody.getSrcTask().getWorker();
		srcTask.setFinished(processingWorker);
		taskDao.update(srcTask);
		//set the crawling time for the worker
		processingWorker.setLastCrawlingTime(java.time.LocalDateTime.now());
		workerDao.update(processingWorker);

		if (!reqBody.isResource()) {
			List<String> urlList = reqBody.getUrlList();
			if (!urlList.isEmpty()) {
				insertUrlkList(urlList);
			} else {
				System.out.println("empty url set, not posting any task");
			}
		} else {
			insertResource();
		}

	}

	private void setSrcTask(Task t) {
		this.srcTask = t;
	}

	@Override
	public Set<String> getGeoTypeSet() {
		return seedMap.keySet();
	}

}
