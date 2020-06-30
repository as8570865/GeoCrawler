package idv.ray.geocrawler.master.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import idv.ray.geocrawler.master.dao.ResourceDao;
import idv.ray.geocrawler.master.dao.TaskDao;

@Service
public class TestCrawlerService  {

	@Autowired
	private TaskDao tDao;
	@Autowired
	private ResourceDao rDao;

	public void test(String geoType) {
//		Task t=tDao.getReady(geoType);
//		System.out.println(t);
	}
}
