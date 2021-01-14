package com.jay.liqui.config;

import javax.sql.DataSource;
import com.jay.liqui.jwt.JWTAuthorizationFilter;
import com.jay.liqui.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.core.annotation.Order;

@Configuration
//@Order(1)
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

/*    @Autowired
    private UserDetailsService userDetailsService; */

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }*/



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Cross-origin-resource-sharing: localhost:8080, localhost:4200(allow for it.)
        http.cors().and()
                .authorizeRequests()
                //These are public paths
                .antMatchers("/resources/**",  "/error", "/api/user/**").permitAll()
                //These can be reachable for just have admin role.
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                //All remaining paths should need authentication.
                .anyRequest().fullyAuthenticated()
                .and()
                //logout will log the user out by invalidated session.
                .logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/user/logout", "POST"))
                .and()
                //login form and path
                .formLogin().loginPage("/api/user/login").and()
                //enable basic authentication
                .httpBasic().and()
                //We will handle it later.
                //Cross side request forgery
                .csrf().disable();

        //jwt filter
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(),jwtTokenProvider));
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select username, password, enabled from usr01 where username=?")
                .authoritiesByUsernameQuery("select username, role from usr01 where username=?")
        ;


    }


    //---------------------------
/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/edit/*", "/delete/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
        ;
    }
*/



    //@Override

    /*protected void configure(HttpSecurity http) throws Exception {

        //Cross-origin-resource-sharing: localhost:8080, localhost:4200(allow for it.)
        http.cors().and()
                .authorizeRequests()
                //These are public paths
                .antMatchers("/resources/**",  "/error", "/api/user/**").permitAll()
                //These can be reachable for just have admin role.
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                //All remaining paths should need authentication.
                .anyRequest().fullyAuthenticated()
                .and()
                //logout will log the user out by invalidated session.
                .logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/user/logout", "POST"))
                .and()
                //login form and path
                .formLogin().loginPage("/api/user/login").and()
                //enable basic authentication
                .httpBasic().and()
                //We will handle it later.
                //Cross side request forgery
                .csrf().disable();

        //jwt filter
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(),jwtTokenProvider));


    }*/



/*    @Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("jayendra")
                .password("titan123")
                .roles("USER")
                .and()
                .withUser("admin")
                //.password("12345")
                .password("$2a$10$K65x7/TZEpXlnDGBZSC.5u0R.iO7U1CbkZ.VIIKjSkY8uOBNmeqzK")
                .roles("ADMIN")
                ;
    }*/

    //Cross origin resource sharing.
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
