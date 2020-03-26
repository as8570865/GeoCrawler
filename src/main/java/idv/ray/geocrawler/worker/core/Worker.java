package idv.ray.geocrawler.worker.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import idv.ray.geocrawler.javabean.geodata.Task;
import idv.ray.geocrawler.javabean.httpbody.HttpBody;
import idv.ray.geocrawler.worker.geostandard.GeoStandard;
import idv.ray.geocrawler.worker.geostandard.SOS;
import idv.ray.geocrawler.worker.geostandard.WFS;
import idv.ray.geocrawler.worker.geostandard.WMS;
import idv.ray.geocrawler.worker.geostandard.WMTS;

public class Worker {

	Map<String, GeoStandard> geoStandardMap;

	private int maxLevel;

	public Worker(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public void setGeoStandardSet(Map<String, GeoStandard> geoStandardMap) {
		this.geoStandardMap = geoStandardMap;
	}

	public void run() throws IOException {

		if (geoStandardMap == null) {
			System.out.println("geoStandardMap is empty, please set geoStandardMap first");
			return;
		}

		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		String startGeoType = (String) context.getBean("startGeoType");

		// first get-task from master
		Task srcTask = MasterConnector.getTask(new HttpBody(new Task()), startGeoType);

		while (true) {

			String geoType = srcTask.getGeoType();
			GeoStandard geoStandard = geoStandardMap.get(geoType);

			// create a httpBody with source task
			HttpBody httpBody = new HttpBody(srcTask);

			// check if task is a geo-resource
			// if it's georesource
			if (geoStandard.isGeoResource(srcTask.getLink())) {
				System.out.println("this task is a georesource........");
				httpBody.setResource(true);
				// it's not georesource
			} else {
				System.out.println("this task is \"not\" a geo-resource........");
				Crawler crawler = (Crawler) context.getBean("crawler");
				Set<String> urlSet = crawler.crawl(srcTask);
				httpBody.setUrlSet(urlSet);
			}
			srcTask = MasterConnector.getTask(httpBody, geoType);
			if (srcTask.getLevel() > this.maxLevel) {
				System.out.println("beyond max level, crawler finished");
				return;
			}

		}

	}

	public static void main(String[] args) throws IOException {

		Map<String, GeoStandard> geoStandardMap = new HashMap<String, GeoStandard>();
		geoStandardMap.put("sos", new SOS());
		geoStandardMap.put("wms", new WMS());
		geoStandardMap.put("wmts", new WMTS());
		geoStandardMap.put("wfs", new WFS());

		Worker worker = new Worker(4);
		worker.setGeoStandardSet(geoStandardMap);
		worker.run();

	}

}
