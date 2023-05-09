package com.shopspiker.auth.service.util;

import com.shopspiker.auth.jpa.constants.JwtTokenTypeEnum;
import com.shopspiker.auth.jpa.constants.PermissionsEnum;
import com.shopspiker.auth.jpa.entity.Client;
import com.shopspiker.auth.jpa.entity.Role;
import com.shopspiker.auth.jpa.entity.User;
import com.shopspiker.auth.service.impl.RoleServiceImpl;
import com.shopspiker.common.web.exception.CustomApplicationException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtUtil {

    public static final String JWT_TOKEN_TYPE = "jwtTokenType";
    public static final String CLIENT_ID = "clientId";
    private static final String KEY_ALIAS = "jwt";
    private static final String KEY_PASSWORD = "password";
    private static String secretKey;
    @Autowired
    private RoleServiceImpl roleService;

    public JwtUtil() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(ResourceUtils.getFile("classpath:jwt.jks")), KEY_PASSWORD.toCharArray());

            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS,
                    new KeyStore.PasswordProtection(KEY_PASSWORD.toCharArray()));
            secretKey = Base64.getMimeEncoder().encodeToString(privateKeyEntry.getPrivateKey().getEncoded());

        } catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException
                 | UnrecoverableEntryException e) {
            log.error(e.getLocalizedMessage());
            System.exit(1);
        }
    }

    public String generateRegistrationToken(String mobileNumber, Client client) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWT_TOKEN_TYPE, JwtTokenTypeEnum.REGISTRATION_TOKEN.toString());
        claims.put(CLIENT_ID, client.getId());

        int expiryTimeInSeconds = client.getAccessTokenValiditySeconds();

        return Jwts.builder().setClaims(claims).setSubject(mobileNumber)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * expiryTimeInSeconds))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public String generateClientToken(Client client) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWT_TOKEN_TYPE, JwtTokenTypeEnum.CLIENT_CREDENTIALS.toString());
        claims.put("id", client.getId());
        claims.put("roles", client.getAuthorities());

        int expiryTimeInSeconds = client.getAccessTokenValiditySeconds();

        return Jwts.builder().setClaims(claims).setSubject(client.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * expiryTimeInSeconds))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public String generateToken(User user, Client client, JwtTokenTypeEnum jwtTokenType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWT_TOKEN_TYPE, jwtTokenType.toString());
        claims.put("firstName", user.getFirstName());
        claims.put("roles", user.getRoles().stream().map(r -> r.getId()).collect(Collectors.toSet()));

        int expiryTimeInSeconds = (JwtTokenTypeEnum.REFRESH_TOKEN == jwtTokenType)
                ? client.getRefreshTokenValiditySeconds()
                : client.getAccessTokenValiditySeconds();

        return Jwts.builder().setClaims(claims).setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * expiryTimeInSeconds))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error(e.getLocalizedMessage());
            throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "UNAUTHORIZED");
        } catch (ExpiredJwtException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    public String getSubjectFromToken() {
        String token = extractJwtFromRequest();
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<SimpleGrantedAuthority> getRolesFromToken() {
        String token = extractJwtFromRequest();
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        List<String> userRoles = (List<String>) claims.get("roles");
        List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
        Role role = null;

        if (userRoles != null && userRoles.size() > 0) {
            for (String userRole : userRoles) {
                try {
                    role = roleService.findByIdAndActive(userRole);
                    roles.add(new SimpleGrantedAuthority(userRole.startsWith("ROLE_") ? userRole : "ROLE_" + userRole));

                    if (!CollectionUtils.isEmpty(role.getPermissions())) {
                        for (PermissionsEnum permission : role.getPermissions()) {
                            roles.add(new SimpleGrantedAuthority(permission.toString()));
                        }
                    }
                } catch (CustomApplicationException e) {
                    log.error("role not found in the token");
                }
            }
        }
        return roles;
    }

    public boolean checkCurrentTokenType(JwtTokenTypeEnum jwtTokenTypeEnum) {
        String token = extractJwtFromRequest();
        validateToken(token);
        return jwtTokenTypeEnum.toString().equalsIgnoreCase(
                (String) getClaimValueFromToken(token, JWT_TOKEN_TYPE));
    }

    public Object getClaimValueFromToken(String token, String claimKey) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.get(claimKey);
    }

    public String extractJwtFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
