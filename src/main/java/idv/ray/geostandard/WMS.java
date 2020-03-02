package idv.ray.geostandard;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WMS implements GeoStandard {

	@Override
	public boolean isGeoResource(String url) {
		url = url.split("\\?")[0];
		url += "?" + "request=GetCapabilities&service=WMS";
		Document doc;
		try {
			System.out.println("modified url is:" + url);
			doc = Jsoup.connect(url).timeout(30 * 1000).get();
			if (doc.toString().contains("</wms:Capabilities>") || doc.toString().contains("</Capabilities>")
					|| doc.toString().contains("</Capability>")) {
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
