package patternSearchUsingJava.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import patternSearchUsingJava.model.FileStructure;

public class App {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	// variable to keep a total count of all the occurences
	private static int GLOBAL_COUNT = 0;

	private static HashMap<String, List<FileStructure>> finalMap = new HashMap<>();

	// Source directory Path variable
	private static String PATH = StringUtils.EMPTY;

	// Search pattern
	private static String PATTERN = StringUtils.EMPTY;
	
	// Search pattern
	private static String XPATH = StringUtils.EMPTY;

	public static void main(String[] args) throws Exception {

		Properties prop = new Properties();
		InputStream inputStream = null;

		inputStream = new FileInputStream("config.properties");

		prop.load(inputStream);

		PATH = prop.getProperty("path");

		String searchOption = args[0];

		// The list of modules which were needed to be skipped.
		List<String> modulesToBeSkipped = new ArrayList<>();

		/*
		 * modulesToBeSkipped.add(".settings");
		 * modulesToBeSkipped.add("xenos-assembly");
		 * modulesToBeSkipped.add("xenos-bpf"); modulesToBeSkipped.add("xenos-dbtest");
		 * modulesToBeSkipped.add("xenos-mtest"); modulesToBeSkipped.add("xenos-srs");
		 * modulesToBeSkipped.add("xenos-tax"); modulesToBeSkipped.add("xenos-test");
		 */

		List<String> moduleNameList = namesOfSubdirectories();

		List<File> fileList = new ArrayList<File>();

		List<FileStructure> requiredFiles = new ArrayList<>();

		for (String moduleName : moduleNameList) {

			if (!modulesToBeSkipped.contains(moduleName)) {
				fileList = getFiles(moduleName);

				switch (searchOption) {
				case "regexSearch":
					PATTERN = prop.getProperty("");
					requiredFiles = search(fileList);
					break;
				case "xPathSearch":
					XPATH = prop.getProperty("");
					requiredFiles = parseXML(fileList,XPATH);
					break;
				}

					finalMap.put(moduleName, requiredFiles);
			}
		}

		ExcelWriter.writeIntoExcel(finalMap);

		// System.out.println("Total number of connection requests " + GLOBAL_COUNT);
	}

	/**
	 * API to list out all the subdirectories under a parent directory
	 * 
	 * @return list of subdirectories under a parent directory
	 */
	private static List<String> namesOfSubdirectories() {
		File file = new File(PATH);
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		return (Arrays.asList(directories));
	}

	/**
	 * This method is used to fetch the files from the sub-directory.
	 * 
	 * @param moduleName
	 *            the name of the module within which file need to be searched.
	 * @throws Exception
	 */
	private static List<File> getFiles(String moduleName) throws Exception {
		File directory = new File(PATH + moduleName.trim());
		// File directory = new
		// File("E:\\NRI-NAM\\NAMRUICodebase\\NAMRUI\\devel\\client\\JSPX\\views\\ref");

		List<File> fileList = new ArrayList<File>();
		// System.out.println(directory.getPath());
		Iterator<File> iter = null;
		if (directory.isDirectory()) {

			iter = FileUtils.iterateFiles(directory, new String[] { "jspx" }, true); // to find all files
																						// within the directory
																						// recursively with .js
																						// and .jspx extension

			while (iter.hasNext()) {
				File file = (File) iter.next();
				fileList.add(file);
			}
		}
		System.out.println("Module Name " + moduleName);

		return fileList;
		
	}

	/**
	 * This method is used to search a particular pattern in the list of files.
	 * 
	 * @param fileList
	 *            the list of files on a module.
	 * @return list of File Structure objects which has been populated according to
	 *         our need
	 * 
	 */

	private static List<FileStructure> search(List<File> fileList) {

		// pattern to find the required string
		Pattern nameSpaceRegex = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE);

		List<FileStructure> requiredList = new ArrayList<>();
		Matcher nameSpaceMatcher = nameSpaceRegex.matcher("");
		for (File file : fileList) {

			// files containing the word Query would be skipped
			// if(!file.getName().toLowerCase().contains("query")) {
			Path path = Paths.get(file.getPath());
			try (BufferedReader reader = Files.newBufferedReader(path, ENCODING);
					LineNumberReader lineReader = new LineNumberReader(reader);) {
				String line = null;
				while ((line = lineReader.readLine()) != null) {
					FileStructure fileStructure = new FileStructure();
					nameSpaceMatcher.reset(line); // reset the input
					if (nameSpaceMatcher.find()) {
						line = line.trim();
						/***********************/
						/***********************/
						/***********************/
						/* BE CAREFUL THIS PART */
						/***********************/
						/***********************/
						/***********************/

						fileStructure.setFileName(file.getPath());
						fileStructure.setLine(line.trim());
						fileStructure.setLineNo(lineReader.getLineNumber());
						GLOBAL_COUNT++;
						requiredList.add(fileStructure);
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
		}
		if (requiredList.size() != 0)
			System.out.println(requiredList.size());
		return requiredList;
	}

	/*
	 * private static List<FileStructure> parseXML(List<File> fileList) throws
	 * Exception { List<FileStructure> requiredList = new ArrayList<>();
	 * 
	 * for (File file : fileList) { String fileName = file.getName().toLowerCase();
	 * if (fileName.contains("detail") || fileName.contains("confirmation")) {
	 * //System.out.println(file.getPath().toString()); requiredList =
	 * XMLParserAndModifier.modifyXML(file.getPath().toString()); }
	 * 
	 * }
	 * 
	 * return requiredList; }
	 */

	private static List<FileStructure> parseXML(List<File> fileList,String XPATH) throws Exception {
		List<FileStructure> requiredList = new ArrayList<>();

		for (File file : fileList) {
			String fileName = file.getName().toLowerCase();
			if (fileName.contains("detail") || fileName.contains("confirmation")) {
				// System.out.println(file.getPath().toString());
				requiredList = XMLParserAndModifier.modifyXML(file.getPath().toString(),XPATH);
			}

		}

		return requiredList;
	}

}
