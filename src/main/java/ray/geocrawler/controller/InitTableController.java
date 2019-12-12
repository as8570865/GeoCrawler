package ray.geocrawler.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ray.geocrawler.dao.GeoDataDao;

@Controller
@RequestMapping(value = "/init")
public class InitTableController extends GeoDataController {

	private Map<String, String> tableSchemaMap;
	private List<String> geoTypeList;

	private GeoDataDao rDao;
	private GeoDataDao tDao;

	public void setTableSchemaMap(Map<String, String> tableSchemaMap) {
		this.tableSchemaMap = tableSchemaMap;

	}

	public void setGeoTypeList(List<String> ls) {
		this.geoTypeList = ls;
	}

	public void init() {
		rDao = this.daoMap.get("resource");
		tDao = this.daoMap.get("task");
		rDao.setTableSchema(tableSchemaMap.get("resource"));
		tDao.setTableSchema(tableSchemaMap.get("task"));
		for (String geoType : geoTypeList) {
			rDao.setGeoType(geoType);
			tDao.setGeoType(geoType);
			rDao.init();
			tDao.init();
		}
	};
}
