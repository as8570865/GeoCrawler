package idv.ray.geocrawler.master.crawler.controller;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import idv.ray.geocrawler.master.hiberdao.ResourceDao;
import idv.ray.geocrawler.util.javabean.geodata.Resource;

@Controller
public class TestController {

	@Autowired
	@Qualifier("hiberResourceDao")
	ResourceDao resourceDao;

	@Autowired
	ApplicationContext actx;
	
	@Autowired
	SessionFactory sf;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test() {

		Resource r = new Resource(101, "abc", 1, "sos", 0);
		resourceDao.insert(r);

	}

	@PostConstruct
	public void init() {
		for (String s : actx.getBeanDefinitionNames()) {
			System.out.println(s);
		}
		

	}

}
