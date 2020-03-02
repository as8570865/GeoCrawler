package idv.ray.geocrawler.controller;

import java.lang.reflect.InvocationTargetException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import idv.ray.geocrawler.service.TaskService;
import idv.ray.geodata.Task;

@Controller
public class TaskController {

	private TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	// when first calling, set task.id=0
	@RequestMapping(value = "/{geoType}", method = RequestMethod.POST)
	public @ResponseBody String post(@RequestBody String req, @PathVariable("geoType") String geoType)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException, JSONException {

		if(!taskService.isValidGeotype(geoType))
			return "wrong geotype!!";

		JSONObject reqJson = new JSONObject(req);
		
		//if empty request when first calling, directly get next task
		if(reqJson.has("taskId")) {
			System.out.println("posting geourl...");
			taskService.post(geoType, reqJson);
		}

		//return a different geotype task
		Task t = taskService.getNext(taskService.getNextGeoType());
		System.out.println("get next task");
		
		if (!t.isValid()) {
			System.out.println("no prepared task!!");
			System.out.println("finished!!");
		}
		System.out.println("//////////////");
		return t.toJsonString();
		
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public @ResponseBody String get() {
		return taskService.getNextGeoType();
		
		
	}

}
