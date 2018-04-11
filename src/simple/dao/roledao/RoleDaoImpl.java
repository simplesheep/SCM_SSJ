package simple.dao.roledao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import simple.dao.BaseDao;
import simple.entity.Role;
@Repository
public class RoleDaoImpl implements RoleDao {

	@Override
	public List<Role> findRoleList(Connection connection, String sql, Object[] params) {
		
		
		ResultSet rs = BaseDao.executeQuery(connection, sql, params);
		
		List<Role> list = new ArrayList<Role>();
		Role role = null;
		
		if(rs != null) {
			
			try {
				while(rs.next()) {
					role = new Role();
					role.setId(rs.getInt("id"));
					role.setRoleName(rs.getString("roleName"));
					list.add(role);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return list;
	}

}
