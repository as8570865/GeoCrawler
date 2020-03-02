import java.io.IOException;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import idv.ray.geodata.Task;
import idv.ray.geostandard.GeoStandard;
import idv.ray.geostandard.SOS;
import idv.ray.geoworker.Crawler;

public class Test {

	public static void main(String[] args) throws IOException {

		GeoStandard sos = new SOS();

		Task task = new Task("sos uk air", 0);

		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		Crawler crawler = (Crawler) context.getBean("crawler");
		
		Set<String> urls=crawler.crawl(task);
		for(String url:urls) {
			System.out.println("url: "+url);
		}

	}

}
