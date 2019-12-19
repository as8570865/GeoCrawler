package ray.geocrawler.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ray.geocrawler.dao.GeoDataDao;
import ray.geocrawler.model.Task;
import ray.geocrawler.service.TaskService;

@Controller
@RequestMapping(value = "/test")
public class TestController {

	TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
		System.out.println("setting task service in test controller");
	}

	@RequestMapping(value = "/{geoType}/next", method = RequestMethod.GET)
	public void init(@RequestBody String reqBody,@PathVariable("geoType") String geoType) {

//		Task task=taskService.getNext(geoType,new Task());
//		
//		while(task!=null) {
//			task=taskService.getNext(geoType,task);
//			System.out.println(task.toString());
//		}		
	}

}
