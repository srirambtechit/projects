package com.msrm.sqlrunner.servlet;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class BootstrapServlet
 */
@WebServlet("/uiFlow")
public class UIController extends HttpServlet {

	private Logger logger = Logger.getLogger(UIController.class);

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UIController() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		logger.info(page + " page loading");
		System.out.println("page: " + page);
		if (Objects.nonNull(page)) {
			switch (page) {
				case "login" :
				case "home" : {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/index.html");
					dispatcher.forward(request, response);
					break;
				}
				case "settings" : {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/settings.html");
					dispatcher.forward(request, response);
					break;
				}
				case "user" : {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/user.html");
					dispatcher.forward(request, response);
					break;
				}
				case "workspace" : {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/workspace.html");
					dispatcher.forward(request, response);
					break;
				}
				case "error" : {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error.html");
					System.out.println();
					dispatcher.forward(request, response);
					break;
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
