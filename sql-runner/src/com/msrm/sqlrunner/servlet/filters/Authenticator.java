package com.msrm.sqlrunner.servlet.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebFilter(urlPatterns = {"/", "/index", "/home", "/ui", "/data"})
public class Authenticator implements Filter {

	private Logger logger = Logger.getLogger(Authenticator.class);

	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("AuthenticationFilter initialized");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String uri = httpRequest.getRequestURI();
		logger.info("Requested Resource::" + uri);
		System.out.println("URL : " + uri + "?" + httpRequest.getQueryString());

		String contextRoot = httpRequest.getServletContext().getServletContextName();
		uri = uri.substring(contextRoot.length() + 2);
		System.out.println("URI : " + uri);

		HttpSession session = httpRequest.getSession(false);
		System.out.println("session: " + session);
		if (session == null || session.getAttribute("user") == null) {
			logger.error("Unauthorized access request");
			httpRequest.getRequestDispatcher("uiFlow?page=login").forward(httpRequest, httpResponse);
		} else {
			// pass the request along the filter chain
			String path = "";
			if (uri.equals("data")) {
				path = "bizFlow?" + httpRequest.getQueryString();
			} else if (uri.equals("ui")) {
				path = "uiFlow?" + httpRequest.getQueryString();
			}
			System.out.println("Initiated flow is " + path);
			httpRequest.getRequestDispatcher(path).forward(httpRequest, httpResponse);
//			chain.doFilter(httpRequest, response);
		}

	}

	public void destroy() {
		// close any resources here
	}

}