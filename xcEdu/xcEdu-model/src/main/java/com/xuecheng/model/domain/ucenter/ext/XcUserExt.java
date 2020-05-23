package com.xuecheng.model.domain.ucenter.ext;

import com.xuecheng.model.domain.ucenter.XcMenu;
import com.xuecheng.model.domain.ucenter.XcUser;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
public class XcUserExt extends XcUser {
    private List<XcMenu> permissions;// 权限信息
    private String companyId;// 企业信息
}