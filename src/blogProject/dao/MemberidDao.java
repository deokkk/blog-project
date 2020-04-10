package blogProject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import blogProject.commons.DBUtil;

public class MemberidDao {
	public boolean searchid(Connection conn, String memberid) throws SQLException {
		String sql = "SELECT memberid FROM memberid WHERE memberid=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberid);
			rs = stmt.executeQuery();
			while(rs.next()) {
				if(rs.getString("memberid").equals(memberid)) {
					return false;
				}
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return true;
	}
	
	public int insertMemberid(Connection conn, String memberId) throws SQLException {
		String sql = "INSERT INTO memberid(memberid, memberid_date) VALUES(?, now())";			
		PreparedStatement stmt = null;
		int row2 = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			System.out.println(stmt + " <-- insertMemberid stmt");
			row2 = stmt.executeUpdate();
		} finally {
			DBUtil.close(null, stmt, null);
		}
		return row2;
	}
}
