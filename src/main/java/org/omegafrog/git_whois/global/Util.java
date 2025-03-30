package org.omegafrog.git_whois.global;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.Password;

public class Util {

	public static class Jwt{

		public static String[] generateTokens(Map<String, Object> claims, int expSecond, int refreshExpSecond, String secret) {

			Date issuedAt = new Date();
			Date expiration = new Date(issuedAt.getTime() + 1000L * expSecond);
			Date refreshExpiration = new Date(issuedAt.getTime() + 1000L * refreshExpSecond);

			Password key = Keys.password(secret.toCharArray());
			String accessToken = Jwts.builder()
				.claims(claims)
				.issuedAt(issuedAt)
				.expiration(expiration)
				.signWith(key)
				.compact();
			String refreshToken = Jwts.builder()
				.claims(Map.of("jti", claims.get("jti")))
				.issuedAt(issuedAt)
				.expiration(refreshExpiration)
				.signWith(key)
				.compact();

			return new String[]{accessToken, refreshToken};
		}
	}
}
