package ray.geocrawler.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ray.geocrawler.model.Task;
import ray.geocrawler.service.TaskService;

@Controller
@RequestMapping(value = "/task")
public class TaskController {
	TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService=taskService;
	}
	
	
	
	
}
