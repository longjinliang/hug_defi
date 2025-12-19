package com.ruoyi.web.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 通知
 * 
 * @author ruoyi
 */
@RestController
public class NoticeController extends BaseController
{
    @Autowired
    ISysNoticeService noticeService;




    /**
     * 获取公告列表
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/notice/list")
    public AjaxResult getNoticeList(HttpServletResponse response, HttpServletRequest request,@RequestBody JSONObject param){

        String lang = request.getHeader("lang");
        if(StringUtils.isEmpty(lang)){
            lang = "zh-CN";
        }
        if(!lang.equalsIgnoreCase("zh-CN")&&!lang.equalsIgnoreCase("en-US")){
            lang = "zh-CN";
        }

        List<JSONObject> list=noticeService.getArticleList(lang);

        if(list.size()==0 && !lang.equalsIgnoreCase("zh-CN")){
            list=noticeService.getArticleList("zh-CN");
        }


        return AjaxResult.success(list);
    }

    /**
     * 获取公告详情
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/notice/detail")
    public AjaxResult getNoticeDetail(HttpServletResponse response, HttpServletRequest request,@RequestBody JSONObject param){

        long noticeId = param.getLongValue("noticeId");
        JSONObject articleDetail = noticeService.getArticleDetail(noticeId);
        return AjaxResult.success(articleDetail);
    }






}
