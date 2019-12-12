package ray.geocrawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import ray.geocrawler.dao.*;

@Controller
@RequestMapping("/resource")
public class ResourceController extends GeoDataController{

	public void setGeoDataDao(GeoDataDao geoDataDao) {
		System.out.println("set resourceDao in test controller");
		this.geoDataDao = geoDataDao;

	}

}
