package com.ruoyi.framework.interceptor;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * 防止重复提交拦截器
 *
 * @author ruoyi
 */
@Component
public  class LangInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {

        String lang = request.getHeader("lang");
        if (StringUtils.isNotEmpty(lang)) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver != null) {
                Locale locale = parseLocale(lang);
                if (locale != null) {
                    localeResolver.setLocale(request, response, locale);
                }
            }
        }

        return true;

    }

    private Locale parseLocale(String langTag) {
        if (!StringUtils.hasText(langTag)) {
            return null;
        }
        try {
            // 使用标准解析
            Locale locale = org.springframework.util.StringUtils.parseLocale(langTag);

            langTag=langTag.toLowerCase();
            // 如果是中文处理
            if (langTag.startsWith("zh")) {
                if (langTag.contains("tw") || langTag.contains("hant")||langTag.contains("hk")) {
                    return Locale.TRADITIONAL_CHINESE;  // zh_TW
                } else if (langTag.contains("cn") || langTag.contains("hans")) {
                    return Locale.SIMPLIFIED_CHINESE;  // zh_CN
                }
            }
            return locale;
        } catch (Exception e) {
            // 解析失败，返回默认
            return null;
        }
    }

}
