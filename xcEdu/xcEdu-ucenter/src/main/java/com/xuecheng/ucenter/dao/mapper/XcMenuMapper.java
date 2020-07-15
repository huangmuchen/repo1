package com.xuecheng.ucenter.dao.mapper;

import com.xuecheng.model.domain.ucenter.XcMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/7/10 21:00
 * @version: V1.0
 * @Description: 用户权限持久层接口
 */
public interface XcMenuMapper {

    /**
     * 根据用户id查询用户权限列表
     *
     * @param userId
     * @return
     */
    @Select("select id,code,p_id pId,menu_name menuName,url,is_menu isMenu,level,sort,status,icon,create_time createTime,update_time updateTime from xc_menu where id in (select menu_id from xc_permission where role_id in (select role_id from xc_user_role where user_id = #{userId}))")
    List<XcMenu> selectPermissionByUserId(@Param("userId") String userId);
}