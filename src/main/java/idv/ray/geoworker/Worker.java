package idv.ray.geoworker;

import java.io.IOException;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import idv.ray.geodata.Task;

public class Worker {

	public static void main(String[] args) throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		Set<String> geoTypeSet=(Set<String>) context.getBean("geoTypeSet");
		for(String geoType:geoTypeSet) {
			JSONObject reqJson = new JSONObject();
			Task task = new Task(new JSONObject(Connector.getTask(reqJson, geoType)));
			System.out.println("worker gets task: "+task.toString());
			
			Crawler crawler=(Crawler) context.getBean("crawler");
			Set<String> resultSet=crawler.crawl(task);
			
			JSONObject crawledResultJson=new JSONObject();
			JSONArray urlArr=new JSONArray();
			JSONObject resultJson=new JSONObject();
			for(String url:resultSet) {
				urlArr.put(url);
			}
			resultJson.put("value", urlArr);
			crawledResultJson.put("taskId", task.getId());
			crawledResultJson.put("type", "task");	
			crawledResultJson.put("result", resultJson);
			System.out.print("worker gets next task: "+Connector.getTask(crawledResultJson, geoType));
			
			
		}

	}

}
