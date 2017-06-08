package com.msrm.sqlrunner.servlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.msrm.sqlrunner.beans.SqlResult;
import com.msrm.sqlrunner.beans.User;
import com.msrm.sqlrunner.beans.User.Role;
import com.msrm.sqlrunner.core.SqlRunner;
import com.msrm.sqlrunner.core.UserManager;
import com.msrm.sqlrunner.exception.ExceptionUtil;
import com.msrm.sqlrunner.exception.SqlRunnerException;
import com.msrm.sqlrunner.util.JsonUtil;
import com.msrm.sqlrunner.util.JsonUtil.ArrayBuilder;

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
							request.getRequestDispatcher("ui?page=login&error=Invalid Username or Password").include(request, response);
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

						String columnJson = JsonUtil.ArrayBuilder.newArrayBuilder().singleArray(sqlResult.getColumns());
						System.out.println("ColumnJson : " + columnJson);

						ArrayBuilder rowJsonBuilder = JsonUtil.ArrayBuilder.newArrayBuilder();
						for (List<String> list : sqlResult.getRows()) {
							rowJsonBuilder.elements(list);
						}
						String rowJson = rowJsonBuilder.build();
						System.out.println("RowJson : " + rowJson);

						String encodedColumnString = new String(java.util.Base64.getMimeEncoder().encode(columnJson.getBytes()), StandardCharsets.UTF_8);
						String encodedRowString = new String(java.util.Base64.getMimeEncoder().encode(rowJson.getBytes()), StandardCharsets.UTF_8);
						
						
						//@formatter:off
						String resultJson = JsonUtil.Builder.newBuilder()
								.property("status", "success")
								.json("columns", encodedColumnString)
								.json("rows", encodedRowString)
								.build();
						//@formatter:on
						System.out.println("ResultJson : " + resultJson);

						response.setContentType("application/json");
						response.getWriter().append(resultJson);
					} catch (SqlRunnerException e) {
						// SQL exception occurred due to whatever reason
						response.setContentType("application/json");
						//@formatter:off
						String resultJson = JsonUtil.Builder.newBuilder()
								.property("status", "error")
								.property("type", "SQL Error")
								.property("cause", (e.getCause() != null) ? e.getCause().getMessage() : "")
								.property("error", e.getMessage())
								.build();
						//@formatter:on
						response.getWriter().append(resultJson);
					}
					break;
				}
				case "doLogout" : {
					HttpSession session = request.getSession(false);
					session.removeAttribute("user");
					session.invalidate();
					request.getRequestDispatcher("").forward(request, response);
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
			}
		}
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
