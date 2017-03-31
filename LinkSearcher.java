import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkSearcher {
	private URL url;
	static private final Pattern LINK_REGEXP = Pattern
			.compile("<a href=\"([^\"]*html)\"|<frame[^<]src=\"([^\"]*\\.html?)");
	static private final Pattern TARGET_URL_REGEXP = Pattern.compile("https?");
	private Set<URL> referenceadUrls;

	public LinkSearcher(String url) throws MalformedURLException {
		this.url = new URL(url);
		this.referenceadUrls = new HashSet<URL>();
	}

	public Set<URL> search(URL url) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
			Set<URL> searchedUrls = new HashSet<URL>();
			String str;
			while ((str = br.readLine()) != null) {
				Matcher m = LINK_REGEXP.matcher(str);
				if (m.find()) {
					String link = (m.group(1) != null) ? m.group(1) : m.group(2);
					try {
						URL searchedUrl = this.url.toURI().resolve(link).toURL();
						if (searchedUrl.toString().startsWith(this.url.toString())
								&& TARGET_URL_REGEXP.matcher(this.url.toString()).find()) {
							searchedUrls.add(searchedUrl);
						}
					} catch (URISyntaxException e) {
						continue;
					}
				}
			}
			return result;
		}
	}

	public String createOutputStr(Set<URL> searchedUrls) {
		StringBuilder outputStr = new StringBuilder();
		for (URL url : searchedUrls) {
			outputStr.append(url).append("\n");
		}
		return outputStr.toString();
	}
}
