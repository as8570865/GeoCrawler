package idv.ray.geocrawler.master.crawler.service;

import java.util.Optional;
import java.util.Set;

import idv.ray.geocrawler.exception.GeoCrawlerException.FailToInitializeException;
import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.httpbody.CrawlerHttpBody;

public interface CrawlerService {

	public void init() throws FailToInitializeException;

	public Optional<Task> getNextTask();

	public void post(String geoType, CrawlerHttpBody reqBody);

	public Set<String> getGeoTypeSet();

}
