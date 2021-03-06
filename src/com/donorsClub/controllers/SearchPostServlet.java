package com.donorsClub.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.donorsClub.models.Item;
import com.donorsClub.models.User;
import com.donorsClub.services.SearchPostService;

/**
 * Servlet implementation class SearchPostServlet
 */
@WebServlet(urlPatterns= {"/home","/search"})
public class SearchPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchPostServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String keyWord = request.getParameter("q");
		if(keyWord == null) {
			keyWord = "";
		}
		SearchPostService sps = new SearchPostService();
		List<Item> itms = sps.searchPost(keyWord);
		int limit = 5;
		List<Item> mostInterestedItems  =sps.getMostInterestedItem(limit);
		
		User user = (User)request.getSession().getAttribute("user");
		request.setAttribute("items", itms); 
//		request.setAttribute("user", user);
//		System.out.println(user);
		request.setAttribute("mostInterestedItems", mostInterestedItems); 
		request.getRequestDispatcher("/WEB-INF/views/homepage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	/*	SearchPostService sps = new SearchPostService();
		String keyWord = request.getParameter("q");
		List<Item> itms = sps.searchPost(keyWord);
		System.out.println("key = "+keyWord);
		
		request.setAttribute("items", itms); 
		System.out.println("yess == "+itms);
		request.getRequestDispatcher("/WEB-INF/views/homepage.jsp").forward(request, response);*/
	}

}
