package blogProject.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blogProject.commons.DBUtil;
import blogProject.dao.CommentDao;
import blogProject.dao.LikeyDao;
import blogProject.dao.PostsDao;
import blogProject.vo.Page;
import blogProject.vo.Posts;
import blogProject.vo.Subject;
import blogProject.vo.postsAndMember;

public class PostsService {
	private PostsDao postsDao;
	private LikeyDao likeyDao;
	private CommentDao commentDao;
	private final int ROW_PER_PAGE = 10;
	private final int PAGE_PER_GROUP = 5;
	
	public void removePosts(int postsNo) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			likeyDao = new LikeyDao();
			likeyDao.deleteLikey(conn, postsNo);
			
			commentDao = new CommentDao();
			commentDao.deleteCommentByPosts(conn, postsNo);
			
			postsDao = new PostsDao();
			postsDao.deletePosts(conn, postsNo);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updatePosts(Posts posts) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			postsDao = new PostsDao();
			postsDao.updatePosts(conn, posts);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addPosts(Posts posts) {
		Connection conn = null;
		System.out.println(posts.getPostsTitle() + " <---Service postsTitle!!!!!!!!");
		try {
			conn = DBUtil.getConnection();
			postsDao = new PostsDao();
			postsDao.insertPosts(conn, posts);
			System.out.println(posts.getPostsTitle() + " <---Service postsTitle");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Map<String, Object> getPostsListAll(int currentPage) {
		postsDao = new PostsDao();
		ArrayList<Posts> list = null;
		Map<String, Object> map = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			int beginRow = (currentPage-1)*ROW_PER_PAGE;
			int totalRow = postsDao.countPosts(conn);
			int lastPage = (totalRow%ROW_PER_PAGE!=0) ? totalRow/ROW_PER_PAGE+1 : totalRow/ROW_PER_PAGE;
			int lastPageGroup = (lastPage%PAGE_PER_GROUP!=0) ? lastPage/PAGE_PER_GROUP+1 : lastPage/PAGE_PER_GROUP;
			int currentPageGroup = ((currentPage-1)%PAGE_PER_GROUP==0) ? currentPage : ((currentPage-1)/PAGE_PER_GROUP)*PAGE_PER_GROUP+1;
			Page page = new Page();
			page.setBeginRow(beginRow);
			page.setCurrentPage(currentPage);
			page.setCurrentPageGroup(currentPageGroup);
			page.setLastPage(lastPage);
			page.setLastPageGroup(lastPageGroup);
			page.setPagePerGroup(PAGE_PER_GROUP);
			page.setRowPerPage(ROW_PER_PAGE);
			page.setTotalRow(totalRow);
			list = postsDao.selectPostsListAll(conn, beginRow, ROW_PER_PAGE);
			map = new HashMap<String, Object>();
			map.put("page", page);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public postsAndMember getPostsOne(int postsNo) {
		postsDao = new PostsDao();
		postsAndMember pnm = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			pnm = postsDao.selectPostsOne(conn, postsNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pnm;
	}
	
	public Map<String, Object> getPostsList(String subjectName, int currentPage) {
		postsDao = new PostsDao();
		ArrayList<Posts> list = null;
		Map<String, Object> map = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			int beginRow = (currentPage-1)*ROW_PER_PAGE;
			int totalRow = postsDao.countPostsBySubject(conn, subjectName);
			int lastPage = (totalRow%ROW_PER_PAGE!=0) ? totalRow/ROW_PER_PAGE+1 : totalRow/ROW_PER_PAGE;
			int lastPageGroup = (lastPage%PAGE_PER_GROUP!=0) ? lastPage/PAGE_PER_GROUP+1 : lastPage/PAGE_PER_GROUP;
			int currentPageGroup = ((currentPage-1)%PAGE_PER_GROUP==0) ? currentPage : ((currentPage-1)/PAGE_PER_GROUP)*PAGE_PER_GROUP+1;
			Page page = new Page();
			page.setBeginRow(beginRow);
			page.setCurrentPage(currentPage);
			page.setCurrentPageGroup(currentPageGroup);
			page.setLastPage(lastPage);
			page.setLastPageGroup(lastPageGroup);
			page.setPagePerGroup(PAGE_PER_GROUP);
			page.setRowPerPage(ROW_PER_PAGE);
			page.setTotalRow(totalRow);
			list = postsDao.selectPostsList(conn, subjectName, beginRow, ROW_PER_PAGE);
			map = new HashMap<String, Object>();
			map.put("page", page);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
