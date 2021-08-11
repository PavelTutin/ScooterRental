package by.tutin.configuration;

import by.tutin.security.jwt.JwtAccessDeniedHandler;
import by.tutin.security.jwt.JwtAuthenticationEntryPoint;
import by.tutin.security.jwt.JwtFilter;
import by.tutin.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    private static final String ADMIN_ENDPOINT = "/admin/**";
    private static final String AUTH_ENDPOINT = "/auth/**";
    private static final String USERS_ENDPOINT = "/users/admin**";
    private static final String ORDERS_ENDPOINT = "/orders/admin**";
    private static final String SCOOTERS_ENDPOINT = "/scooters/admin**";
    private static final String SPOTS_ENDPOINT = "/spots/admin**";


    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(tokenProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(USERS_ENDPOINT).hasRole("ADMIN")
                .antMatchers(ORDERS_ENDPOINT).hasRole("ADMIN")
                .antMatchers(SCOOTERS_ENDPOINT).hasRole("ADMIN")
                .antMatchers(SPOTS_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated();
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
