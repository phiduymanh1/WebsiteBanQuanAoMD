//package org.example.shopquanao.Controller.Security;
//
//import lombok.AllArgsConstructor;
//import org.example.shopquanao.Entity.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.List;
//
//@AllArgsConstructor
//public class CustomUserDetails implements UserDetails {
//    private final User user;
//
//    @Override
//    public List<GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() { return true; }  // account khong hết hạn
//
//    @Override
//    public boolean isAccountNonLocked() { return true; } // tài khoản không bị khóa
//
//    @Override
//    public boolean isCredentialsNonExpired() { return true; } //xác thực thông tin tài khoản
//
//    @Override
//    public boolean isEnabled() { return true; } // tài khoản được kích hoạt
//}
