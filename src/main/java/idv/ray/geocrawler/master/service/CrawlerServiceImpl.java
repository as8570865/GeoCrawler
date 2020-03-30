package idv.ray.geocrawler.master.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import idv.ray.geocrawler.javabean.circularlist.CircularList;
import idv.ray.geocrawler.javabean.geodata.Resource;
import idv.ray.geocrawler.javabean.geodata.Task;
import idv.ray.geocrawler.javabean.httpbody.HttpBody;
import idv.ray.geocrawler.master.dao.ResourceDao;
import idv.ray.geocrawler.master.dao.TaskDao;
import idv.ray.geocrawler.master.dao.TaskDaoImpl;

@Configuration
public class CrawlerServiceImpl implements CrawlerService {

	private class GeoTypeAlternater {

		// used to evenly distributed task to worker between different geotype
		private CircularList<String> geoTypeCirList;
		private int iteratingTimes = 0;

		GeoTypeAlternater(CircularList<String> geoTypeCirList) {
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

	private class TableInitializer {

		private Map<String, String> tableSchemaMap;
		private Map<String, List<String>> seedMap;

		private TableInitializer(Map<String, String> tableSchemaMap, Map<String, List<String>> seedMap) {
			this.seedMap = seedMap;
			this.tableSchemaMap = tableSchemaMap;
		}

		private void init() {
			if (!tDao.isInitialized()) {
				// init table
				rDao.init(tableSchemaMap.get("resource"));
				tDao.init(tableSchemaMap.get("task"));

				// init seed
				for (String geoType : seedMap.keySet()) {
					List<String> seeds = seedMap.get(geoType);
					for (String seed : seeds) {
						Task task = new Task(seed, 0, geoType, 0);// level=0
						tDao.insert(task);
					}
				}
			}
		}

	}

	@Autowired
	private TaskDao tDao;
	@Autowired
	private ResourceDao rDao;

	private boolean initialized = false;

	private GeoTypeAlternater geoTypeAlternater;

	private Task srcTask;

	@Bean
	public CrawlerServiceImpl crawlerService() {
		return new CrawlerServiceImpl();
	}

	private void setSrcTask(Task prevTask) {
		this.srcTask = prevTask;
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

	public void init(Map<String, String> tableSchemaMap, Map<String, List<String>> seedMap) {
		TableInitializer ti = new TableInitializer(tableSchemaMap, seedMap);
		geoTypeAlternater = new GeoTypeAlternater(new CircularList<String>(seedMap.keySet()));
		ti.init();
		setInitialized(true);
	}

	public Task getNextTask() {

		String geoType = geoTypeAlternater.next();

		Task returnTask = tDao.getNextNullStatus(geoType);
		if (returnTask.isValid()) {
			// set task running
			returnTask.setRunning(true);
			tDao.update(returnTask);
			System.out.println("get next task with null status");
		} else {
			// call get next running task if there is no null status task
			System.out.println("get next task with running status");
			returnTask = tDao.getNextRunningStatus(geoType);
		}

		return returnTask;

	}

	public void post(String geoType, HttpBody reqBody) {

		Task st = reqBody.getSrcTask();
		if (st.isValid()) {
			setSrcTask(st);

			// set previous task finished
			srcTask.setRunning(false);
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

	public boolean isInitialized() {
		return tDao.isInitialized();
	}

	private void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

}
