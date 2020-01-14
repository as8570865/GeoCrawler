package idv.ray.geoworker;

import java.io.IOException;

import org.json.JSONObject;
import org.jsoup.Jsoup;

public class Connector {

	private static final String MASTER_URL = "http://localhost:8080/GeoCrawler/";

	public static String getTask(JSONObject requestJson,String geoType) throws IOException {

		return Jsoup.connect(MASTER_URL+geoType).requestBody(requestJson.toString()).header("Content-Type", "application/json").post().text();

	}

}
