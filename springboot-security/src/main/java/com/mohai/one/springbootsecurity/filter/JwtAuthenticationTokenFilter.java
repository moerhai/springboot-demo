//package com.mohai.one.springbootsecurity.filter;
//
//import com.mohai.one.springbootsecurity.jwt.JwtTokenHelper;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @Auther: moerhai@qq.com
// * @Date: 2020/9/20 14:30
// */
//@Component
//@Log4j2
//public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
//
//
//    @Autowired(required = false)
//    private UserDetailsService userDetailsService;
//
//    @Value("${jwt.header}")
//    private String header;
//
//    @Value("${jwt.tokenHead}")
//    private String tokenHead;
//
//    @Autowired
//    private JwtTokenHelper jwtTokenHelper;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request, HttpServletResponse response,
//            FilterChain chain) throws ServletException, IOException {
//        String authHeader = request.getHeader(this.header);
//
//            // The part after "Bearer " if (authHeader != null && authHeader.startsWith(tokenHead) && authHeader.length() > tokenHead.length() + 1) {
//            final String authToken = authHeader.substring(tokenHead.length() + 1);
//            String username = jwtTokenHelper.getUsernameFromToken(authToken);
//            log.info("checking authentication,username:{},authToken:{}", username, authToken);
//            // 校验token是否有效合法
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//                // 校验token是否过期
//                if (jwtTokenHelper.validateToken(authToken, userDetails)) {
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
//                            request));
//                    log.info("authenticated user " + username + ", setting security context");
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                } else {
//                    log.error("token过期，token:{}", authToken);
//                }
//            } else {
//                log.error("token失效，无法获取到用户信息，token:{}", authHeader);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//
//
//}
