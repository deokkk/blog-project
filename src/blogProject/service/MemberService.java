package blogProject.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blogProject.commons.DBUtil;
import blogProject.dao.CommentDao;
import blogProject.dao.LikeyDao;
import blogProject.dao.MemberDao;
import blogProject.dao.MemberidDao;
import blogProject.dao.PostsDao;
import blogProject.vo.Member;
import blogProject.vo.Page;

public class MemberService {
	private MemberDao memberDao;
	private MemberidDao memberidDao;
	private CommentDao commentDao;
	private PostsDao postsDao;
	private LikeyDao likeyDao;
	private final int ROW_PER_PAGE = 10;
	private final int PAGE_PER_GROUP = 5;
	
	public void updateMember(Member member) {
		memberDao = new MemberDao();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			memberDao.updateMember(conn, member);
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
	
	public void updateMemberLevel(Member member) {
		memberDao = new MemberDao();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			memberDao.updateMemberLevel(conn, member);
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
	
	public Map<String, Object> selectMemberListAll(int currentPage) {
		memberDao = new MemberDao();
		Connection conn = null;
		Map<String, Object> map = null;
		ArrayList<Member> list = null;
		try {
			conn = DBUtil.getConnection();
			int beginRow = (currentPage-1)*ROW_PER_PAGE;
			int totalRow = memberDao.countMember(conn);
			int lastPage = (totalRow%ROW_PER_PAGE!=0) ? totalRow/ROW_PER_PAGE+1 : totalRow/ROW_PER_PAGE;
			int lastPageGroup = (lastPage%PAGE_PER_GROUP!=0) ? lastPage/PAGE_PER_GROUP+1 : lastPage/PAGE_PER_GROUP;
			int currentPageGroup = ((currentPage-1)%PAGE_PER_GROUP==0) ? currentPage : ((currentPage-1)/PAGE_PER_GROUP)*PAGE_PER_GROUP+1;
			//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			//System.out.println((currentPage-1)%PAGE_PER_GROUP + " <---(currentPage-1)%PAGE_PER_GROUP");
			//System.out.println(currentPage + " <--currentPage");
			//System.out.println((currentPage/PAGE_PER_GROUP) + " <--(currentPage/PAGE_PER_GROUP)");
			//System.out.println((currentPage/PAGE_PER_GROUP)*PAGE_PER_GROUP+1 + " <--(currentPage/PAGE_PER_GROUP)*PAGE_PER_GROUP+1");
			Page page = new Page();
			page.setBeginRow(beginRow);
			page.setCurrentPage(currentPage);
			page.setCurrentPageGroup(currentPageGroup);
			page.setLastPage(lastPage);
			page.setLastPageGroup(lastPageGroup);
			page.setPagePerGroup(PAGE_PER_GROUP);
			page.setRowPerPage(ROW_PER_PAGE);
			page.setTotalRow(totalRow);
			list = memberDao.selectMemberListAll(conn, beginRow, ROW_PER_PAGE);
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
	
	public boolean seachId(String memberId) {
		memberDao = new MemberDao();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			return memberDao.searchId(conn, memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addMember(Member member) {
		memberDao = new MemberDao();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			memberDao.insertMember(conn, member);
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
	
	public int removeMember(Member member) {
		System.out.println("MemberService.removeMember()");
		Connection conn = null;
		int row2=0;
		int success = 0;
		try {
			memberDao = new MemberDao();
			memberidDao = new MemberidDao();
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			// 1. comment 삭제
			commentDao = new CommentDao();
			commentDao.deleteComment(conn, member.getMemberId());
			// 2. likey 삭제
			likeyDao = new LikeyDao();
			likeyDao.deleteLikey(conn, member.getMemberId());
			// 3. posts 삭제
			postsDao = new PostsDao();
			postsDao.deletePosts(conn, member.getMemberId());

			// 4. member 삭제
			int row = memberDao.deleteMember(conn, member);
			System.out.println("memberDao.deleteMember()");
			
			// 5. memberid 추가
			if(row == 1) {
				row2=memberidDao.insertMemberid(conn, member.getMemberId());
				System.out.println("memberidDao.insertMemberid()");
			}
			if(row2 == 1) {
				conn.commit();
				success = 1;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("fail");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println(success);
		return success;
	}
	
	public Member getMemberOne(String memberId) {
		memberDao = new MemberDao();
		Connection conn = null;
		Member member = null;
		try {
			conn = DBUtil.getConnection();
			member = memberDao.selectMemberOne(conn, memberId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return member;
	}
	
	public Member getMemberOne(Member member) {
		memberDao = new MemberDao();
		Connection conn = null;
		Member returnMember = null;
		try {
			conn = DBUtil.getConnection();
			returnMember = memberDao.selectMemberOne(conn, member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnMember;
	}
}
