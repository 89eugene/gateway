package com.apigateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class AuthHeaderFilter extends ZuulFilter {
  private Logger log = LoggerFactory.getLogger(AuthHeaderFilter.class);

  @Override
  public String filterType() {
    return PRE_TYPE;
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    if (request.getHeader("X-User-Session-ID") == null) {
      String sessionId = UUID.randomUUID().toString();
      ctx.addZuulRequestHeader("X-User-Session-ID", sessionId);
    }

    return null;
  }
}
