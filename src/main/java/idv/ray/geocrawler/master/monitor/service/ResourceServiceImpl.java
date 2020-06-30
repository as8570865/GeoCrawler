package idv.ray.geocrawler.master.monitor.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import idv.ray.geocrawler.master.dao.ResourceDao;
import idv.ray.geocrawler.util.geostandard.GeoStandard;
import idv.ray.geocrawler.util.javabean.geodata.Resource;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	@Qualifier("geoStandardMap")
	private Map<String, GeoStandard> geoStandardMap;

	@Override
	public Resource getById(int id) {
		return resourceDao.getById(id);
	}

	@Override
	public void delete(Resource geoData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(Resource resource, String capabilitiesUrl) {
		GeoStandard gs = geoStandardMap.get(resource.getGeoType());
		if (gs.isGeoResource(capabilitiesUrl) && !resourceDao.contains(resource))
			resourceDao.insert(resource);

	}

	@Override
	public List<Resource> get(int id, String geoType, String processorIp, String workerName, int minLevel,
			int maxLevel) {

		return resourceDao.get(id, geoType, processorIp, workerName, minLevel, maxLevel);
	}

}
