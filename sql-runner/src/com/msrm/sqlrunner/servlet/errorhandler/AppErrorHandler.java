package com.msrm.sqlrunner.servlet.errorhandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.msrm.sqlrunner.util.JsonUtil;

/**
 * Servlet implementation class AppErrorHandler
 */
@WebServlet("/appErrorHandler")
public class AppErrorHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(AppErrorHandler.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AppErrorHandler() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Application error thrown");
		System.out.println("Application error thrown");

		// Analyze the servlet exception
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		if (servletName == null) {
			servletName = "Unknown";
		}
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}

		// Set response content type
		response.setContentType("application/json");

		//@formatter:off
		String jsonResponse = JsonUtil.Builder
			.newBuilder()
			.property("status", "error")
			.property("type", "Application Error")
			.property("servletName", servletName)
			.property("statusCode",String.valueOf(statusCode))
			.property("occurredAt", requestUri)
			.property("message", throwable.getMessage())
			.build();
		//@formatter:on

		System.out.println("Error : " + jsonResponse);
		response.getWriter().write(jsonResponse);

		request.getRequestDispatcher("uiFlow?page=error").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
