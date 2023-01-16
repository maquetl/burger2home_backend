package com.isl.lionelmaquet.burger2home.Utils;

import com.isl.lionelmaquet.burger2home.Security.services.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class AuthUtils {
    public static Long getCurrentUserId(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }

    public static boolean currentUserIsUserRole(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"));
    }

    public static boolean currentUserIsAdmin(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public static final String preauth_admin = "hasRole('ADMIN')";
    public static final String preauth_connected = "hasRole('USER') or hasRole('MARKETING') or hasRole('STOCK') or hasRole('ADMIN')";

    public static final String preauth_stock = "hasRole('STOCK') or hasRole('ADMIN')";

    public static final String preauth_marketing = "hasRole('MARKETING') or hasRole('ADMIN')";
}
