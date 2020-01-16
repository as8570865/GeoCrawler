package idv.ray.geostandard;

public interface GeoStandard {
	// if the url is a geo-resource, it can be directly stored into DB
	public boolean isGeoResource(String url);

}
