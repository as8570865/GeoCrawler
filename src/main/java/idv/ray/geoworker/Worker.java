package idv.ray.geoworker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import idv.ray.geodata.Task;
import idv.ray.geostandard.GeoStandard;
import idv.ray.geostandard.SOS;

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

		for (String geoType : geoStandardMap.keySet()) {
			// get geoStandard
			GeoStandard geoStandard = geoStandardMap.get(geoType);

			JSONObject crawledResultJson = new JSONObject();
			int level=0;
			while (true) {
				Task task = new Task(new JSONObject(Connector.getTask(crawledResultJson, geoType)));
				if(task.getLevel()>this.maxLevel) {
					System.out.println("beyond max level, crawler finished");
					return;					
				}

				// create crawled result json
				// put the task id
				crawledResultJson.put("taskId", task.getId());

				if (geoStandard.isGeoResource(task.getLink())) {
					System.out.println("this task is geo-resource........");
					crawledResultJson.put("type", "resource");
				} else {
					System.out.println("this task is \"not\" geo-resource........");
					ApplicationContext context= new ClassPathXmlApplicationContext("beans.xml");
					Crawler crawler = (Crawler)context.getBean("crawler");
					Set<String> resultSet = crawler.crawl(task);
					JSONObject resultJson = new JSONObject();
					JSONArray urlArr = new JSONArray();
					for (String url : resultSet) {
						urlArr.put(url);
					}
					crawledResultJson.put("type", "task");
					resultJson.put("value", urlArr);
					crawledResultJson.put("result", resultJson);

				}

			}

		}

	}

	public static void main(String[] args) throws IOException {

		Map<String, GeoStandard> geoStandardMap = new HashMap<String, GeoStandard>();
		geoStandardMap.put("sos", new SOS());

		Worker worker = new Worker(2);
		worker.setGeoStandardSet(geoStandardMap);
		worker.run();

	}

}
