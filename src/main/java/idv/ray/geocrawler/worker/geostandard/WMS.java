package idv.ray.geocrawler.worker.geostandard;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import idv.ray.geocrawler.worker.core.Crawler;

public class WMS implements GeoStandard {

	String[] CapabilitiesTagArr = {};

	public boolean isGeoResource(String url) {
		url += "?" + "request=GetCapabilities&service=WMS";
		Document doc;
		try {

			doc = Jsoup.connect(url).userAgent(Crawler.USER_AGENT).timeout(30 * 1000).get();
			if (doc.toString().contains("</WMS_Capabilities") || doc.toString().contains("</WMT_MS_Capabilities")) {
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
