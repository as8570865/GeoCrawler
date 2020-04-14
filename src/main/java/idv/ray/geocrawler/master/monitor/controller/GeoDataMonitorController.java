package idv.ray.geocrawler.master.monitor.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;

@Controller
public interface GeoDataMonitorController<T> {

	public void insert(T geoData,Optional<String> capabilitiesUrl);
}
