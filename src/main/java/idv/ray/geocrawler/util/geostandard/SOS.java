package idv.ray.geocrawler.util.geostandard;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SOS implements RequestBasedGeoStandard {

	private static final String REQUEST_STATEMENT = "?request=GetCapabilities&service=SOS";

	@Bean
	public SOS sos() {
		return new SOS();
	}

	public boolean isGeoResource(String capabilitiesUrl) {

		Document doc;

		try {
			doc = Jsoup.connect(capabilitiesUrl).timeout(10 * 1000).ignoreHttpErrors(true).get();

			if (doc.toString().contains("</sos:Capabilities") || doc.toString().contains("</Capabilities")
					|| doc.toString().contains("</Capability")) {
				System.out.println("this is geoResource!!");
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		}

		return false;
	}

	@Override
	public String getCapabilitiesUrl(String url) {
		return url + REQUEST_STATEMENT;
	}

}
