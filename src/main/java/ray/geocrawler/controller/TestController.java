package ray.geocrawler.controller;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ray.geocrawler.dao.*;

@Controller
public class TestController {

	ResourceDao resourceDao;

	public void setResourceDao(ResourceDao resourceDao) {
		System.out.println("set resourceDao in test controller");
		this.resourceDao = resourceDao;

	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test() {

		System.out.println("executing query...");

		resourceDao.init();

	}
}
