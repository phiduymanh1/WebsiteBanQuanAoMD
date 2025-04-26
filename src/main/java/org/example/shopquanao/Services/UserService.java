//package org.example.shopquanao.Services;
//
//import lombok.RequiredArgsConstructor;
//import org.example.shopquanao.Repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//
//public class UserService implements UserDetailsService {
////
////    @Autowired
////    private UserRepository userRepository;
////
////    @Autowired
////    private PasswordEncoder passwordEncoder;
////
////    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        org.example.shopquanao.Entity.User entityUser = userRepository.findByUsername(username)
////                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
////
////        return User
////                .withUsername(entityUser.getUsername())
////                .password(entityUser.getPassword())
////                .roles(entityUser.getRole().name())
////                .build();
////    }
////
////    // Tạo user mới với password mã hóa
////    public org.example.shopquanao.Entity.User createUser(org.example.shopquanao.Entity.User entityUser) {
////        entityUser.setPassword(passwordEncoder.encode(entityUser.getPassword()));
////        return userRepository.save(entityUser);
////    }
////
////    // Tìm user theo username
////    public org.example.shopquanao.Entity.User findByUsername(String username) {
////        return userRepository.findByUsername(username).orElse(null);
////    }
//}
