package se.kth.korlinge.caloriecounter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.kth.korlinge.caloriecounter.data.User;
import se.kth.korlinge.caloriecounter.presentation.UserDTO;
import se.kth.korlinge.caloriecounter.repositories.UserRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;

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
    * @throws UsernameNotFoundException
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
    * @param userDTO
    * @return
    */
    public boolean register(UserDTO userDTO) {
       String username = userDTO.getUsername();
       String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());

       if (userRepository.findByUsername(username) == null) {
           User user = new User(username, encryptedPassword);
           userRepository.save(user);
           return true;
       } else {
           return false;
       }
    }
}
