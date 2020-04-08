package idv.ray.geocrawler.master.crawler.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import idv.ray.geocrawler.master.crawler.service.CrawlerService;
import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.httpbody.HttpBody;
import idv.ray.geocrawler.util.jsonserializer.JSONSerializer;

@Controller
public class TaskController {

	@Autowired
	private CrawlerService crawlerService;

	// when first calling, set task.id=0
	@RequestMapping(value = "/{geoType}", method = RequestMethod.POST)
	public @ResponseBody String post(@RequestBody String reqString, @PathVariable("geoType") String geoType)
			throws JsonParseException, JsonMappingException, IOException {

		if (!crawlerService.getGeoTypeSet().contains(geoType))
			return "wrong geotype!!";

		// parse request string to HttpBody object
		HttpBody httpBody = new JSONSerializer<HttpBody>().deserialize(reqString, HttpBody.class);
		crawlerService.post(geoType, httpBody);

		// return a different geotype task
		Task t = crawlerService.getNextTask();

		if (!t.isValid()) {
			System.out.println("no prepared task!!");
			System.out.println("finished!!");
		}
		System.out.println("//////////////");
		return new JSONSerializer<Task>().serialize(t);

	}

//	@RequestMapping(value = "/test", produces = "application/json")
//	public void test() {
//		System.out.println(crawlerService.isInitialized());
//	}

}
