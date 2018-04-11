package simple.dao.roledao;

import java.sql.Connection;
import java.util.List;

import simple.entity.Role;

public interface RoleDao {
	
	
	public List<Role> findRoleList(Connection connection,String sql,Object[] params);
	
	
}
