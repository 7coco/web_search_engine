import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class WebCrawler1 {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("URLを指定してください");
			System.exit(1);
		}
		try {
			LinkSearcher ls = new LinkSearcher(args[0]);
			URL url = new URL(args[0]);
			Set<URL> urls = ls.search(url);
			System.out.print(ls.createOutputStr(urls));
		} catch (IOException e) {
			System.out.println("入出力エラーです。");
			e.printStackTrace();
		}
	}
}
