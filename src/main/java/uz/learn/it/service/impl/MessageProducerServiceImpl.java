package uz.learn.it.service.impl;

import io.jsonwebtoken.Claims;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import uz.learn.it.dto.request.TokenRequestDTO;
import uz.learn.it.enums.Role;

@Aspect
@Service
public class MessageProducerServiceImpl {
    private final JmsTemplate jmsTemplate;
    private final JwtService jwtService;

    @Autowired
    public MessageProducerServiceImpl(JmsTemplate jmsTemplate, JwtService jwtService) {
        this.jmsTemplate = jmsTemplate;
        this.jwtService = jwtService;
    }

    @AfterReturning(
            pointcut = "execution(* uz.learn.it.service.impl.JwtService.generateToken(org.springframework.security.core.userdetails.UserDetails, uz.learn.it.entity.Client))", // Update package as per your app
            returning = "token"
    )
    public void sendMessage(String token) {
        long clientId = jwtService.extractClientId(token);
        Role role = Enum.valueOf(Role.class, jwtService.extractRoles(token));

        TokenRequestDTO tokenRequestDTO = new TokenRequestDTO();
    }
}
