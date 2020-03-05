package idv.ray.geoworker;

import java.io.IOException;

import org.json.JSONObject;
import org.jsoup.Jsoup;

public class MasterConnector {

	private static final String MASTER_URL = "http://localhost:8080/GeoCrawler/";

	public static String getTask(JSONObject requestJson,String geoType) throws IOException {
		
		String returnString=Jsoup.connect(MASTER_URL+geoType)
				.timeout(100 * 1000)
				.requestBody(requestJson.toString())
				.header("Content-Type", "application/json")
				.post()
				.text();
			
		return returnString;

	}

}
