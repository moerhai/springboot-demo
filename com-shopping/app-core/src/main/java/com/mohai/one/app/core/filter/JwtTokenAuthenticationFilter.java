package com.mohai.one.app.core.filter;

import com.mohai.one.app.core.token.JwtTokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  JWT登录授权过滤器
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/22 23:51
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * app.access.ignore.urls
     */
    @Value("${app.access.ignore-urls}")
    private String ignoreUrls;

    private List<String> urlList;

    @Override
    protected void initFilterBean() throws ServletException {
        if(!StringUtils.isBlank(ignoreUrls))
            urlList = Arrays.asList(ignoreUrls.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
    }

    /**
     * 检查请求中token数据，然后将授权信息放入SecurityContext
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        // 过滤忽略的uri
        boolean result = false;
        if(urlList != null){
            result = urlList.stream().anyMatch(url -> antPathMatcher.match(url,requestUri));
        }
        if(!result){
            LOGGER.debug("processing authentication for '{}'", request.getRequestURL());
            final String authToken = jwtTokenProvider.getToken(request);
            if (authToken != null && !"".equals(authToken)) {
                // The part after "Bearer "
                if (jwtTokenProvider.validateToken(authToken)) {
                    String username = jwtTokenProvider.getUserNameFromToken(authToken);
                    LOGGER.debug("checking authentication,get username:{}", username);
                    // 校验token是否有效合法,是否过期
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        LOGGER.debug("authenticated user " + username + ", setting security context");
                    } else {
                        LOGGER.error("token失效，无法获取到用户信息，token:{}", authToken);
                    }
                }else {
                    LOGGER.error("token已过期，需要重新登录，token:{}", authToken);
                }
            }else{
                LOGGER.debug("no valid JWT token found '{}'",authToken);
            }
        } else {
            LOGGER.debug("ignore the url '{}'", request.getRequestURL());
        }
        filterChain.doFilter(request, response);
    }
}