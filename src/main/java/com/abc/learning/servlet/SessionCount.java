package com.abc.learning.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SessionCount")
public class SessionCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int visitCount = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		response.setContentType("text/html");

		HttpSession httpSession = request.getSession(true);


		if (!httpSession.isNew()) {
			out.println(sessionTypeMessage(false));

		} else {
			out.println(sessionTypeMessage(true));

		}

		out.println(sessionVisitMessage(httpSession));
		return;

	}

	String sessionTypeMessage(boolean newSession) {
		if (newSession) {
			return "Welcome to new session<br>";
		} else {
			return "Currently you are in already existed session<br>";
		}
	}

	String sessionVisitMessage( HttpSession httpSession) {

		boolean firstTimeVisitor;

		String countResponse = (String) httpSession.getAttribute("session_count");

		if (countResponse == null) {
			visitCount = 1;
			firstTimeVisitor = true;
		} else {
			firstTimeVisitor = false;
			visitCount = Integer.parseInt(countResponse) + 1;
		}

		httpSession.setAttribute("session_count", visitCount + "");
		httpSession.setMaxInactiveInterval(50);

		if (firstTimeVisitor) {
			return "You are visiting this page for the first time <br>Number of visits : " + visitCount;
		} else {
			visitCount = Integer.parseInt(countResponse) + 1;
			return "You have already visited this page <br>Number of visits : " + visitCount;
		}
	}

}
