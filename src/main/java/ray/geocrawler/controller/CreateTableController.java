package ray.geocrawler.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ray.geocrawler.dao.*;

@Controller
public class CreateTableController {

	private List<String> geoTypeList;
	private Map<String, GeoDataDao> daoMap;
	
	public void setGeoTypeList(List<String> ls) {
		
		this.geoTypeList=ls;

	}
	
	@RequestMapping(value = "/{geoData}/check", method = RequestMethod.GET)
	public void ResourceInit(@PathVariable("geoData") String geoData) {

//		GeoDataDao dao=get(geoData);
//		
//		for(String geoType:geoTypeList) {
//			GeoDataDao.setGeoType(geoType);
//			GeoDataDao.init();
//		}

		

	}
}
