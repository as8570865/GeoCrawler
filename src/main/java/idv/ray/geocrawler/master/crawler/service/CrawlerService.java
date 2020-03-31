package idv.ray.geocrawler.master.crawler.service;

import java.util.Set;

import idv.ray.geocrawler.javabean.geodata.Task;
import idv.ray.geocrawler.javabean.httpbody.HttpBody;

public interface CrawlerService {

	public void init();

	public Task getNextTask();

	public void post(String geoType, HttpBody reqBody);

	public Set<String> getGeoTypeSet();
}
