package ray.geocrawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ray.geocrawler.service.TaskService;

@Controller
public class InitController {
	TaskService taskService;

	public InitController(TaskService taskService) {
		this.taskService=taskService;
	}

	@RequestMapping(value = "/init", produces = "application/json")
	public @ResponseBody String init() {
		taskService.init();
		return "successfully initialized";
	}
}
