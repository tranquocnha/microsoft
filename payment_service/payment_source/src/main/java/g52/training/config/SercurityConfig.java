package g52.training.config;

//import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SercurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of("*"));
                    cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(List.of("*"));
                    return cors;
                }).and().
                authorizeRequests().
                anyRequest().authenticated().
                and().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");
    }
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

}
