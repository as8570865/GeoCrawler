package idv.ray.geostandard;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SOS implements GeoStandard {

	public boolean isGeoResource(String url) {
		url = url.split("\\?")[0];
		url += "?" + "request=GetCapabilities&service=SOS";

		Document doc;
		try {
			doc = Jsoup.connect(url).timeout(10 * 1000).get();
			if (doc.toString().contains("</sos:Capabilities>") || doc.toString().contains("</Capabilities>")) {
				System.out.println("this is geoResource!!");
				return true;
			}

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();

		}

		return false;
	}

}
