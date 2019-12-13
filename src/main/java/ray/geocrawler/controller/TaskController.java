package ray.geocrawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ray.geocrawler.service.TaskService;

@Controller
public class TaskController {
	TaskService taskService;
	
	public void setTaskService(TaskService taskService) {
		this.taskService=taskService;
		System.out.println("setting tas service");
	}
	
	@RequestMapping(value = "/init", produces = "application/json")
	public @ResponseBody String init() {
		taskService.init();
		return "successfully initialized";
	}
}
