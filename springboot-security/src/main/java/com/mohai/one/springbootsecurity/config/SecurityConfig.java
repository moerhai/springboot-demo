package com.mohai.one.springbootsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

/**
 * authentication：认证
 * authorization：授权
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/9 07:30
 */
//@Configuration
// 启用Security安全支持
//@EnableWebSecurity
// 开启在控制器的方法中使用
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * 通过 properties 和 WebSecurityConfigurer 配置类这两种配置用户的方式不能共存，
     * WebSecurityConfig 中的配置优先级更高，
     * 如果同时存在就会导致 properties 配置文件中的配置不生效，
     * 建议可以把 WebSecurityConfigurer 中的代码注释掉
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // super.configure(auth);
        // 基于内存进行认证 密码使用 BCryptPasswordEncoder 编码器进行加密
//        auth.inMemoryAuthentication()
//                // 从 Spring Security 5.0 开始必须要设置加密方式
//                .passwordEncoder(passwordEncoder())
//                // 在内存中创建一个名为 "user" 的用户，密码为 "123456"，拥有 "USER" 角色
//                .withUser("user")
//                .password(passwordEncoder().encode("123456")).roles("USER")
//                .and()
//                // 在内存中创建一个名为 "admin" 的用户，密码为 "123456"，拥有 "USER" 和 "ADMIN" 角色
//                .withUser("admin")
//                .password(passwordEncoder().encode("123456")).roles("USER", "ADMIN");


        // 基于数据库进行认证，使用 JDBC 进行身份验证
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER", "ADMIN")
                .build();
        auth.jdbcAuthentication().passwordEncoder(passwordEncoder())
                .dataSource(dataSource)
                //.withDefaultSchema()
                // 查询用户信息，如果表结构修改需要自定义SQL
                .usersByUsernameQuery(" select username,password,enabled from users where username = ?")
                // 查询权限信息，如果表结构修改需要自定义SQL
                .authoritiesByUsernameQuery("select username,authority from authorities where username = ?")
                .withUser(user)
                .withUser(admin);

//        // 3。使用 UserDetailsService 进行身份验证
//        auth.userDetailsService(null).and().authenticationProvider(null);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //基于HttpServletRequest使用限制访问
//        http.authorizeRequests()
//                .antMatchers("/css/**","/js/**")
//                .permitAll()        // css js 不用验证身份
//                .anyRequest()       // 其他路径
//                .authenticated()    // 其它页面全部需要验证
//                .and()
//                // formLogin 系统会自动配置 /login 页面用于登录
//                // 如果登录失败会重定向到 login/error/ 页面
//                .formLogin()   // 使用默认登录页面
//                .permitAll()
//                .and()
//                .exceptionHandling()
//                // 设置401错误跳转页面
//                .accessDeniedPage("/401")   // 无权限时跳转的页面
//                .and()
//                .logout().permitAll()
//                .and()
//                // 开启记住我功能，登录会添加Cookie,点击注销会删除Cookie
//                .rememberMe()
//                // 设置记住我参数
//                .rememberMeParameter("remember");
//
//
//        http
//                .authorizeRequests()
//               // .expressionHandler()
//                .antMatchers("/admin/**").hasAuthority("ADMIN")
//                .antMatchers("/user/**").hasAuthority("USER")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .logout()
//        ;
//
//    }


    @Override
    public void configure(WebSecurity web) {
        /*
         * 在Spring Boot中忽略静态文件路径，直接写静态文件的文件夹，
         * Spring Boot默认有静态文件的放置路径，如果应用spring
         * security，配置忽略路径 不应该从Spring Boot默认的静态文件开始
         * 如：在本项目中，所有的js和css都放在static下，如果配置忽略路径，则不能以static开始
         * 配置成web.ignoring().antMatchers("/static/*");这样是不起作用的
         */
        web.ignoring().antMatchers("/themes/**", "/script/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    UserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("user"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("USER", "ADMIN")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.deleteUser(user.getUsername());
//        users.createUser(user);
//        users.deleteUser(admin.getUsername());
//        users.createUser(admin);
//        return users;
//    }


    protected void configure(HttpSecurity http) throws Exception {
        http
                //	指定了多个授权规则。每个规则均按其声明顺序进行考虑。
                .authorizeRequests(authorize -> authorize

                        //	我们指定了任何用户都可以访问的多个URL模式。具体来说，如果URL以“ / resources /”开头，等于“ / signup”或等于“ / about”，则任何用户都可以访问请求。
                        .mvcMatchers("/resources/**", "/signup", "/about").permitAll()

                        // 以“ / admin /”开头的任何URL都将限于角色为“ ROLE_ADMIN”的用户。您将注意到，由于我们正在调用该hasRole方法，因此无需指定“ ROLE_”前缀。
                        .mvcMatchers("/admin/**").hasRole("ADMIN")

                        // 任何以“ / db /”开头的URL都要求用户同时具有“ ROLE_ADMIN”和“ ROLE_DBA”。您会注意到，由于我们使用的是hasRole表达式，因此无需指定“ ROLE_”前缀。
                        .mvcMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")

                        // 	任何尚未匹配的URL都会被拒绝访问。如果您不想意外忘记更新授权规则，这是一个很好的策略。
                        .anyRequest().denyAll()
                );
    }

}