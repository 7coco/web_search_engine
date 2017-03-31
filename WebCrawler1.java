import java.io.IOException;
import java.net.URL;
import java.util.List;

public class WebCrawler1 {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("URLを指定してください");
			System.exit(1);
		}
		try {
			LinkSearcher ls = new LinkSearcher(args[0]);
			List<URL> urls = ls.search();
			System.out.print(ls.createOutputStr(urls));
		} catch (IOException e) {
			System.out.println("入出力エラーです。");
			e.printStackTrace();
		}
	}
}
