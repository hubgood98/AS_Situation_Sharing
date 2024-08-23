package com.example.as_situation_sharing_web.user.service;

import com.example.as_situation_sharing_web.domain.UserData;
import com.example.as_situation_sharing_web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserData> user = this.userRepository.findByUsername(username);

        if(user.isEmpty())
        {
            throw new UsernameNotFoundException(username+" 사용자를 찾을 수 없습니다.");
        }
        UserData userData = user.get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if("admin".equals(username))
        {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else if("user".equals(username))
        {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new User(userData.getUsername(), userData.getPassword(), grantedAuthorities);
    }
}
