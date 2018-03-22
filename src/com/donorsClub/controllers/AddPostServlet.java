package com.donorsClub.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.donorsClub.daos.UserDao;
import com.donorsClub.models.Item;
import com.donorsClub.models.ItemCategory;
import com.donorsClub.models.Picture;
import com.donorsClub.models.User;
import com.donorsClub.services.CategoryService;
import com.donorsClub.services.ImageService;
import com.donorsClub.services.ItemService;

@WebServlet("/add_post.do")
@MultipartConfig
public class AddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddPostServlet() {
		super();
		System.out.println("here");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String myItmeId = request.getParameter("myId");

		if (myItmeId != null && !myItmeId.isEmpty()) {
			ItemService itmService = new ItemService();

			Item item = itmService.getItem(Long.parseLong(myItmeId));
			request.setAttribute("myId", myItmeId);
			request.setAttribute("Item", item);
	 	}
		CategoryService cts = new CategoryService();
		request.setAttribute("Categories", cts.GetAllCategory());
		request.getRequestDispatcher("/WEB-INF/views/add_post.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String itemName = request.getParameter("item_name");
		String description = request.getParameter("description");
		String category_ = request.getParameter("Myselect");
		long categoryId = (category_ == null || category_.isEmpty()) ? 0 : Long.parseLong(category_);
		Part profilePicturePart1 = request.getPart("photo1");
		String imageUrl1 = new ImageService().imageUploader("", profilePicturePart1);
		
		String photo2 = request.getParameter("photo2");
		Part profilePicturePart2 = request.getPart("photo2");
		String imageUrl2 = new ImageService().imageUploader("", profilePicturePart2);
		
		String photo3 = request.getParameter("photo3");
		Part profilePicturePart3 = request.getPart("photo3");
		String imageUrl3 = new ImageService().imageUploader("", profilePicturePart3);
		
		String photo4 = request.getParameter("photo4");
		Part profilePicturePart4 = request.getPart("photo4");
		String imageUrl4 = new ImageService().imageUploader("", profilePicturePart4);
		
		String photo5 = request.getParameter("photo5");
		Part profilePicturePart5 = request.getPart("photo5");
		String imageUrl5 = new ImageService().imageUploader("", profilePicturePart5);
		
		
		
		
		String myItmeId = request.getParameter("myId");
		Item item;
		if (itemName.isEmpty()) {
			request.setAttribute("signupErrorMessages", "Item Name");
			item = new Item();
			List<Picture> lpic = new ArrayList<>();
			item.setItemName(itemName);
			Picture pic = new Picture();
			pic.setPath(imageUrl1);
			lpic.add(pic);

			pic = new Picture();
			pic.setPath(imageUrl2);
			lpic.add(pic);

			pic = new Picture();
			pic.setPath(imageUrl3);
			lpic.add(pic);

			pic = new Picture();
			pic.setPath(imageUrl4);
			lpic.add(pic);

			pic = new Picture();
			pic.setPath(imageUrl5);
			lpic.add(pic);

			item.setPictureList(lpic);
			item.setDescription(description);
			ItemCategory category = new ItemCategory();
			category.setCategoryId(categoryId);
			List<ItemCategory> itemCategoryList = new ArrayList<>();
			itemCategoryList.add(category);
			item.setItemCategoryList(itemCategoryList);

		} else {

			long itmId;
			ItemService itemService = new ItemService();
			User user=(User) request.getSession().getAttribute("user");
		
			if (myItmeId != null && !myItmeId.isEmpty()) {
	
				itmId = Long.parseLong(myItmeId);
				itemService.InsertUpdateItem(itemName, description, imageUrl1, imageUrl2, imageUrl3, imageUrl4, imageUrl5,
						categoryId, user,itmId);
				user = (User)new UserDao().findById(user.getUserId());
				
				request.setAttribute("user", user);
	
				request.getRequestDispatcher("/WEB-INF/views/view_item.jsp").forward(request, response);
				
				
			} else {

				itmId = itemService.InsertUpdateItem(itemName, description, imageUrl1, imageUrl2, imageUrl3, imageUrl4, imageUrl5,
						categoryId, user,0);
				request.setAttribute("CorrerctMessages", "Done");
				item = itemService.getItem(itmId);		
				request.setAttribute("Item", item);
				CategoryService cts = new CategoryService();
				request.setAttribute("Categories", cts.GetAllCategory());
				request.getRequestDispatcher("/WEB-INF/views/add_post.jsp").forward(request, response);
			}
		
			
			

		}

	
	}

}
