package com.mitrais.cdc.mongodbapp.utility;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Getter
@Setter
@Component
public class TokenUtility {

    private String secretKey = "RahasiaDong";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", role);
        Date now = new Date();
        Date validity = new Date(now.getTime()+86400000);

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public boolean validateID(String id){

        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(id);
            log.info("Username", claims.getBody().getExpiration());
            /*if(claims.getBody().getExpiration().before(new Date())){
                return false;
            }
*/
            /*log.info("Username from token:", claims.getBody().getSubject());
            if(userRepository.findByUsername(getUsernameFromtoken(id)) == null){
                return false;
            }*/

        }catch (JwtException | IllegalArgumentException e) {
            //throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
            e.printStackTrace();
        }

        return true;
    }

    public String getUsernameFromtoken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

}
