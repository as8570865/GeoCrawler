package ray.geocrawler.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ray.geocrawler.dao.GeoDataDao;
import ray.geocrawler.model.GeoData;
import ray.geocrawler.model.Resource;
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

	@RequestMapping(value = "/{geoType}", method = RequestMethod.GET)
	public void test( @PathVariable("geoType") String geoType) {

		Task task=new Task(2);
		List<GeoData> l=new ArrayList<GeoData>();
		l.add(new Resource("test1"));
		l.add(new Resource("test2"));
		l.add(new Resource("test3"));
		l.add(new Resource("test2"));
		l.add(new Resource("test3"));
		
		taskService.post(geoType, task, l);
		Task t=taskService.getNext(geoType, task);
		System.out.println(t.toString());
	}

}
