package patternSearchUsingJava.launcher;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import patternSearchUsingJava.model.FileStructure;

public class ExcelWriter {

	public static void writeIntoExcel(HashMap<String, List<FileStructure>> finalMap) {

		// The header columns
		String[] columns = { "Sl No.", "Line No", "Line", "File Name" };

		Workbook workbook = new XSSFWorkbook();

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		// headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 16);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		/*
		 * CellStyle invalidClassStyle = workbook.createCellStyle();
		 * invalidClassStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.
		 * getIndex());
		 * 
		 * 
		 * CellStyle trueConnectionStyle = workbook.createCellStyle();
		 * trueConnectionStyle.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.getIndex(
		 * ));
		 */
		try {
			for (String moduleName : finalMap.keySet()) {

				List<FileStructure> fileStructureList = finalMap.get(moduleName);

				if (fileStructureList.size() == 0)
					continue;
				// Create a Sheet
				Sheet sheet = workbook.createSheet(moduleName);

				// Create header row
				Row headerRow = sheet.createRow(0);
				for (int i = 0; i < columns.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(columns[i]);
					cell.setCellStyle(headerCellStyle);
				}

				int valueRowNum = 1;

				for (FileStructure fileStructure : fileStructureList) {
					Row row = sheet.createRow(valueRowNum);

					row.createCell(0).setCellValue(valueRowNum++);
					row.createCell(1).setCellValue(fileStructure.getLineNo());
					row.createCell(2).setCellValue(fileStructure.getLine());
					row.createCell(3).setCellValue(
							fileStructure.getFileName().replace("E:\\NRI-NAM\\NAMRUICodebase\\NAMRUI", ""));
					/*
					 * if(fileStructure.getFileName().contains("DispatchAction"))
					 * row.createCell(3).setCellStyle(invalidClassStyle);
					 * if(fileStructure.getLine().contains("true"))
					 * row.createCell(2).setCellStyle(trueConnectionStyle);
					 */
					for (int i = 0; i < columns.length; i++) {
						sheet.autoSizeColumn(i);
					}

				}
			}

			// Write the output to a file
			FileOutputStream fileOut;

			fileOut = new FileOutputStream("C:\\Users\\subhams\\Desktop\\TestExcelFiles\\Report.xlsx");

			workbook.write(fileOut);
			fileOut.close();

			System.out.println("Excel Printing successful..!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
