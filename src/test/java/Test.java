import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class Test {

	private static final String USER_AGENT = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";

	public static void main(String[] args) throws IOException {
		
//		Response resp = Jsoup.connect("https://www.geog.illinois.edu/people/shaowen").timeout(10 * 1000).execute();
//		String contentType = resp.contentType();
//		
//		System.out.println("content type: "+contentType);
		
		String urlString="https://www.geog.illinois.edu/people/shaowen";
		System.out.println("url: " + urlString);
		HttpURLConnection urlConnection = (HttpURLConnection) new URL(urlString).openConnection();
		System.out.println("open connection finished...");
		String contentType=urlConnection.getContentType();
		System.out.println("content type: " + contentType);
	}

}
