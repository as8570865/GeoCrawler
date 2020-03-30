package idv.ray.geocrawler.master.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import idv.ray.geocrawler.javabean.geodata.Task;
import idv.ray.geocrawler.javabean.httpbody.HttpBody;
import idv.ray.geocrawler.javabean.json.GeoDataSerializer;
import idv.ray.geocrawler.javabean.json.HttpBodySerializer;
import idv.ray.geocrawler.master.service.CrawlerService;

@Controller
public class TaskController {

	@Autowired
	private CrawlerService crawlerService;

	@Autowired
	@Qualifier("tableSchemaMap")
	private Map<String, String> tableSchemaMap;

	@Autowired
	@Qualifier("seedMap")
	private Map<String, List<String>> seedMap;
	
	@PostConstruct
	public void init() {
		crawlerService.init(tableSchemaMap, seedMap);
	}	

	// when first calling, set task.id=0
	@RequestMapping(value = "/{geoType}", method = RequestMethod.POST)
	public @ResponseBody String post(@RequestBody String reqString, @PathVariable("geoType") String geoType)
			throws JsonParseException, JsonMappingException, IOException {

		if (!seedMap.containsKey(geoType))
			return "wrong geotype!!";

		// parse request string to HttpBody object
		HttpBody httpBody = new HttpBodySerializer().deserialize(reqString, HttpBody.class);
		crawlerService.post(geoType, httpBody);

		// return a different geotype task
		Task t = crawlerService.getNextTask();

		if (!t.isValid()) {
			System.out.println("no prepared task!!");
			System.out.println("finished!!");
		}
		System.out.println("//////////////");
		return new GeoDataSerializer().serialize(t);

	}

//	@RequestMapping(value = "/init", produces = "application/json")
//	public @ResponseBody String init() {
//		
//			crawlerService.init(tableSchemaMap, seedMap);
//			return "successfully initialized!";
//		
//	}

	@RequestMapping(value = "/test", produces = "application/json")
	public void test() {
		System.out.println(crawlerService.isInitialized());
	}

}
