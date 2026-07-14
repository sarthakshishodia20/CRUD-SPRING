package in.strikes.crudApplicationDemo.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

// @Component — Spring ko batata hai ki yeh ek Bean hai, automatically register hoga
@Component
public class LoggingFilter implements Filter {

    // =====================================
    // doFilter() — yeh method tab call hota hai jab bhi koi HTTP request aata hai
    // =====================================
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Step 1: Incoming request ko HttpServletRequest mein cast karo
        // (kyunki ServletRequest generic hai, HTTP-specific methods nahi hote usmein)
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Step 2: Request ki details note karo (BEFORE processing)
        String method = httpRequest.getMethod();           // GET, POST, PUT, DELETE
        String uri = httpRequest.getRequestURI();          // /api/students/get
        long startTime = System.currentTimeMillis();       // Time jab request aayi

        System.out.println("======================================");
        System.out.println("🔵 [REQUEST IN]  " + method + " " + uri);
        System.out.println("   Time: " + new java.util.Date());

        // =========================================================
        // Step 3: chain.doFilter() — SABSE IMPORTANT LINE!
        // Yeh request ko AAGE bhejta hai (next filter ya controller ko)
        // Agar yeh nahi likhoge → request ruk jayegi, response nahi milega!
        // =========================================================
        chain.doFilter(request, response);

        // =========================================================
        // Step 4: Yeh code chain.doFilter() ke BAAD execute hota hai
        // Matlab → Controller process kar chuka hota hai, response ready hai
        // =========================================================
        long timeTaken = System.currentTimeMillis() - startTime;
        int statusCode = httpResponse.getStatus();   // 200, 201, 404, 500 etc.

        System.out.println("🟢 [RESPONSE OUT] " + method + " " + uri);
        System.out.println("   Status: " + statusCode + " | Time Taken: " + timeTaken + "ms");
        System.out.println("======================================");
    }
}
