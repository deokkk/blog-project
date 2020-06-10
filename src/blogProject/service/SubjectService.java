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
import blogProject.dao.SubjectDao;
import blogProject.vo.Page;
import blogProject.vo.Posts;
import blogProject.vo.Subject;

public class SubjectService {
	private SubjectDao subjectDao;
	private PostsDao postsDao;
	private LikeyDao likeyDao;
	private CommentDao commentDao;
	private final int ROW_PER_PAGE = 10;
	private final int PAGE_PER_GROUP = 5;
	
	public void removeSubject(String subjectName) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			postsDao = new PostsDao();
			ArrayList<Posts> list = postsDao.selectPostsNoList(conn, subjectName);
			likeyDao = new LikeyDao();
			commentDao = new CommentDao();
			for(Posts posts : list) {
				likeyDao.deleteLikey(conn, posts.getPostsNo());
				commentDao.deleteCommentByPosts(conn, posts.getPostsNo());
			}
			postsDao.deletePostsBySubject(conn, subjectName);
			
			subjectDao = new SubjectDao();
			subjectDao.deleteSubject(conn, subjectName);
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
	
	public void addSubject(String subjectName) {
		subjectDao = new SubjectDao();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			subjectDao.insertSubject(conn, subjectName);
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
	
	public ArrayList<Subject> getSubjectListAll() {
		System.out.println("subejctService.getSubjectListAll()");
		subjectDao = new SubjectDao();
		postsDao = new PostsDao();
		ArrayList<Subject> list = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//list = postsDao.countPostsBySubject(conn);
			list = subjectDao.selectSubjectListAll(conn);
			for(Subject subject : list) {
				subject.setSubjectCount(postsDao.countPostsBySubject(conn, subject.getSubjectName()));
				System.out.println(subject.getSubjectCount() + " <----------subject size");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public Map<String, Object> getSubjectListAll(int currentPage) {
		subjectDao = new SubjectDao();
		Map<String, Object> map = null;
		ArrayList<Subject> list = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			int beginRow = (currentPage-1)*ROW_PER_PAGE;
			int totalRow = subjectDao.countSubject(conn);
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
			list = subjectDao.selectSubjectListAll(conn, beginRow, ROW_PER_PAGE);
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
