package blogProject.vo;

public class Posts {
	private int postsNo;
	private String subjectName;
	private String memberId;
	private String postsTitle;
	private String postsContent;
	private String postsDate;
	public int getPostsNo() {
		return postsNo;
	}
	public void setPostsNo(int postsNo) {
		this.postsNo = postsNo;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPostsTitle() {
		return postsTitle;
	}
	public void setPostsTitle(String postsTitle) {
		this.postsTitle = postsTitle;
	}
	public String getPostsContent() {
		return postsContent;
	}
	public void setPostsContent(String postsContent) {
		this.postsContent = postsContent;
	}
	public String getPostsDate() {
		return postsDate;
	}
	public void setPostsDate(String postsDate) {
		this.postsDate = postsDate;
	}
}
