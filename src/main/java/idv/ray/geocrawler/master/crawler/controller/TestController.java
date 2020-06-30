package idv.ray.geocrawler.master.crawler.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import idv.ray.geocrawler.master.crawler.service.CrawlerService;
import idv.ray.geocrawler.master.crawler.service.RegisterService;
import idv.ray.geocrawler.util.javabean.worker.Worker;

@Controller
public class TestController {

	@Autowired
	private CrawlerService CS;

	@Autowired
	private RegisterService registerService;

	@RequestMapping(value = "/test/{name}/{ipAddress}", produces = "application/json")
	public void test(HttpServletRequest request, @PathVariable("name") String name,
			@PathVariable("ipAddress") String ipAddress) throws Exception {

		Worker w = new Worker(name);
		w.setIpAddress(ipAddress);
		registerService.regeister(w);

		System.out.println(w.serialize());

	}
}
