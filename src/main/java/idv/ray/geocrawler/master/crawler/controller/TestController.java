package idv.ray.geocrawler.master.crawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import idv.ray.geocrawler.master.crawler.service.CrawlerService;
import idv.ray.geocrawler.master.crawler.service.RegisterService;
import idv.ray.geocrawler.master.dao.ResourceDao;
import idv.ray.geocrawler.util.json.JSONSerializer;

@Controller
public class TestController {

	@Autowired
	private CrawlerService CS;

	@Autowired
	private RegisterService registerService;

	@Autowired
	private ResourceDao resourceDao;

	@RequestMapping(value = "/testTestController", produces = "application/json")
	public void test() throws Exception {
		System.out.println(JSONSerializer.serialize(CS.getQueryCondition("task")));
		System.out.println(CS.getQueryCondition("task"));
		System.out.println(CS.getQueryCondition("worker"));
		System.out.println(CS.getQueryCondition("resource"));
	}
}
