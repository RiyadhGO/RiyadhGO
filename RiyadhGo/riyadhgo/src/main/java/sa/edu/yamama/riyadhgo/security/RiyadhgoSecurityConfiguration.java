package sa.edu.yamama.riyadhgo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

//The Riyadhgo security configuration class allow for http-specific functionalities in sending a request and response for authorization
@Configuration
@EnableWebSecurity
public class RiyadhgoSecurityConfiguration {

	@Autowired
	private RiyadhgoAuthService authProviderService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(rs ->
				rs.requestMatchers("/","/images","/auth/login","/auth/register").permitAll()
						.anyRequest().authenticated()
			
				);
		http.sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.exceptionHandling(x -> x.authenticationEntryPoint((req,res,ex)->{
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		}));
		http.addFilterBefore(new JwtFilter(this.jwtTokenUtil), UsernamePasswordAuthenticationFilter.class);
		http.cors(x -> x.disable()).csrf(x -> x.disable());
		
		http.authenticationProvider(this.authProviderService);
		return http.build();
	}
}
