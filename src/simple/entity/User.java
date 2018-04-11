package simple.entity;

import java.util.Date;


import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
@XmlRootElement   //jaxb  jdk提供的用于将java对象转化成xml  或者将xml转化成Java对象的技术
public class User {
	private Integer id; //id 
	@NotEmpty
	@Length(min = 6,max = 10,message = "编码长度不符合")
	
	private String userCode;
	private String userName; 
	
	@NotEmpty(message="密码不得为空")
	@Length(min= 6,max=8,message="密码格式不正确")
	private String userPassword; 

	private Integer gender;
	
	private String iconPath;//头像
	private String zjzPath;//证件照
	
	
	
	
	public String getZjzPath() {
		return zjzPath;
	}

	public void setZjzPath(String zjzPath) {
		this.zjzPath = zjzPath;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	//@JsonField(format="")  用注解 高侵入式 但是可以根据业务需求指定不同的时间格式
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;  
	private String phone; 
	private String address; 
	private Integer userRole;    
	private Integer createdBy;  
	private Date creationDate;
	private Integer modifyBy;    
	private Date modifyDate;  
	
	private Integer age;
	
	private String userRoleName;   
	
	public User(){}
	
	public User(Integer id,String userCode,String userName,String userPassword,Integer gender,Date birthday,String phone,
			String address,Integer userRole,Integer createdBy,Date creationDate,Integer modifyBy,Date modifyDate){
		this.id = id;
		this.userCode = userCode;
		this.userName = userName;
		this.userPassword = userPassword;
		this.gender = gender;
		this.birthday = birthday;
		this.phone = phone;
		this.address = address;
		this.userRole = userRole;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.modifyBy = modifyBy;
		this.modifyDate = modifyDate;
	}
	public String getUserRoleName() {
		return userRoleName;
	}
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	public Integer getAge() {
		/*long time = System.currentTimeMillis()-birthday.getTime();
		Integer age = Long.valueOf(time/365/24/60/60/1000).IntegerValue();*/
		Date date = new Date();
		Integer age = date.getYear()-birthday.getYear();
		return age;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getUserRole() {
		return userRole;
	}
	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Integer getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
