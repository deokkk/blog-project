package blogProject.service;

import java.sql.*;

import blogProject.commons.DBUtil;
import blogProject.dao.MemberidDao;

public class MemberidService {
	private MemberidDao memberidDao;
	
	public boolean searchid(String memberid) {
		memberidDao = new MemberidDao();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			return memberidDao.searchid(conn, memberid);
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
}
