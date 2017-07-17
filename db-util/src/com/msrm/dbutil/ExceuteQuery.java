package com.msrm.dbutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Servlet implementation class ExceuteQuery
 */
@WebServlet("/execute")
public class ExceuteQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExceuteQuery() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Excel report generation");
		String sql = request.getParameter("query");
		String user = (String) request.getSession(false).getAttribute("username");
		try {
			SqlResult sqlResult = SqlRunner.run(user, sql);
			String reportPath = Props.value("report.path");
			String fileName = reportPath + File.separator;

			FileUtil.newFileName(reportPath);
			
			
			Files.write(Paths.get(reportPath + File.separator + "query.sql"), bytes, options)
			Workbook wb = createHSSFExcel(fileName, sqlResult);
			
			

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			// SQL exception occurred due to whatever reason
			response.setContentType("application/json");
			System.out.println("excelReport Err : " + e.getMessage());
			response.getWriter().append(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected Workbook createHSSFExcel(String fileName, SqlResult sqlResult) {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet1 = wb.createSheet();

		// XSSF Example
		HSSFFont font = (HSSFFont) wb.createFont();
		font.setBold(true);
		font.setColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());

		// creating a column row in spreadsheet
		int rowId = 0;
		Row row = sheet1.createRow(rowId++);
		List<String> columnData = sqlResult.getColumns();
		for (int i = 0; i < columnData.size(); i++) {
			HSSFCell cell = (HSSFCell) row.createCell(i);
			HSSFRichTextString rt = new HSSFRichTextString(columnData.get(i));
			rt.applyFont(0, 10, font);
			cell.setCellValue(rt);
		}

		// creating a data row in spreadsheet
		List<List<String>> rowsData = sqlResult.getRows();
		for (List<String> rowData : rowsData) {
			Row newRow = sheet1.createRow(rowId++);
			for (int i = 0; i < rowData.size(); i++) {
				newRow.createCell(i).setCellValue(rowData.get(i));
			}
		}
		return wb;
	}

}
