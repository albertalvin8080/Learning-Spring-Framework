package com.albert.authenticationservice.endpoint.controllers;

import com.albert.core.model.AppUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/user")
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class UserInfoController
{
    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Returns the credentials of the user present in the JWT token.",
            responses = {@ApiResponse(responseCode = "200")},
            tags = {"Info"},
            /**
             * Here you tried to manually create a header named "Authorization" and receive a token.
             * However, the OpenAPI 3 specification, which is used by springdoc-openapi, does not
             * allow explicitly adding an Authorization header. This behavior is not related to
             * springdoc-openapi, but to swagger-ui which respects the OpenAPI Spec.
             * */
//            parameters = @Parameter(
//                    in = ParameterIn.HEADER,
//                    name = "Authorization",
//                    description = "JWT token for authorization",
//                    required = true,
//                    schema = @Schema(type = "string")
//            ),
            /**
             * Instead, use this SecurityScheme to send the Bearer Token.
             * */
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<AppUser> userInfo(Principal principal) {
//        AppUser appUser = (AppUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        final AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(appUser);
    }
}
