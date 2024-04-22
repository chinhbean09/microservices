package com.chinhbean.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;






@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config>{
	private final WebClient.Builder webClientBuilder;
	
	public AuthFilter(WebClient.Builder webClientBuilder) {
		super(Config.class);
		this.webClientBuilder = webClientBuilder;
	}
	
	
	 public static class Config {
        // empty class as I don't need any particular configuration
    }

	@Override
	public GatewayFilter apply(Config config) {
		  return (exchange, chain) -> {
			  if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                  throw new RuntimeException("Missing authorization information");
              }

              String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

              String[] parts = authHeader.split(" ");

              if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                  throw new RuntimeException("Incorrect authorization structure");
              }
           
              Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
      		JWTVerifier verifier = JWT.require(algorithm).build();
      		DecodedJWT decodedJWT = verifier.verify(parts[1]);
      		String username = decodedJWT.getSubject();
           if(username == "" || username == null) {
        	   throw new RuntimeException("Athorization error");
           }
        	   ServerHttpRequest request = exchange.getRequest().mutate().
        	      header("X-auth-username", username).
        	      build();
        	      return chain.filter(exchange.mutate().request(request).build());
          
                 
          
		  };
	}
}
