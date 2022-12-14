package com.example.ssmdemo203.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ssmdemo203.module.sys.pojo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *     Mapper 接口
 * <p>
 *
 * @author mashunping
 * @since 2022-10-22
 */
// ibatis mybatis mybatisPlus
@Mapper
@Repository("userDao")
public interface UserDao extends BaseMapper<User> {
    //ibatis test
    @Select("SELECT\n" +
            "\tsu.id, \n" +
            "\tsu.username, \n" +
            "\tsu.`password`, \n" +
            "\tsu.`name`, \n" +
            "\tsu.avatar, \n" +
            "\tsu.introduction, \n" +
            "\tsu.`status`, \n" +
            "\tsu.create_user, \n" +
            "\tsu.create_time, \n" +
            "\tsu.update_user, \n" +
            "\tsu.update_time\n" +
            "FROM\n" +
            "\tsys_user AS su\n" +
            "WHERE\n" +
            "\tsu.username = #{username}\n")
    @Results({
                @Result(id = true, property = "id",column = "id"),
                @Result(property = "roles",column = "id",javaType = List.class,
                many = @Many(select = "com.example.ssmdemo203.module.sys.dao.Role.findByUid"))
            })
    public User findByUsername(String username);

    @Update("UPDATE \n" +
            "\tsys_user \n" +
            "SET\n" +
            "\tid = #{User.id},\n" +
            "\tusername = #{User.username},\n" +
            "\t`password` = #{User.password},\n" +
            "\tNAME = #{User.name},\n" +
            "\tavatar = #{User.avatar},\n" +
            "\tintroduction = #{User.introduction},\n" +
            "\tSTATUS = #{User.status},\n" +
            "\tcreate_user = #{User.createUser},\n" +
            "\tcreate_time = #{User.createTime},\n" +
            "\tupdate_user = #{User.updateUser},\n" +
            "\tupdate_time = #{User.updateTime} \n" +
            "WHERE id = #{User.id}\n")
    public Integer updateUser(User user);
}
