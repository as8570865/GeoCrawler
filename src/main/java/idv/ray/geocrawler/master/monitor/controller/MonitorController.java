package idv.ray.geocrawler.master.monitor.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;

import idv.ray.geocrawler.master.monitor.service.ResourceService;
import idv.ray.geocrawler.master.monitor.service.TaskService;
import idv.ray.geocrawler.master.monitor.service.WorkerService;
import idv.ray.geocrawler.util.javabean.geodata.Resource;
import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.httpbody.QueryResult;
import idv.ray.geocrawler.util.javabean.worker.Worker;
import idv.ray.geocrawler.util.json.GeoDataMixin;
import idv.ray.geocrawler.util.json.JSONSerializer;

@Controller
@RequestMapping("/monitor")
public class MonitorController {
	private static final String DEFAULT_MAX_LEVEL_FOR_QUERY = "999";
	private static final String DEFAULT_MIN_LEVEL_FOR_QUERY = "0";
	private static final String DEFAULT_ID_FOR_QUERY = "0";

	@Autowired
	@Qualifier("tomcatPort")
	private String port;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private WorkerService workerService;

	@CrossOrigin
	@RequestMapping(value = "/{geoDataType}", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<String> get(@PathVariable("geoDataType") String geoDataType,
			@RequestParam(value = "id", required = false, defaultValue = DEFAULT_ID_FOR_QUERY) int id,
			@RequestParam(value = "geoType", required = false) String geoType,
			@RequestParam(value = "processorIp", required = false) String processorIp,
			@RequestParam(value = "workerName", required = false) String workerName,
			@RequestParam(value = "minLevel", required = false, defaultValue = DEFAULT_MIN_LEVEL_FOR_QUERY) int minLevel,
			@RequestParam(value = "maxLevel", required = false, defaultValue = DEFAULT_MAX_LEVEL_FOR_QUERY) int maxLevel,
			@RequestParam(value = "status", required = false) Task.Status status, HttpServletRequest request,
			Model model) {

		List resulrList;
		String ru = request.getRequestURI();

		try {
			GeoDataMixin.SrcTaskSerializer.QUERY_GEODATA_BY_ID_URL = "http://"
					+ InetAddress.getLocalHost().getHostAddress() + ":" + port + ru.substring(0, ru.lastIndexOf("/"))
					+ "/task?id=";
		} catch (UnknownHostException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (geoDataType.equals(Task.TYPE_NAME.toLowerCase()))
			resulrList = taskService.get(id, geoType, processorIp, workerName, minLevel, maxLevel, status);
		else if (geoDataType.equals(Resource.TYPE_NAME.toLowerCase()))
			resulrList = resourceService.get(id, geoType, processorIp, workerName, minLevel, maxLevel);
		else
			return new ResponseEntity<String>(
					"Wrong direction. Please type \"task\", \"resource\" or \"worker\" after \"monitor/\".",
					HttpStatus.BAD_REQUEST);

		QueryResult qr = new QueryResult(resulrList);
		String returnJson;
		try {
			returnJson = qr.GeoDataSerialize();

		} catch (JsonProcessingException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(returnJson, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/worker", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<String> get(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "ipAddress", required = false) String ipAddress) {

		List resulrList = workerService.get(name, ipAddress);

		QueryResult qr = new QueryResult(resulrList);
		String returnJson;
		try {
			returnJson = qr.serialize();
		} catch (JsonProcessingException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(returnJson, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/worker/idle", produces = "application/json")
	public ResponseEntity<String> getIdleWorkers(@RequestParam(value = "duration") int idleDurationHrs)
			throws JsonProcessingException {
		List<Worker> workers = workerService.getIdleWorkers(Duration.ofHours(idleDurationHrs));
		return new ResponseEntity<String>(JSONSerializer.serialize(workers), HttpStatus.OK);
	}
}
