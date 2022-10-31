package org.dflynt.primmy.journalservice.interceptor;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.dflynt.primmy.journalservice.exceptions.InvalidTokenException;
import org.dflynt.primmy.journalservice.exceptions.TokenExpirationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;

@Component
public class Interceptor implements HandlerInterceptor {

    Logger LOGGER = LoggerFactory.getLogger(Interceptor.class);

    private final String ACCESS_TOKEN = "37f3d42fd20eb72055aea46bbc31f00c5b49ca480b3c613a5288602f6d4af7a5";
    private final String REFRESH_TOKEN = "6d0e34ec58f18b8b18d952cc6a3d084410665a2ae382769ff80680aba9215";
    byte[] accessKeyBytes;
    byte[] refreshKeyBytes;

    Key accessKey;
    Key refreshKey;

    public Interceptor() {
        accessKeyBytes = Decoders.BASE64.decode(ACCESS_TOKEN);
        refreshKeyBytes = Decoders.BASE64.decode(REFRESH_TOKEN);

        accessKey = Keys.hmacShaKeyFor(accessKeyBytes);
        refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        LOGGER.info("Request interceptor -- " + request.getHeader("Authorization"));
        try {
            String token = request.getHeader("Authorization").split(" ")[1];

            Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token);
            return true;

        } catch(ExpiredJwtException e) {
            LOGGER.info("Logging expired JWT");
            response.setStatus(401);
            response.getWriter().write("Expired Token");

            return false;
        } catch(JwtException e) {
            LOGGER.info("Invalid token");
            response.setStatus(403);
            response.getWriter().write("Invalid Token");

            return false;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            response.setStatus(407);
            response.getWriter().write("Invalid Token");

            return false;
        }
    }
}
