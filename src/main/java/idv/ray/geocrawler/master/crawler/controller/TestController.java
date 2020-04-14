package idv.ray.geocrawler.master.crawler.controller;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import idv.ray.geocrawler.master.monitor.service.TaskService;

@Controller
public class TestController {

	@Autowired
	private TaskService taskService;

//	@Autowired
//	@Qualifier("hiberTaskDao")
//	TaskDao taskDao;

	@Autowired
	private ApplicationContext actx;

	@Autowired
	private SessionFactory sf;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test() {
		taskService.getAll();
		

	}

	@PostConstruct
	public void init() {
//		for (String s : actx.getBeanDefinitionNames()) {
//			System.out.println(s);
//		}

		
	}

}
