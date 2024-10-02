package com.list.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.list.todolist.user.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if(servletPath.startsWith("/tasks/")) {

        var authorization = request.getHeader("Authorization");

        var authEncoded = authorization.substring("Basic".length()).trim();

        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

        String authString = new String(authDecoded);

        String[] authFinal = authString.split(":");
        String username = authFinal[0];
        String password = authFinal[1];

        var user = this.userRepository.findByUsername(username);
        if (user == null) {
            response.sendError(401, "Usuário não existente.");
        } else {
            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray());
            if (passwordVerify.verified) {
                request.setAttribute("idUser", user.getId());
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401, "Senha Incorreta");
            }
        }
    } else {
            filterChain.doFilter(request, response);
        }
    }
}
