package com.springboot.healthmanage.service.impl;

import com.springboot.healthmanage.entity.User;
import com.springboot.healthmanage.mapper.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ログインフォームで入力されたusernameでDBを検索
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("ユーザー情報の認証に失敗しました: " + username));

        // DBから取得した情報（ユーザー名、パスワード）を
        //     Spring Security が扱える UserDetails オブジェクトに変換して返す
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // DBに保存されているパスワード
                Collections.emptyList()
        );
    }
}
