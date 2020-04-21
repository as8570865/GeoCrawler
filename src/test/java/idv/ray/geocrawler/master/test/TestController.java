package idv.ray.geocrawler.master.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {
	
	@Autowired
	private TestCrawlerService testCS;

	
	@RequestMapping(value = "/test/{geoType}", method = RequestMethod.GET)
	public void post( @PathVariable("geoType") String geoType) {
		
		testCS.test(geoType);
	}
}
