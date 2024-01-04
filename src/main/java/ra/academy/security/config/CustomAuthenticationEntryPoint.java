package ra.academy.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // tra ve thong bao loi
        log.error("error",authException.getMessage());
        response.setStatus(403);
        response.setHeader("error","forbiden");
        Map<String,String> map = new HashMap<>();
        map.put("message","Ban khong co quyen truy cap");
        new ObjectMapper().writeValue(response.getOutputStream(),map);
    }
}
