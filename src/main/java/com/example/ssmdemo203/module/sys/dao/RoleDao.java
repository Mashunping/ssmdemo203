package com.example.ssmdemo203.module.sys.dao;

import com.example.ssmdemo203.module.sys.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("roleDao")
public interface RoleDao {
    @Select("SELECT\n" +
            "\tsr.id, \n" +
            "\tsr.role_name, \n" +
            "\tsr.role_desc, \n" +
            "\tsr.`status`, \n" +
            "\tsr.createuser, \n" +
            "\tsr.createtime, \n" +
            "\tsr.updateuser, \n" +
            "\tsr.updatetime\n" +
            "FROM\n" +
            "\tsys_role AS sr\n" +
            "\tINNER JOIN\n" +
            "\tsys_user_role AS sur\n" +
            "\tON \n" +
            "\t\tsr.id = sur.role_id\n" +
            "WHERE\n" +
            "\tsur.user_id =  #{uid}\n")
    List<Role>findByUid(Integer uid);

    @Select("SELECT\n" +
            " sr.role_name\n" +
            "FROM\n" +
            " sys_role AS sr\n" +
            " INNER JOIN\n" +
            " sys_user_role AS sur\n" +
            " ON \n" +
            "  sr.role_name = sur.id\n" +
            "WHERE\n" +
            " sur.user_id = #{uid}")
    List<Role> findRoleNameByUid(Integer uid);
}
