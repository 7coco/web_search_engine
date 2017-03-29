import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkSearcher {
	private URL url;

	public LinkSearcher(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	public String search(URL url, Pattern regexp) throws IOException {
		InputStream is = url.openStream();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String str;
			while ((str = br.readLine()) != null) {
				// ここでsearch を行う。
			}
		}

	}
}