package idv.ray.geocrawler.worker.core;

import java.io.IOException;

import org.jsoup.Jsoup;

import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.httpbody.CrawlerHttpBody;
import idv.ray.geocrawler.util.json.JSONSerializer;

public class MasterConnector {

	private static final String MASTER_URL = "http://localhost:8080/geocrawler-master/";

	public static Task getTask(CrawlerHttpBody reqBody, String geoType) throws IOException {

		String returnString = Jsoup.connect(MASTER_URL + geoType).timeout(300 * 1000)
				.requestBody(new JSONSerializer<CrawlerHttpBody>().serialize(reqBody))
				.header("Content-Type", "application/json")
				.post()
				.text();

		return new JSONSerializer<Task>().deserialize(returnString, Task.class);

	}

}
