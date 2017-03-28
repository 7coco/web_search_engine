import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileSearcher {
	private String fileName;

	public FileSearcher() {
		System.out.println("ファイル名を指定してください。");
	}

	public FileSearcher(String f) {
		fileName = f;
	}

	/**
	 * 指定されたファイルから指定された文字列の位置を検索します。
	 * 
	 * @param query
	 *            検索文字列
	 * @param fileName
	 *            検索対象のファイル名
	 * @return 検索結果。キー：行数、値：文字の位置リスト
	 * @throws IOException
	 */
	public Map<Integer, List<Integer>> search(String query) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			Map<Integer, List<Integer>> result = new LinkedHashMap<Integer, List<Integer>>();
			String str;
			int lineNumber = 1;
			while ((str = br.readLine()) != null) {
				if (str.indexOf(query) == -1) {
					lineNumber++;
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

	/**
	 * 引数のマップから最終的に出力する文字列を作ってそれを返します。
	 * 
	 * @param resultMap
	 *            検索結果のマップ
	 * @return 出力するべき文字列
	 */
	public String createOutputStr(Map<Integer, List<Integer>> resultMap) {
		StringBuilder answer = new StringBuilder();
		for (Map.Entry<Integer, List<Integer>> entry : resultMap.entrySet()) {
			int line = entry.getKey();
			List<Integer> chars = entry.getValue();
			answer.append(line).append("行目 ");
			for (Integer c : chars) {
				answer.append(c).append("文字目 ");
			}
			answer.append("\n");
		}
		return answer.toString();
	}
}
