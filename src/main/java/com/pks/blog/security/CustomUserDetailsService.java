package com.pks.blog.security;

import com.pks.blog.entity.Role;
import com.pks.blog.entity.User;
import com.pks.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameOrEmailId(usernameOrEmail,usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with user name or email"+usernameOrEmail));
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getUserName());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
               user.getPassword(),mapRolesToAuthority(user.getRoles()));
    }

    //check the roles of the user
    private Collection<? extends GrantedAuthority>mapRolesToAuthority(Set<Role>roles){
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
