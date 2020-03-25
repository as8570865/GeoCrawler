package idv.ray.geocrawler.master.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.json.JSONException;
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

	private CrawlerService crawlerService;

	public TaskController(CrawlerService crawlerService) {
		this.crawlerService = crawlerService;
	}

	// when first calling, set task.id=0
	@RequestMapping(value = "/{geoType}", method = RequestMethod.POST)
	public @ResponseBody String post(@RequestBody String reqString, @PathVariable("geoType") String geoType)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException, JSONException, JsonParseException,
			JsonMappingException, IOException {

		if (!crawlerService.isValidGeotype(geoType))
			return "wrong geotype!!";

		// parse request string to HttpBody object
		HttpBody httpBody = new HttpBodySerializer().deserialize(reqString, HttpBody.class);
		crawlerService.post(geoType, httpBody);

		// return a different geotype task
		Task t = crawlerService.getNext(crawlerService.getNextGeoType());

		if (!t.isValid()) {
			System.out.println("no prepared task!!");
			System.out.println("finished!!");
		}
		System.out.println("//////////////");
		return new GeoDataSerializer().serialize(t);

	}

}
