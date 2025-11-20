package com.example.serving_web_content.aop;

import com.example.serving_web_content.model.User;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleCheckAspect {
    @Autowired
    private HttpSession httpSession;

    @Before("@annotation(com.example.serving_web_content.aop.RolesAllowed)")
    public void checkRole(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RolesAllowed rolesAllowed = signature.getMethod().getAnnotation(RolesAllowed.class);
        String[] allowedRoles = rolesAllowed.value();

        // Retrieve current user from session
        Object userObj = httpSession.getAttribute("currentUser");
        if (userObj == null) {
            throw new AccessDeniedException("User is not logged in");
        }
        User currentUser = (User) userObj;
        String userRole = currentUser.getRole();

        boolean permitted = false;
        for (String role : allowedRoles) {
            if (role.equalsIgnoreCase(userRole)) {
                permitted = true;
                break;
            } else if (role.equalsIgnoreCase("SELF")) {
                // check currentuser.getUserId == paramUserId
                // if yes
                // permitted = true;
                // break;
                Long targetId = extractUserId(joinPoint);
                if (targetId != null && targetId.equals(currentUser.getId())) {
                    permitted = true;
                    break;
                }
            }
        }

        if (!permitted) {
            throw new AccessDeniedException("User with role " + userRole + " does not have permission");
        }
    }

    // helper method that reads @PathVariable Long userId from the method arguments
    private Long extractUserId(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Long) {
                return (Long) arg;
            }
        }
        return null;
    }
}
