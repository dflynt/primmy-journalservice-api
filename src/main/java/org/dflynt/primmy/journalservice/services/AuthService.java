package org.dflynt.primmy.journalservice.services;

import antlr.Token;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.dflynt.primmy.journalservice.exceptions.InvalidTokenException;
import org.dflynt.primmy.journalservice.exceptions.TokenExpirationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.security.Key;

@Service
public class AuthService {

    private final String ACCESS_TOKEN = "37f3d42fd20eb72055aea46bbc31f00c5b49ca480b3c613a5288602f6d4af7a5";
    private final String REFRESH_TOKEN = "6d0e34ec58f18b8b18d952cc6a3d084410665a2ae382769faebf80680aba9215";
    byte[] accessKeyBytes;
    byte[] refreshKeyBytes;

    Key accessKey;
    Key refreshKey;
    Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    public AuthService() {
        accessKeyBytes = Decoders.BASE64.decode(ACCESS_TOKEN);
        refreshKeyBytes = Decoders.BASE64.decode(REFRESH_TOKEN);

        accessKey = Keys.hmacShaKeyFor(accessKeyBytes);
        refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes);
    }

    public boolean checkAuthToken(String authHeader) throws  Exception{
        //LOGGER.info("Interceptor authheader: " + authHeader);

        try {
            String token = authHeader.split(" ")[1];

            Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token);
            return true;

        } catch(ExpiredJwtException e) {
            throw new TokenExpirationException();
        } catch(JwtException e) {
            throw new InvalidTokenException();
        } catch (Exception e) {
            LOGGER.info("Catching general exception");
        }

        return true;
    }
}
