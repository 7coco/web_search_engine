import java.io.*;

public class FileSearch1
{
	public static void main(String[] args) {
		if(args.length != 2){
			System.out.println("検索したい文字列と検索対象テキストファイル名を空白区切で入力してください.");
			System.out.println("例）hoge test.txt");
			System.exit(1);
		}

		try {
			String result = search(args[0], args[1]);
			if(result.length() > 0){
				System.out.println("入力された文字列が現れた行番号は以下の通りです。");
				System.out.println(result);
			}else{
				System.out.println("該当する行はありませんでした。");
			}
		} catch (IOException e) {
			System.out.println("入出力エラーです。");
		}
	}

	public static String search(String query, String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		String result = "";
		String str;
		int lineNumber = 1;
		try{
			while((str = br.readLine()) != null){
				if (str.indexOf(query) != -1){
					result += "\n";
				 	result += lineNumber + "行目の";
					int charNumber = 0;
					while(str.indexOf(query) != -1){
						charNumber += str.indexOf(query);
						result += (charNumber + 1) + "文字目 ";
						str = str.substring(str.indexOf(query) + query.length());
						charNumber += query.length();
						System.out.println(str);
						System.out.println(charNumber);
					}
				}
				lineNumber++;
			}
		}finally{
			br.close();
		}
		return result;
  	}
}
