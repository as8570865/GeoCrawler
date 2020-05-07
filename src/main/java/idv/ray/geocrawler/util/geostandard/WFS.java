package idv.ray.geocrawler.util.geostandard;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WFS implements RequestBasedGeoStandard {

	private static final String REQUEST_STATEMENT = "?request=GetCapabilities&service=WFS";
	
	@Bean
	public WFS wfs() {
		return new WFS();
	}

	public boolean isGeoResource(String capabilitiesUrl) {
		Document doc;
		try {

			doc = Jsoup.connect(capabilitiesUrl).timeout(30 * 1000).get();
			if (doc.toString().contains("</wfs:WFS_Capabilities") || doc.toString().contains("</WFS_Capabilities")) {
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
