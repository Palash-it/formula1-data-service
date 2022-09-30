package com.recommit.assignment.formula1.formula1dataservice.security.serviceImpl;

import com.recommit.assignment.formula1.formula1dataservice.entity.UserEntity;
import com.recommit.assignment.formula1.formula1dataservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("login.user.notFound",
                        new String[]{username}, LocaleContextHolder.getLocale())));
        log.info("====[User found:{}]====", username);
        return new AppUserDetailsImpl(userEntity);
    }
}
