package com.bnk.recipientssaverntaskresolver.jwt;

import com.bnk.miscellaneous.entities.User;
import com.bnk.recipientssaverntaskresolver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder encoder;

    public UserDetailsServiceImpl( ) {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetail = userRepository.findByUsername(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User Added Successfully";
    }
}
