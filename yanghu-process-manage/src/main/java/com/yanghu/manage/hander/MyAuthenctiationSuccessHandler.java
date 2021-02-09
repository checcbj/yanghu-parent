package com.yanghu.manage.hander;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanghu.manage.base.DataResult;
import com.yanghu.manage.entity.User;
import com.yanghu.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理器
 *
 */
@Slf4j
@Component("myAuthSuccessHandler")
public class MyAuthenctiationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserService userService;
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
    log.info("###########登录成功");
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    log.info("name="+name);
    User user = userService.findOneUserByName(name);
    request.getSession().setAttribute("userid",user.getId());
    request.getSession().setAttribute("name",name);

    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(DataResult.build(200, "登录成功！", true)));
  }
}

