package idv.ray.geocrawler.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import idv.ray.geocrawler.javabean.geodata.Task;
import idv.ray.geocrawler.master.service.CrawlerService;


@Controller
public class TestController {

	CrawlerService crawlerService;

	public void setcrawlerService(CrawlerService crawlerService) {
		this.crawlerService = crawlerService;
		//System.out.println("setting task service in test controller");
	}

	@RequestMapping(value = "/{geoType}/test", method = RequestMethod.GET)
	public void test( @PathVariable("geoType") String geoType) {

		Task t=crawlerService.getNext(geoType);
		System.out.println("task: "+t.toString());
	}

}
