package idv.ray.geocrawler.worker.geostandard;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WMTS implements GeoStandard{

	public boolean isGeoResource(String url) {
		url += "?" + "request=GetCapabilities&service=WMTS";
		Document doc;
		try {

			doc = Jsoup.connect(url).timeout(30 * 1000).get();
			if (doc.toString().contains("</Capabilities") ) {
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
