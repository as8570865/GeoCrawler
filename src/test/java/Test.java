import java.io.IOException;

import org.json.JSONObject;

import idv.ray.geoworker.Connector;

public class Test {

	public static void main(String[] args) throws IOException {

		JSONObject crawledResultJson=new JSONObject();

		crawledResultJson.put("taskId", "task");
		crawledResultJson.put("type", "task");
		crawledResultJson.put("result", "task");
		System.out.print("worker gets next task: " + Connector.getTask(crawledResultJson, "sos"));
	}

}
