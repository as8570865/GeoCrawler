package idv.ray.geocrawler.util.geostandard;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WMPS implements RequestBasedGeoStandard {

	private static final String REQUEST_STATEMENT = "?request=GetCapabilities&service=WMTS";

	public boolean isGeoResource(String capabilitiesUrl) {

		Document doc;
		try {

			doc = Jsoup.connect(capabilitiesUrl).timeout(30 * 1000).get();
			if (doc.toString().contains("<Capabilities")) {
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

	@Override
	public String getCapabilitiesUrl(String url) {
		return url+REQUEST_STATEMENT;
	}
}
