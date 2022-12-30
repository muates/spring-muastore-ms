package com.muates.userservice.security;

import com.muates.userservice.model.entity.User;
import com.muates.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("Username not exist!!");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.get().getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().toString()));
        });

        return new CustomUserDetail(
                new org.springframework.security.core.userdetails.User(
                        user.get().getUsername(),
                        user.get().getPassword(),
                        user.get().getEnabled(),
                        true,
                        true,
                        true,
                        authorities
                )
        );
    }

    // Todo: signup eklenecek
}
