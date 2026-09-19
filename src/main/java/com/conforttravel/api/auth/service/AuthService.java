package com.conforttravel.api.auth.service;

import com.conforttravel.api.auth.dto.LoginRequest;
import com.conforttravel.api.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}