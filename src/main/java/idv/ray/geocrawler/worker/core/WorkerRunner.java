package idv.ray.geocrawler.worker.core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import idv.ray.geocrawler.util.geostandard.GeoStandard;
import idv.ray.geocrawler.util.geostandard.RequestBasedGeoStandard;
import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.httpbody.CrawlerHttpBody;
import idv.ray.geocrawler.util.javabean.worker.Worker;
import idv.ray.geocrawler.worker.exception.GeoCrawlerWorkerException.CrawledResultException;
import idv.ray.geocrawler.worker.exception.GeoCrawlerWorkerException.FailToGetNextTaskException;
import idv.ray.geocrawler.worker.exception.GeoCrawlerWorkerException.FailToRegisterException;
import idv.ray.geocrawler.worker.exception.GeoCrawlerWorkerException.MasterException;

public class WorkerRunner {

	private Map<String, GeoStandard> geoStandardMap;

	private int maxLevel;

	public static Worker WORKER;

	public WorkerRunner(int maxLevel) {
		geoStandardMap = new HashMap<String, GeoStandard>();
		this.maxLevel = maxLevel;
	}

	public static void register(Worker worker) throws FailToRegisterException {
		MasterConnector.register(worker);
		WORKER = worker;
	}

	public void run() throws MasterException, InstantiationException, IllegalAccessException, ClassNotFoundException,
			FailToGetNextTaskException, CrawledResultException {

		// import geotype set from master
		Set<String> geoTypeSet = MasterConnector.getGeoTypeSet();
		for (String s : geoTypeSet) {
			// create geoStanard instance by reflection
			GeoStandard gs = (GeoStandard) Class.forName("idv.ray.geocrawler.util.geostandard." + s.toUpperCase())
					.newInstance();
			;
			geoStandardMap.put(s, gs);
		}

		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		String startGeoType = (String) context.getBean("startGeoType");

		// first get-task from master
		Task emptySrcTask = new Task();
		Task srcTask = MasterConnector.SendCrawledResultAndGetNextTask(new CrawlerHttpBody(emptySrcTask), startGeoType);

		while (true) {

			// this source task was processed by this worker
			srcTask.setWorker(WORKER);
			String geoType = srcTask.getGeoType();
			RequestBasedGeoStandard geoStandard = (RequestBasedGeoStandard) geoStandardMap.get(geoType);

			// create a httpBody with source task
			CrawlerHttpBody httpBody = new CrawlerHttpBody(srcTask);

			// check if task is a geo-resource
			if (geoStandard.isGeoResource(geoStandard.getCapabilitiesUrl(srcTask.getLink()))) {
				// if it's georesource
				System.out.println("this task is a georesource........");
				httpBody.setResource(true);
				// it's not georesource
			} else {
				System.out.println("this task is \"not\" a geo-resource........");
				Crawler crawler = (Crawler) context.getBean("crawler");
				List<String> urlList = crawler.crawl(srcTask);
				httpBody.setUrlList(urlList);
			}
			srcTask = MasterConnector.SendCrawledResultAndGetNextTask(httpBody, geoType);
			if (srcTask.getLevel() > this.maxLevel) {
				System.out.println("beyond max level, crawler finished");
				return;
			}

		}

	}

	public static void main(String[] args) throws UnknownHostException, InstantiationException, IllegalAccessException,
			ClassNotFoundException, MasterException, FailToGetNextTaskException, CrawledResultException {

		while (true) {
			Scanner reader = new Scanner(System.in);
			try {
				System.out.println("Please assign a worker name for registration. "
						+ "If this worker has already been registered, please type anything to continue: ");
				register(new Worker(reader.next(), InetAddress.getLocalHost().getHostAddress()));
				break;

			} catch (FailToRegisterException e) {
				System.out.println("Registration fails. Please try again. Error message: " + e.getMessage());
			}
		}

		WorkerRunner workerRunner = new WorkerRunner(4);
		workerRunner.run();

	}

}
