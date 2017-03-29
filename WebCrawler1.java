import java.io.IOException;
import java.util.List;

public class WebCrawler1 {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("URLを指定してください");
			System.exit(1);
		}
		try {
			LinkSearcher ls = new LinkSearcher(args[0]);
			List<String> links = ls.search();
			System.out.print(ls.createOutputStr(links));
		} catch (IOException e) {
			System.out.println("入出力エラーです。");
			e.printStackTrace();
		}
	}
}