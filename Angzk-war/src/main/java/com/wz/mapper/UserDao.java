/**
 * 
 * @author Wz
 */
package com.wz.mapper;

import com.wz.business.user.model.SysUser;

/**   
 * @Description:
 * @author: Wz
 * @date:   2018年4月14日
 */
public interface UserDao {
	 public SysUser findByUserName(String username);
}
