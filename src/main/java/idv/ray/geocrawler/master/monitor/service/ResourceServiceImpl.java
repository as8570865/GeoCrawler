package idv.ray.geocrawler.master.monitor.service;

import java.awt.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import idv.ray.geocrawler.master.dao.ResourceDao;
import idv.ray.geocrawler.util.geostandard.GeoStandard;
import idv.ray.geocrawler.util.javabean.geodata.Resource;

@Configuration
@Transactional
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	@Qualifier("geoStandardMap")
	private Map<String, GeoStandard> geoStandardMap;

	public Resource getById(int id) {
		return resourceDao.get(id);
	}

	public void delete(Resource geoData) {
		// TODO Auto-generated method stub

	}

	public List getAll() {

		return null;
	}

	public void insert(Resource resource, String capabilitiesUrl) {
		GeoStandard gs = geoStandardMap.get(resource.getGeoType());
		if (gs.isGeoResource(capabilitiesUrl) && !resourceDao.containsLink(resource.getLink()))
			resourceDao.insert(resource);

	}

	public List getAll(int a) {
		// TODO Auto-generated method stub
		return null;
	}

}
