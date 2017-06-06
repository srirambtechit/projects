package com.msrm.sqlrunner.servlet;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.msrm.sqlrunner.beans.User;
import com.msrm.sqlrunner.core.UserManager;
import com.msrm.sqlrunner.exception.ExceptionUtil;
import com.msrm.sqlrunner.exception.SqlRunnerException;
import com.msrm.sqlrunner.util.JsonUtil.ArrayBuilder;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = {"/bizFlow", "login"})
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
						User user = UserManager.selectAll().stream().filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password)).findFirst().orElse(null);
						if (user == null) {
							response.sendRedirect("ui?page=login");
						}
					} catch (SqlRunnerException e) {
						e.printStackTrace();
					}

					User user = new User(username, password);
					String status = doLogin(user);
					if (status.contains("error") || status.equals("unknown")) {
						response.sendRedirect("ui?page=login");
					}
					user.setLoggedIn(true);
					HttpSession session = request.getSession(true);
					session.setAttribute("user", user);
					if ("admin".equals(user.getUsername())) {
						response.sendRedirect("ui?page=user");
					} else {
						response.sendRedirect("ui?page=workspace");
					}
					break;
				}
				case "executeQuery" :
					String query = request.getParameter("query");

					break;
				case "logout" :
					break;
				case "addUser" : {
					System.out.println("addUser flow");
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					User user = new User(username, password);
					String httpResponse = "";
					try {
						if (UserManager.add(user)) {
							httpResponse = "";
						} else {
							httpResponse = "{ 'error': ''}";
						}
					} catch (SqlRunnerException e) {
						ExceptionUtil.error(e, logger);
					}
					response.setContentType("application/json");
					response.getWriter().append(httpResponse);
					break;
				}
				case "fetchUser" :
					String json = fetchUser();
					response.setContentType("application/json");
					response.getWriter().append(json.toString());
					break;
				case "fetchSampleData" :
					response.setContentType("application/json");
					String contextPath = request.getServletContext().getRealPath("/");
					String jsonString = Files.readAllLines(FileSystems.getDefault().getPath(contextPath + "/data/2500.txt")).stream().reduce((s1, s2) -> s1 + s2).orElse("{}");
					response.getWriter().append(jsonString);
					break;
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
			jsonBuilder.elements(String.valueOf(i), user.getUsername(), user.getPassword());
		}
		String json = jsonBuilder.build();
		System.out.println(json);
		return json;
	}

	private String doLogin(User user) {
		try {
			List<User> users = UserManager.selectAll();
			long count = users.stream().filter(u -> u.equals(user)).count();
			logger.info("Count: " + count);
			// valid user
			if (count == 1) {
				return "Admin".equals(user.getUsername()) ? "admin" : user.getUsername();
			} else {
				return "unknown";
			}
		} catch (SqlRunnerException e) {
			ExceptionUtil.error(e, logger);
			return "{ error: Invalid username or password }";
		}

	}

}
