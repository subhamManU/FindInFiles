package patternSearchUsingJava.model;

import org.apache.commons.lang.StringUtils;

public class FileStructure {
	
	private int LineNo = 0;
	
	private String Line =  StringUtils.EMPTY;
	
	private String FileName = StringUtils.EMPTY;

	public int getLineNo() {
		return LineNo;
	}

	public void setLineNo(int lineNo) {
		LineNo = lineNo;
	}

	public String getLine() {
		return Line;
	}

	public void setLine(String line) {
		Line = line;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

}
