package idv.ray.geocrawler.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import idv.ray.geocrawler.master.service.CrawlerService;

@Controller
public class InitController {
	CrawlerService crawlerService;

	public InitController(CrawlerService crawlerService) {
		this.crawlerService = crawlerService;
	}

	@RequestMapping(value = "/init", produces = "application/json")
	public @ResponseBody String init() {
		crawlerService.init();
		return "successfully initialized";
	}
}
