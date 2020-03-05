package idv.ray.geostandard;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SOS implements GeoStandard {

	public boolean isGeoResource(String url) {

		url += "?" + "request=GetCapabilities&service=SOS";

		Document doc;
		try {
			doc = Jsoup.connect(url).timeout(10 * 1000).ignoreHttpErrors(true).get();
			return isValidCapabilities(doc);

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();

		}

		return false;
	}

	private boolean isValidCapabilities(Document doc) {
		if (doc.toString().contains("</sos:Capabilities") || doc.toString().contains("</Capabilities") || doc.toString().contains("</Capability")) {
			System.out.println("this is geoResource!!");
			return true;
		}
		return false;
	}

}
