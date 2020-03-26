package idv.ray.geocrawler.master.service;

import java.util.List;
import java.util.Map;

import idv.ray.geocrawler.javabean.geodata.Task;
import idv.ray.geocrawler.javabean.httpbody.HttpBody;

public interface CrawlerService {

	public void init(Map<String, String> tableSchemaMap, Map<String, List<String>> seedMap);

	public boolean isInitialized();

	public Task getNextTask();

	public void post(String geoType, HttpBody reqBody);

}
