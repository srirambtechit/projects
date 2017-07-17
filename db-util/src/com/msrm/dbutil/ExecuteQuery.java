package com.msrm.dbutil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
public class ExecuteQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExecuteQuery() {
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
			String queryFile = reportPath + File.separator + "query.sql";
			System.out.println("ReportPath : " + reportPath);

			int seqNo = 0;
			if (Paths.get(queryFile).toFile().exists()) {
				System.out.println("If-Block-Start");
				//@formatter:off
				seqNo = Files.lines(Paths.get(queryFile), StandardCharsets.UTF_8)
						.map(s -> s.substring(0, s.indexOf(";")))
						.mapToInt(Integer::parseInt)
						.max()
						.orElse(0);
				System.out.println("If-Block-End");
				//@formatter:on
			}

			seqNo = seqNo == 0 ? 1 : seqNo + 1;

			System.out.println("Query file : " + queryFile);
			Files.write(Paths.get(queryFile), (seqNo + ";" + sql + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

			String fileName = "report-" + seqNo + ".xls";
			Workbook wb = createHSSFExcel(sqlResult);

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

	protected Workbook createHSSFExcel(SqlResult sqlResult) {
		Workbook wb = new HSSFWorkbook();
		try {
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
				rt.applyFont(0, columnData.get(i).length(), font);
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
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return wb;
	}

}
