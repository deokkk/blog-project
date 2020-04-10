package blogProject.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import blogProject.commons.DBUtil;
import blogProject.dao.LikeyDao;
import blogProject.vo.Likey;

public class LikeyService {
	private LikeyDao likeyDao;	
	public void addLikey(Likey likey) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			likeyDao = new LikeyDao();
			int likeCkValue = likeyDao.isLikey(conn, likey);
			if(likey.getLikeyCk()==1) {
				if(likeCkValue==0) {
					// update like_ck = 1
					likeyDao.updateLikey(conn, likey);
				} else if(likeCkValue==1) {
					// delete
					likeyDao.deleteLikey(conn, likey);
				} else {
					// insert
					likeyDao.insertLikey(conn, likey);
				}
			}
			if(likey.getLikeyCk()==0) {
				if(likeCkValue==0) {
					// delete
					System.out.println("delete");
					likeyDao.deleteLikey(conn, likey);
				} else if(likeCkValue==1) {
					// update like_ck = 0
					System.out.println("update");
					likeyDao.updateLikey(conn, likey);
				} else {
					// insert
					likeyDao.insertLikey(conn, likey);
				}
			}
			
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
	
	public Map<String, Object> countLikey(int postsNo) {
		Connection conn = null;
		Map<String, Object> map = null;
		try {
			conn = DBUtil.getConnection();
			likeyDao = new LikeyDao();
			map = likeyDao.countLikey(conn, postsNo);
		} catch (ClassNotFoundException | SQLException e) {
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
