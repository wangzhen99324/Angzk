package com.wz.mapper;
import java.util.List;

import com.wz.business.user.model.Permission;

/**
 * Created by yangyibo on 17/1/20.
 */
public interface PermissionDao {
    public List<Permission> findAll();
    public List<Permission> findByAdminUserId(int userId);
}