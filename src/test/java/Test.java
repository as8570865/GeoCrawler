import java.io.IOException;

import idv.ray.geostandard.GeoStandard;
import idv.ray.geostandard.SOS;

public class Test {

	public static void main(String[] args) throws IOException {

		GeoStandard sos=new SOS();
		if(sos.isGeoResource("https://uk-air.defra.gov.uk/sos-ukair/service?service=SOS&request=GetCapabilities")) 
			System.out.println("successed");
		else 
			System.out.println("fail");
	}

}
