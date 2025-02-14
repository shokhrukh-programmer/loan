package uz.learn.it.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import uz.learn.it.dto.request.TokenRequestDTO;
import uz.learn.it.enums.Role;
import uz.learn.it.service.impl.JwtService;

@Aspect
@Service
public class TokenSendingAspect {
    private final JmsTemplate jmsTemplate;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TokenSendingAspect(JmsTemplate jmsTemplate, JwtService jwtService, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @AfterReturning(
            pointcut = "execution(* uz.learn.it.service.impl.JwtService.generateToken(org.springframework.security.core.userdetails.UserDetails, uz.learn.it.entity.Client))", // Update package as per your app
            returning = "token"
    )
    public void sendMessage(String token) throws JsonProcessingException {
        long clientId = jwtService.extractClientId(token);
        Role role = Enum.valueOf(Role.class, jwtService.extractRoles(token));

        TokenRequestDTO tokenRequestDTO = TokenRequestDTO.builder()
                .token(token)
                .role(role)
                .clientId(clientId)
                .build();

        String json = objectMapper.writeValueAsString(tokenRequestDTO);

        jmsTemplate.convertAndSend("token-queue", json);
    }
}

