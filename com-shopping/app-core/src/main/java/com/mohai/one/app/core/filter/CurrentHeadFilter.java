package com.mohai.one.app.core.filter;

import com.mohai.one.app.core.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/15 01:56
 */
public class CurrentHeadFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentHeadFilter.class);

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * access.ignore.url
     */
    @Value("${access.ignore-urls}")
    private String ignoreUrls;

    private List<String> urlList;

    @Override
    protected void initFilterBean() throws ServletException {
        if(!StringUtils.isBlank(ignoreUrls))
            urlList = Arrays.asList(ignoreUrls.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");
        }
        String requestUri = ((HttpServletRequest) request).getRequestURI();
        String requestBody = getStringFromStream(request);

        if (StringUtils.isBlank(requestBody)) {
            throw new BadRequestException("无法获取输入信息");
        }
        // 过滤忽略的uri
        boolean result = false;
        if(urlList != null){
            result = urlList.stream().anyMatch(url -> antPathMatcher.match(url,requestUri));
        }
        if(!result){

        }
        chain.doFilter(request,response);
    }

    private String getStringFromStream(ServletRequest req) {
        ServletInputStream is;
        try {
            is = req.getInputStream();
            int nRead = 1;
            int nTotalRead = 0;
            byte[] bytes = new byte[10240];
            while (nRead > 0) {
                nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
                if (nRead > 0) {
                    nTotalRead = nTotalRead + nRead;
                }
            }
            return new String(bytes, 0, nTotalRead, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}