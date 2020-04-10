package blogProject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.PostsService;
import blogProject.vo.Member;
import blogProject.vo.Posts;

@WebServlet("/AddPosts")
public class AddPosts extends HttpServlet {
	private PostsService postsService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("loginMember") != null && ((Member)request.getSession().getAttribute("loginMember")).getMemberLevel() < 5) {
			request.getRequestDispatcher("/WEB-INF/views/insertPosts.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath()+"/Home");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		System.out.println(memberId + " <--memberId");
		String subjectName = request.getParameter("subjectName");
		System.out.println(subjectName + " <--subjectName");
		String postsTitle = request.getParameter("postsTitle");
		System.out.println(postsTitle + " <---postsTitle");
		String postsContent = request.getParameter("postsContent");
		System.out.println(postsContent + " <--postsCotnent");
		Posts posts = new Posts();
		posts.setMemberId(memberId);
		posts.setPostsContent(postsContent);
		posts.setPostsTitle(postsTitle);
		posts.setSubjectName(subjectName);
		postsService = new PostsService();
		postsService.addPosts(posts);
		response.sendRedirect(request.getContextPath()+"/PostsListAll");
	}

}
