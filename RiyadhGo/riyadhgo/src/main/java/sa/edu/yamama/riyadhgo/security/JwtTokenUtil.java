package sa.edu.yamama.riyadhgo.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import sa.edu.yamama.riyadhgo.domain.User;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -3301605591108950415L;

	public static final SecretKey key = Jwts.SIG.HS256.key().build();
	private Logger logger = org.slf4j.LoggerFactory.getLogger(JwtTokenUtil.class);

	@Value("${jwt.token.expiration.in.seconds}")
	private Long expiration;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public String getUserRoleFromToken(String token) {
		var claims = this.getAllClaimsFromToken(token);
		String role = (String) claims.get("role");
		return role;
	}

	public Long getUserIDFromToken(String token) {
		var claims = this.getAllClaimsFromToken(token);

		return Long.parseLong(claims.get("userId").toString());
	}

	public GrantedAuthority grantedAuthorityFromToken(String token) {
		var claims = this.getAllClaimsFromToken(token);
		String role = (String) claims.get("role");
		return new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return role;
			}
		};
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {

		logger.info("token:[" + token + "]");
		var jwsClaims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
		// Claims cl = jwsClaims.getPayload();
		return jwsClaims.getPayload();
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(User userDetails) {
		Map<String, Object> claims = new HashMap<>();
		if (userDetails.getAuthorities() != null && !userDetails.getAuthorities().isEmpty()) {
			var auth = userDetails.getAuthorities().stream().findFirst().get();
			claims.put("role", auth.getAuthority());
		} else {
			claims.put("role", UserRoleNames.USER);
		}
		claims.put("userId", userDetails.getUserId());
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		final Date createdDate = new Date(System.currentTimeMillis());
		final Date expirationDate = calculateExpirationDate(createdDate);

		String jws = Jwts.builder().subject(subject).claim("role", claims.get("role"))
				.claim("userId", claims.get("userId"))
				.issuedAt(createdDate).expiration(expirationDate).signWith(key).compact();
		return jws;
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public String refreshToken(String token) {
		final Date createdDate = new Date();
		final Date expirationDate = calculateExpirationDate(createdDate);
		final Claims claims = getAllClaimsFromToken(token);

		String jws = Jwts.builder().subject(claims.getSubject()).claim("role", claims.get("role"))
				.issuedAt(createdDate).expiration(expirationDate).signWith(key).compact();
		return jws;
	}

	private Date calculateExpirationDate(Date createdDate) {
		return new Date(createdDate.getTime() + expiration * 1000);
	}
}
