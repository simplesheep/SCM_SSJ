package simple.service.roleservice;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import simple.dao.BaseDao;
import simple.dao.roledao.RoleDao;
import simple.entity.Role;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public List<Role> findRoleList() {

		Connection connection = BaseDao.getConnection();

		String sql = "select id,roleName from smbms_role";
		List<Role> list = null;
		try {
			list = roleDao.findRoleList(connection, sql, null);
		} finally {
			BaseDao.close(connection, null, null);

		}
		return list;
	}

}
