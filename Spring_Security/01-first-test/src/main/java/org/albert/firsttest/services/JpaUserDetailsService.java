package org.albert.firsttest.services;

import lombok.RequiredArgsConstructor;
import org.albert.firsttest.adapters.UserAdapter;
import org.albert.firsttest.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        return userRepository.findByName(username)
                .map(UserAdapter::new)
                .orElseThrow(() -> new UsernameNotFoundException("User '%s' not found.".formatted(username)));
    }
}
