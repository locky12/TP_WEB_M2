package com.tp.webtp.configuration;

import com.tp.webtp.Auth.AppAuthProvider;
import com.tp.webtp.Auth.AuthenticationEntryPointImpl;
import com.tp.webtp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;


@Configuration
    @EnableWebSecurity
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        UserService userService;

        @Autowired
        private AuthenticationEntryPoint authEntryPoint;



        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.headers().frameOptions().disable();

           http.authenticationProvider(getProvider());


            http.authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/logout").permitAll()
                    .antMatchers("/users").permitAll();
//                    .antMatchers("/users/*").authenticated()
//                    .antMatchers("/series/*").authenticated();
//                    .antMatchers("/").permitAll()





            http.authorizeRequests().anyRequest().authenticated();

            // Use AuthenticationEntryPoint to authenticate user/password
            http.httpBasic();

        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            return bCryptPasswordEncoder;
        }

        @Bean
        public AuthenticationProvider getProvider() {
            AppAuthProvider provider = new AppAuthProvider();
            provider.setUserDetailsService(userService);
            return provider;
        }
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
             auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

            String password = "123";

            String encrytedPassword = this.passwordEncoder().encode(password);
            System.out.println("Encoded password of 123=" + encrytedPassword);


            InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> //
                    mngConfig = auth.inMemoryAuthentication();

            // Defines 2 users, stored in memory.
            // ** Spring BOOT >= 2.x (Spring Security 5.x)
            // Spring auto add ROLE_
            UserDetails u1 = User.withUsername("tom").password(encrytedPassword).roles("USER").build();
            UserDetails u2 = User.withUsername("jerry").password(encrytedPassword).roles("USER").build();

            mngConfig.withUser(u1);
            mngConfig.withUser(u2);

            // If Spring BOOT < 2.x (Spring Security 4.x)):
            // Spring auto add ROLE_
            // mngConfig.withUser("tom").password("123").roles("USER");
            // mngConfig.withUser("jerry").password("123").roles("USER");
        }


}
