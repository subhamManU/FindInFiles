package patternSearchUsingJava.launcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class JSPXParser {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	public static void main(String[] args) throws FileNotFoundException {

		String filePath = "E:\\NRI-NAM\\XMLParserTest\\Sample.txt";

		ArrayList<String> lines = new ArrayList<>();
		int i_td = 0;
		int i_close_td = 0;
		Scanner sc = new Scanner(new File(filePath));
		String line = null;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (line.contains("<tr>")) {
				line = line.replaceAll("<tr>", "");
			}
			if (line.contains("<td>")) {
				if (i_td == 0) {
					line = line.replaceAll("<td>", "<li>");
					i_td = 1;
				} else {
					line = line.replace("<td>", "");
					i_td = 0;
				}
			}
			if (line.contains("</td>")) {
				if (i_close_td == 0) {
					line = line.replaceAll("</td>", "");
					i_close_td = 1;
				} else {
					line = line.replaceAll("</td>", "</li>");
					i_close_td = 0;
				}
			}
			if (line.contains("</tr>")) {
				line = line.replaceAll("</tr>", "");
				
				
			}
			lines.add(line);
			
			//lines.removeAll(Collections.singleton(""));

		}
		sc.close();
		// System.out.println(lines);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String lineToWrite : lines) {
				writer.write(lineToWrite);
				writer.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
