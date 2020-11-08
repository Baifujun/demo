package com.baixs.demo.mapper;

import com.baixs.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
    User selectByRoleName(String roleName);
}
