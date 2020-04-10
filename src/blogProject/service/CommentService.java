package blogProject.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blogProject.commons.DBUtil;
import blogProject.dao.CommentDao;
import blogProject.vo.Comment;
import blogProject.vo.Page;

public class CommentService {
	private CommentDao commentDao;
	private final int ROW_PER_PAGE = 5;
	private final int PAGE_PER_GROUP = 5;
	
	public void updateComment(Comment comment) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			commentDao = new CommentDao();
			commentDao.updateComment(conn, comment);
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
	
	public void removeComment(int commentNo) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			commentDao = new CommentDao();
			commentDao.deleteComment(conn, commentNo);
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
	
	public void addComment(Comment comment) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			commentDao = new CommentDao();
			commentDao.insertComment(conn, comment);
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
	
	public Map<String, Object> getCommentListAll(int postsNo, int currentPage) {
		Connection conn = null;
		ArrayList<Comment> list = null;
		Map<String, Object> map = null;
		commentDao = new CommentDao();
		try {
			conn = DBUtil.getConnection();
			int beginRow = (currentPage-1)*ROW_PER_PAGE;
			int totalRow = commentDao.countComment(conn, postsNo);
			
			int lastPage = 1;
			if(totalRow!=0) {
				lastPage = (totalRow%ROW_PER_PAGE!=0) ? totalRow/ROW_PER_PAGE+1 : totalRow/ROW_PER_PAGE;
			}
			System.out.println(lastPage);
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
			list = commentDao.selectCommentListAll(conn, postsNo, beginRow, ROW_PER_PAGE);
			map = new HashMap<String, Object>();
			map.put("page", page);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
}
