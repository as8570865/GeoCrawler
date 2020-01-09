package idv.ray.geourl;

public interface GeoStandard {
	// if the url is a geo-resource, it can be directly stored into DB
	public boolean isGeoResource();

	// if the url is a catalogue, go further to retrieve geo-resource from cata
	public boolean isCatalogue();

	// retrieve geo-resource from cata
	public String getGeoResourceFromCata();
}
