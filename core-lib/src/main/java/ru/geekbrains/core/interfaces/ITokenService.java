package ru.geekbrains.core.interfaces;

import ru.geekbrains.core.models.UserInfo;

public interface ITokenService {

    String generateToken(UserInfo user);

    UserInfo parseToken(String token);
}
