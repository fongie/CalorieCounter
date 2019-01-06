package se.kth.korlinge.caloriecounter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.korlinge.caloriecounter.data.User;
import se.kth.korlinge.caloriecounter.repositories.UserRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;

/**
 * Service that handles logic concerning the authentication.
 */
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

   /**
    * Called by spring security on login attempt. We dont use roles so roles are empty.
    * @param username
    * @return
    * @throws UsernameNotFoundException When trying to log in with an unknown username.
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username);
       if (user == null) {
          throw new UsernameNotFoundException(username);
       }
       return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<GrantedAuthority>());
    }

   /**
    * Register a new user
    * @param username
    * @param password
    * @return
    */
    public boolean register(String username, String password) {
       String encryptedPassword = passwordEncoder.encode(password);

       if (userRepository.findByUsername(username) == null) {
           User user = new User(username, encryptedPassword);
           userRepository.save(user);
           return true;
       } else {
           return false;
       }
    }
}
