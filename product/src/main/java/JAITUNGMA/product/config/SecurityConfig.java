package JAITUNGMA.product.config;

import JAITUNGMA.product.config.filter.CorsFilter;
import JAITUNGMA.product.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Autowired
    private OidcUserService oidcUserService;

    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                    .addFilterBefore(corsFilter(), SessionManagementFilter.class) //adds custom CorsFilter
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/home", "/signup",
                        "/css/**", "/js/**", "/images/*").permitAll()

                    .antMatchers("/product/add")
                    .access("hasRole('ROLE_ADMIN')")

                    .antMatchers("/product/delete")
                    .access("hasRole('ROLE_ADMIN')")

                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/product", true)
                    .permitAll()
                    .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/product", true)
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .permitAll();
            ClientRegistrationRepository repository =
                    getApplicationContext()
                            .getBean(ClientRegistrationRepository.class);

            if (repository != null) {
                http
                        .oauth2Login().clientRegistrationRepository(repository)
                        .userInfoEndpoint().oidcUserService(oidcUserService).and()
                        .loginPage("/login").permitAll();
            }
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }
}