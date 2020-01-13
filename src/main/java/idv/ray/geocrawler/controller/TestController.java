package idv.ray.geocrawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import idv.ray.geocrawler.service.TaskService;
import idv.ray.geodata.Task;

@Controller
public class TestController {

	TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
		//System.out.println("setting task service in test controller");
	}

	@RequestMapping(value = "/{geoType}/test", method = RequestMethod.GET)
	public void test( @PathVariable("geoType") String geoType) {

		Task t=taskService.getNext(geoType);
		System.out.println("task: "+t.toString());
	}

}
