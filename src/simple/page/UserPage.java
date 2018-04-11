package simple.page;

import java.util.List;

import simple.entity.User;

public class UserPage {
	
	
	private Integer currentIndex;//当前页
	
	private Integer totalIndex;//总页数
	
	private Integer totalCount; //总数据记录数
	
	private Integer pageSize = 5;//每页的数据条数
	
	private List<User> users;//每页显示的数据
	
	//起始页
	private Integer start;
	//终止位
	private Integer end;
	public Integer getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(Integer currentIndex) {
		this.currentIndex = currentIndex;
	}
	public Integer getTotalIndex() {
		return totalIndex;
	}
	public void setTotalIndex(Integer totalIndex) {
		this.totalIndex = totalIndex;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	
	
	
	
}
