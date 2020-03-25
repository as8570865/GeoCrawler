package idv.ray.geocrawler.master.service;

import idv.ray.geocrawler.javabean.geodata.Task;
import idv.ray.geocrawler.javabean.httpbody.HttpBody;

public interface CrawlerService {

	public void init();

	public boolean isValidGeotype(String geoType);

	public Task getNext(String geoType);
	
	public String getNextGeoType();

	public void post(String geoType, HttpBody reqBody) ;

}
