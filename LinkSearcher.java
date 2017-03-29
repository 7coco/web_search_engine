import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkSearcher {
	private URL url;
	static private final Pattern LINK_REGEXP = Pattern.compile("<a href=\"([^\"]*)\"|src=\"([^\"]*)");

	public LinkSearcher(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	public List<String> search() throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
			List<String> result = new ArrayList<String>();
			String str;
			while ((str = br.readLine()) != null) {
				Matcher m = LINK_REGEXP.matcher(str);
				if (m.find()) {
					String link = (m.group(1) != null) ? m.group(1) : m.group(2);
					result.add(link);
				}
			}
			return result;
		}
	}

	public String createOutputStr(List<String> searchedLinks) {
		StringBuilder outputStr = new StringBuilder();
		for (String link : searchedLinks) {
			outputStr.append(link).append("\n");
		}
		return outputStr.toString();
	}
}