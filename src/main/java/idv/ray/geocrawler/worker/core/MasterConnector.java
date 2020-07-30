package idv.ray.geocrawler.worker.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;

import idv.ray.geocrawler.util.javabean.geodata.GeoData;
import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.httpbody.CrawlerHttpBody;
import idv.ray.geocrawler.util.javabean.worker.Worker;
import idv.ray.geocrawler.util.json.JSONSerializer;
import idv.ray.geocrawler.worker.exception.GeoCrawlerWorkerException;
import idv.ray.geocrawler.worker.exception.GeoCrawlerWorkerException.CrawledResultException;
import idv.ray.geocrawler.worker.exception.GeoCrawlerWorkerException.FailToGetNextTaskException;
import idv.ray.geocrawler.worker.exception.GeoCrawlerWorkerException.FailToRegisterException;
import idv.ray.geocrawler.worker.exception.GeoCrawlerWorkerException.MasterException;

public class MasterConnector {

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		masterUrl = (String) context.getBean("masterUrl");
	}

	private static String masterUrl;

	public static Task SendCrawledResultAndGetNextTask(CrawlerHttpBody reqBody, String geoType)
			throws MasterException, FailToGetNextTaskException, CrawledResultException {

		String returnString;
		try {
			System.out.println("reqBody: " + reqBody.serialize());
			returnString = Jsoup.connect(masterUrl + "/" + geoType).timeout(300 * 1000).requestBody(reqBody.serialize())
					.header("Content-Type", "application/json").post().text();

		} catch (JsonProcessingException e) {
			throw new GeoCrawlerWorkerException.CrawledResultException(
					"Fail to serialize the request body sent from this worker.", e);

		} catch (IOException e) {
			throw new GeoCrawlerWorkerException.FailToGetNextTaskException(
					"Fail to send the request to master to get the next task.", e.fillInStackTrace());
		}

		try {
			Task t = (Task) GeoData.deserialize(returnString, Task.class);
			t.setWorker(WorkerRunner.WORKER);
			return t;

		} catch (IOException e) {
			throw new GeoCrawlerWorkerException.MasterException(
					"Fail to deserialize the next task, the problem may come from master.", e);
		}

	}

	public static void register(Worker worker) throws FailToRegisterException {

		String reqBody = "";

		try {
			reqBody = worker.serialize();
		} catch (JsonProcessingException e) {
			throw new GeoCrawlerWorkerException.FailToRegisterException("Fail to setialize this worker.", e);
		}

		try {
			String returnString = Jsoup.connect(masterUrl + "/" + "register").timeout(1000 * 10).requestBody(reqBody)
					.header("Content-Type", "application/json").post().text();
			System.out.println("Send request: " + masterUrl + "/" + "register");
			System.out.println("Response: " + returnString);

		} catch (IOException e) {
			throw new GeoCrawlerWorkerException.FailToRegisterException(
					"Fail to register. Please make sure the worker name has not been used, or check the master server.",
					e);
		}

	}

	public static Set<String> getGeoTypeSet() throws MasterException {
		String json;
		try {
			json = Jsoup.connect(masterUrl + "/" + "geoTypeSet").timeout(1000 * 10).get().text();

		} catch (IOException e) {
			throw new GeoCrawlerWorkerException.MasterException(
					"Fail to get the geotype set from master, the problem may come from master.", e);
		}
		try {
			return JSONSerializer.deserialize(json, HashSet.class);

		} catch (IOException e) {
			throw new GeoCrawlerWorkerException.MasterException(
					"Fail to deserialize the geotype set from master, the problem may come from master.", e);
		}
	}

}
