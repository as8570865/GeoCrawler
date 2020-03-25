package idv.ray.geocrawler.worker.core;

import java.io.IOException;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import idv.ray.geocrawler.javabean.geodata.Task;
import idv.ray.geocrawler.javabean.httpbody.HttpBody;
import idv.ray.geocrawler.javabean.json.GeoDataSerializer;
import idv.ray.geocrawler.javabean.json.HttpBodySerializer;

public class MasterConnector {

	private static final String MASTER_URL = "http://localhost:8080/geocrawler-master/";

	public static Task getTask(HttpBody reqBody,String geoType) throws IOException {
		
		String returnString=Jsoup.connect(MASTER_URL+geoType)
				.timeout(300 * 1000)
				.requestBody(new HttpBodySerializer().serialize(reqBody))
				.header("Content-Type", "application/json")
				.post()
				.text();
			
		return (Task)new GeoDataSerializer().deserialize(returnString, Task.class);

	}

}
