package idv.ray.geoworker;

import java.io.IOException;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import idv.ray.geodata.Task;

public class Worker {

	public static void main(String[] args) throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		Set<String> geoTypeSet=(Set<String>) context.getBean("geoTypeSet");
		for(String geoType:geoTypeSet) {
			
		}
		
		JSONObject reqJson = new JSONObject();
		Task task = new Task(new JSONObject(Connector.getTask(reqJson, "test5")));
		
		Crawler crawler=(Crawler) context.getBean("crawler");
		Set<String> resultSet=crawler.crawl(task);
		for(String link:resultSet)
			System.out.println(link);
		
	}

}
