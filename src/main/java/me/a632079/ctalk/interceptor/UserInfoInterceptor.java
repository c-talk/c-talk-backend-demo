package me.a632079.ctalk.interceptor;

import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.po.UserInfo;
import me.a632079.ctalk.util.UserInfoUtil;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectStreamClass;
import java.util.Objects;

/**
 * @className: UserInfoInterceptor
 * @description: UserInfoInterceptor - 用户信息注入工具类
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@Component
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object id = request.getSession()
                           .getAttribute("id");

        if (Objects.isNull(id)) {
            return false;
        }

        UserInfo info = new UserInfo();
        info.setId((Long) id);

        UserInfoUtil.setUserInfo(info);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoUtil.clear();
    }
}
