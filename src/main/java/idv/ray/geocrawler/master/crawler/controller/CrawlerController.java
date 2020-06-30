package idv.ray.geocrawler.master.crawler.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import idv.ray.geocrawler.exception.GeoCrawlerException.FailToInitializeException;
import idv.ray.geocrawler.exception.IncorrectRegisterationException.UserNameUsedException;
import idv.ray.geocrawler.exception.IncorrectRegisterationException.WorkerExistsException;
import idv.ray.geocrawler.master.crawler.service.CrawlerService;
import idv.ray.geocrawler.master.crawler.service.RegisterService;
import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.httpbody.CrawlerHttpBody;
import idv.ray.geocrawler.util.javabean.worker.Worker;
import idv.ray.geocrawler.util.json.GenericJsonSerializer;

@Controller
public class CrawlerController {

	@Autowired
	private CrawlerService crawlerService;

	@Autowired
	private RegisterService registerService;

	// when first calling, set task.id=0
	@RequestMapping(value = "/{geoType}", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody String reqString, @PathVariable("geoType") String geoType,
			HttpServletRequest request) {

		try {
			CrawlerHttpBody httpBody = CrawlerHttpBody.deserialize(reqString);
			// source task = 0 means there is no source task processed, so no need to post
			if (httpBody.getSrcTask().getId() != 0)
				crawlerService.post(geoType, httpBody);
		} catch (IOException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		// return a different geotype task
		Optional<Task> ot = crawlerService.getNextTask();
		Task t;
		String json;
		try {
			t = ot.get();
		} catch (NoSuchElementException e) {
			return new ResponseEntity<String>("no next task", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			json = t.serialize();
			return new ResponseEntity<String>(json, HttpStatus.OK);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/init", produces = "application/json")
	public ResponseEntity<String> init() {

		try {
			crawlerService.init();
			return new ResponseEntity<String>("successfully initializing", HttpStatus.OK);

		} catch (FailToInitializeException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

	@RequestMapping(value = "/geoTypeSet", method = RequestMethod.GET)
	public ResponseEntity<String> getGeoTypeSet() {

		try {
			return new ResponseEntity<String>(GenericJsonSerializer.serialize(crawlerService.getGeoTypeSet()),
					HttpStatus.OK);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody String reqString, HttpServletRequest request) {

		try {
			Worker w = Worker.deserialize(reqString);
			w.setIpAddress(request.getRemoteAddr());
			registerService.regeister(w);
			return new ResponseEntity<String>("registering worker: " + w.toString() + " successfully", HttpStatus.OK);

		} catch (WorkerExistsException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);

		} catch (IOException | UserNameUsedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public @ResponseBody String test(@RequestBody String reqString) {
		return "successfully post!";
	}

}
