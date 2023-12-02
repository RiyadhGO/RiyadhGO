package sa.edu.yamama.riyadhgo.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sa.edu.yamama.riyadhgo.domain.User;

import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    private JwtTokenUtil jwtTokenUtil;

    private Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    public JwtFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Get authorization header and validate

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isEmpty(header) || !header.trim().startsWith("Bearer")) {
            chain.doFilter(request, response);

            return;
        }

        // Get jwt token and validate
        final String token = header.replace("Bearer:", "").trim();
        if (!jwtTokenUtil.isTokenExpired(token)) {
            logger.info("Authenticating from token");
            this.updateSecurityContextFromToken(token, request);

        }

        chain.doFilter(request, response);
    }

    private void updateSecurityContextFromToken(String token, HttpServletRequest request) {

        var id = jwtTokenUtil.getUserIDFromToken(token);
        var userName = jwtTokenUtil.getUsernameFromToken(token);
        var role = jwtTokenUtil.grantedAuthorityFromToken(token);
        var principal = new User();
        principal.setUserId(id);
        principal.setEmail(userName);
        principal.setRole(role.getAuthority());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                principal, null, List.of(role));
        this.logger.info("username = " + userName + " *** role = " + role.getAuthority());
    
        var ctx = SecurityContextHolder.getContext();
        ctx.setAuthentication(authentication);
        SecurityContextHolder.setContext(ctx);
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

}
