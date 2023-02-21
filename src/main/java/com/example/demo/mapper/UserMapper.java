package com.example.demo.mapper;

import com.example.demo.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Entity com.example.demo.pojo.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




