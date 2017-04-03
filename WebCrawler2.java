import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebCrawler2 {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("URLを指定してください");
			System.exit(1);
		}
		try {
			LinkSearcher2 ls = new LinkSearcher2(args[0]);
			List<String> initializeList = new ArrayList<>(Arrays.asList(""));
			List<String> links = ls.searchAllReferenceableLinks(initializeList);
			List<URL> urls = ls.makeLinksToURL(links);
			System.out.print(ls.createOutputStr(urls));
		} catch (IOException e) {
			System.out.println("入出力エラーです。");
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
