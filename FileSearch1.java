import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileSearch1 {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("検索したい文字列と検索対象テキストファイル名を空白区切で入力してください.");
			System.out.println("例）hoge test.txt");
			System.exit(1);
		}

		try {
			Map<Integer, List<Integer>> result = search(args[0], args[1]);
			if (result.size() > 0) {
				System.out.println("入力された文字列が現れた行番号は以下の通りです。");
				result.forEach((line, chars) -> {
					String answer;
					answer = line + "行目";
					for (Integer c : chars) {
						answer += c + "文字目 ";
					}
					System.out.println(answer);
				});
			} else {
				System.out.println("該当する行はありませんでした。");
			}
		} catch (IOException e) {
			System.out.println("入出力エラーです。");
		}
	}

	public static Map<Integer, List<Integer>> search(String query, String fileName) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			Map<Integer, List<Integer>> result = new LinkedHashMap<Integer, List<Integer>>();
			String str;
			int lineNumber = 1;
			while ((str = br.readLine()) != null) {
				if (str.indexOf(query) != -1) {
					List<Integer> charNumbers = new ArrayList<Integer>();
					int charNumber = 0;
					while (str.indexOf(query, charNumber) != -1) {
						charNumber = str.indexOf(query, charNumber) + 1;
						charNumbers.add(charNumber);
					}
					result.put(lineNumber, charNumbers);
				}
				lineNumber++;
			}
			return result;
		}
	}
}
