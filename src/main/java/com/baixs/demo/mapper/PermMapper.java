package com.baixs.demo.mapper;

import com.baixs.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermMapper {
    User selectByPermissionName(String permissionName);
}
