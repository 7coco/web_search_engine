import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSearcher3 {
	private String fileName;

	public FileSearcher3(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 指定されたファイルから指定された文字列の位置を検索します。
	 * 
	 * @param regexp
	 *            検索パターン
	 * @return 検索結果。キー：行数、値：文字の位置リスト
	 * @throws IOException
	 */
	public Map<Integer, List<Integer>> search(Pattern regexp) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			Map<Integer, List<Integer>> result = new LinkedHashMap<Integer, List<Integer>>();
			String str;
			int lineNumber = 1;
			while ((str = br.readLine()) != null) {
				Matcher m = regexp.matcher(str);
				if (!m.find()) {
					lineNumber++;
					continue;
				}
				List<Integer> charNumbers = new ArrayList<Integer>();
				int charNumber = 0;
				while (m.find(charNumber)) {
					charNumber = m.start();
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
