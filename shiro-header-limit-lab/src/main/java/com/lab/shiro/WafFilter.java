package com.lab.shiro;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class WafFilter implements Filter {

    private static final int MAX_REMEMBER_ME_LENGTH = 3500;
    private static final int MIN_EXPLOIT_LENGTH = 500;
    private static final byte[] SHIRO_KEY = java.util.Base64.getDecoder().decode("kPH+bIxk5D2deZiIxcaaaA==");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String rememberMe = getRememberMeCookie(req);
        if (rememberMe != null && rememberMe.length() > MIN_EXPLOIT_LENGTH) {
            if (rememberMe.length() > MAX_REMEMBER_ME_LENGTH) {
                blockedLength(resp);
                return;
            }
            if (isShiroExploit(rememberMe)) {
                blocked(resp);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isShiroExploit(String cookieValue) {
        try {
            byte[] encrypted = java.util.Base64.getDecoder().decode(cookieValue);
            if (encrypted.length < 32) {
                return false;
            }
            byte[] iv = Arrays.copyOfRange(encrypted, 0, 16);
            byte[] cipherText = Arrays.copyOfRange(encrypted, 16, encrypted.length);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SHIRO_KEY, "AES"), new IvParameterSpec(iv));
            cipher.doFinal(cipherText);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    private String getRememberMeCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("rememberMe".equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    private void blockedLength(HttpServletResponse resp) throws IOException {
        resp.setStatus(403);
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().write("WAF: 5bCP5bCP6ISa5pys5a2Q5Y+v56yR5Y+v56yR==");
    }

    private void blocked(HttpServletResponse resp) throws IOException {
        resp.setStatus(403);
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().write("WAF: 5aSn6buR5a6i77yM5Y2z5bCG5rS+5Ye6ZmJp6bih5ZOU5L2g==");
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
