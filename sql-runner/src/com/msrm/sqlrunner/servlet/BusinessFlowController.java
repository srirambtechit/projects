package com.msrm.sqlrunner.servlet;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.msrm.sqlrunner.beans.SqlResult;
import com.msrm.sqlrunner.beans.User;
import com.msrm.sqlrunner.beans.User.Role;
import com.msrm.sqlrunner.core.Configs;
import com.msrm.sqlrunner.core.SqlRunner;
import com.msrm.sqlrunner.core.UserManager;
import com.msrm.sqlrunner.exception.ExceptionUtil;
import com.msrm.sqlrunner.exception.SqlRunnerException;
import com.msrm.sqlrunner.util.JsonUtil;
import com.msrm.sqlrunner.util.JsonUtil.ArrayBuilder;
import com.msrm.sqlrunner.util.TomcatUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = {"/bizFlow", "/login", "/logout"})
public class BusinessFlowController extends HttpServlet {

	private Logger logger = Logger.getLogger(BusinessFlowController.class);

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BusinessFlowController() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		logger.info(action + " received and is being processed");
		System.out.println("Biz action : " + action);
		if (Objects.nonNull(action)) {
			switch (action) {
				case "doLogin" : {
					String username = request.getParameter("username");
					String password = request.getParameter("password");

					try {
						//@formatter:off
						User user = UserManager.selectAll()
										.stream()
										.filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
										.findFirst()
										.orElse(null);
						//@formatter:on
						if (user == null) {
							System.out.println("User is null");
							request.getRequestDispatcher("uiFlow?page=login").include(request, response);
							return;
						}

						HttpSession session = request.getSession(true);
						session.setAttribute("user", user);
						user.setLoggedIn(true);

						if (Role.isAdmin(user)) {
							response.sendRedirect("uiFlow?page=user");
						} else {
							response.sendRedirect("uiFlow?page=workspace");
						}
					} catch (SqlRunnerException e) {
						e.printStackTrace();
					}
					break;
				}
				case "executeQuery" : {
					String sql = request.getParameter("query");
					User user = (User) request.getSession(false).getAttribute("user");
					try {
						SqlResult sqlResult = SqlRunner.run(user, sql);

						if (sqlResult.getErrorJson() != null && !sqlResult.getErrorJson().isEmpty()) {
							response.setContentType("application/json");
							response.getWriter().append(sqlResult.getErrorJson());
							return;
						}
						String columnJson = JsonUtil.ArrayBuilder.newArrayBuilder().singleArray(sqlResult.getColumns());
						System.out.println("ColumnJson : " + columnJson);

						ArrayBuilder rowJsonBuilder = JsonUtil.ArrayBuilder.newArrayBuilder();
						for (List<String> list : sqlResult.getRows()) {
							rowJsonBuilder.elements(list);
						}
						String rowJson = rowJsonBuilder.build();
						System.out.println("RowJson : " + rowJson);

						//@formatter:off
						String resultJson = JsonUtil.Builder.newBuilder()
								.property("status", "success")
								.encodeJson("columns", columnJson)
								.encodeJson("rows", rowJson)
								.build();
						//@formatter:on
						System.out.println("ResultJson : " + resultJson);

						response.setContentType("application/json");
						response.getWriter().append(resultJson);
					} catch (SqlRunnerException e) {
						// SQL exception occurred due to whatever reason
						response.setContentType("application/json");
						System.out.println("Biz flow error : " + e.getMessage());
						response.getWriter().append(e.getMessage());
					}
					break;
				}
				case "excelReport" : {

					System.out.println("Excel report generation");
					String sql = request.getParameter("query");
					User user = (User) request.getSession(false).getAttribute("user");
					try {
						SqlResult sqlResult = SqlRunner.run(user, sql);
						String fileName = request.getParameter("excelFile");

						Workbook wb = null;
						if (fileName.toLowerCase().endsWith("xlsx")) {
							wb = createXSSFExcel(fileName, sqlResult);
						} else if (fileName.toLowerCase().endsWith("xls")) {
							wb = createHSSFExcel(fileName, sqlResult);
						}

						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
						wb.write(response.getOutputStream());
					} catch (SqlRunnerException e) {
						// SQL exception occurred due to whatever reason
						response.setContentType("application/json");
						System.out.println("excelReport Err : " + e.getMessage());
						response.getWriter().append(e.getMessage());
					}
					break;
				}
				case "doLogout" : {
					HttpSession session = request.getSession(false);
					session.removeAttribute("user");
					session.invalidate();
					request.getRequestDispatcher("uiFlow?page=login").forward(request, response);
					break;
				}
				case "addUser" : {
					System.out.println("addUser flow");
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					String role = request.getParameter("role");
					User user = new User(username, password);
					user.setRole(Role.get(role));
					String httpResponse = "";
					try {
						if (UserManager.add(user)) {
							httpResponse = JsonUtil.Builder.newBuilder().property("status", "success").build();
						} else {
							httpResponse = JsonUtil.Builder.newBuilder().property("status", "error").property("error", "User already exists").build();
						}
					} catch (SqlRunnerException e) {
						ExceptionUtil.error(e, logger);
						httpResponse = e.getMessage();
					}
					System.out.println("UserAddition : " + httpResponse);
					response.setContentType("application/json");
					response.getWriter().append(httpResponse);
					break;
				}
				case "fetchUser" : {
					String json = fetchUser();
					response.setContentType("application/json");
					response.getWriter().append(json.toString());
					break;
				}
				case "fetchSampleData" : {
					response.setContentType("application/json");
					String contextPath = request.getServletContext().getRealPath("/");
					String jsonString = Files.readAllLines(FileSystems.getDefault().getPath(contextPath + "/data/2500.txt")).stream().reduce((s1, s2) -> s1 + s2).orElse("{}");
					response.getWriter().append(jsonString);
					break;
				}
				case "fetchSettings" : {
					//@formatter:off
					String jsonStrign = JsonUtil.Builder.newBuilder()
						.property("dbUsername", Configs.value(Configs.DB_USERNAME))
						.property("dbPassword", Configs.value(Configs.DB_PASSWORD))
						.property("jdbcUrl", Configs.value(Configs.DB_JDBCURL))
						.property("allowedTimeFrom", Configs.value(Configs.APP_ALLOWED_TIME_FROM))
						.property("allowedTimeTo", Configs.value(Configs.APP_ALLOWED_TIME_TO))
						.property("sqlAllowedPerUser", Configs.value(Configs.APP_SQL_ALLOWED_PER_USER))
						.property("userMaxAllowed", Configs.value(Configs.APP_USER_MAXALLOWED))
						.property("catalinaHome", Configs.value(Configs.TOMCAT_CATALINA_HOME))
						.build();
					//@formatter:on
					response.setContentType("application/json");
					response.getWriter().append(jsonStrign);
					break;
				}
				case "saveSettings" : {
					String dbUsername = request.getParameter("dbUsername");
					String dbPassword = request.getParameter("dbPassword");
					String jdbcUrl = request.getParameter("jdbcUrl");
					String allowedTimeFrom = request.getParameter("allowedTimeFrom");
					String allowedTimeTo = request.getParameter("allowedTimeTo");
					String sqlAllowedPerUser = request.getParameter("sqlAllowedPerUser");
					String userMaxAllowed = request.getParameter("userMaxAllowed");
					String catalinaHome = request.getParameter("catalinaHome");

					try {
						// populating all fields
						Map<String, String> props = new HashMap<>();
						for (Configs config : Configs.values()) {
							props.put(Configs.key(config), Configs.value(config));
						}

						// updating form data
						props.put("db.username", dbUsername);
						props.put("db.password", dbPassword);
						props.put("db.jdbcUrl", jdbcUrl);
						props.put("app.time.allowed.from", allowedTimeFrom);
						props.put("app.time.allowed.to", allowedTimeTo);
						props.put("app.sql.allowedPerUser", sqlAllowedPerUser);
						props.put("app.user.maxAllowed", userMaxAllowed);
						props.put("tomcat.catalina.home", catalinaHome);

						System.out.println(props);

						StringBuffer buffer = new StringBuffer();
						for (Entry<String, String> entry : props.entrySet()) {
							buffer.append(entry.getKey() + "=" + entry.getValue());
							buffer.append("\n");
						}

						response.setContentType("application/json");
						String file = Configs.value(Configs.APP_CONFIG_FILE);
						Files.write(Paths.get(file), buffer.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
						response.getWriter().append(JsonUtil.Builder.newBuilder().property("status", "success").property("message", "Details saved successfully").build());
					} catch (Exception e) {
						response.getWriter().append(JsonUtil.Builder.newBuilder().property("status", "error").property("message", "Problem is occurred during processing").build());
					}
					break;
				}
				case "restartServer" : {
					// restarting Tomcat server
					String catalinaHome = Configs.value(Configs.TOMCAT_CATALINA_HOME);
					boolean restarted = TomcatUtil.restartTomcat(catalinaHome);

					response.setContentType("text/html");
					if (restarted)
						response.getWriter().append("Tomcat restarted<br/><a href='ui?page=login'>Login</a>");
					else
						response.getWriter().append("Tomcat is not restarted<br/><a href='ui?page=login'>Login</a>");
					break;
				}
			}
		}
	}

	protected Workbook createXSSFExcel(String fileName, SqlResult sqlResult) {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet1 = wb.createSheet();

		// XSSF Example
		XSSFFont font = (XSSFFont) wb.createFont();
		font.setBold(true);
		font.setColor(new XSSFColor(new java.awt.Color(255, 0, 0)));
		
		XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
	    style.setFillForegroundColor(new XSSFColor(new java.awt.Color(128, 0, 128)));
	    
		// creating a column row in spreadsheet
		int rowId = 0;
		Row row = sheet1.createRow(rowId++);
		List<String> columnData = sqlResult.getColumns();
		for (int i = 0; i < columnData.size(); i++) {
			XSSFCell cell = (XSSFCell) row.createCell(i);
			XSSFRichTextString rt = new XSSFRichTextString(columnData.get(i));
			rt.applyFont(font);
			cell.setCellValue(rt);
			cell.setCellStyle(style);
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

	private String fetchUser() {
		List<User> users = null;
		try {
			users = UserManager.selectAll();
		} catch (SqlRunnerException e) {
			ExceptionUtil.error(e, logger);
		}
		Objects.requireNonNull(users);
		ArrayBuilder jsonBuilder = ArrayBuilder.newArrayBuilder();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			jsonBuilder.elements(String.valueOf(i), user.getUsername(), user.getPassword(), user.getRole().name());
		}
		String json = jsonBuilder.build();
		System.out.println(json);
		return json;
	}

}
