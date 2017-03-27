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
			if (result.isEmpty()) {
				System.out.println("該当する行はありませんでした。");
			} else {
				System.out.println("入力された文字列が現れた行番号は以下の通りです。");
				String answer = createAnswer(result);
				System.out.print(answer);
			}
		} catch (IOException e) {
			System.out.println("入出力エラーです。");
			e.printStackTrace();
		}
	}

	public static Map<Integer, List<Integer>> search(String query, String fileName) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			Map<Integer, List<Integer>> result = new LinkedHashMap<Integer, List<Integer>>();
			String str;
			int lineNumber = 1;
			while ((str = br.readLine()) != null) {
				if (str.indexOf(query) == -1) {
					continue;
				}
				List<Integer> charNumbers = new ArrayList<Integer>();
				int charNumber = 0;
				while ((charNumber = str.indexOf(query, charNumber)) != -1) {
					charNumber++;
					charNumbers.add(charNumber);
				}
				result.put(lineNumber, charNumbers);
				lineNumber++;
			}
			return result;
		}
	}

	public static String createAnswer(Map<Integer, List<Integer>> resultMap) {
		String answer = "";
		for (Map.Entry<Integer, List<Integer>> entry : resultMap.entrySet()) {
			int line = entry.getKey();
			List<Integer> chars = entry.getValue();
			answer += line + "行目 ";
			for (Integer c : chars) {
				answer += c + "文字目 ";
			}
			answer += "\n";
		}
		return answer;
	}
}
