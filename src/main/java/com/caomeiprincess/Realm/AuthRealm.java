package com.caomeiprincess.Realm;

import com.caomeiprincess.common.CommonHolder;
import com.caomeiprincess.entity.User;
import com.caomeiprincess.exception.GlobalException;
import com.caomeiprincess.service.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 权限校验
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 身份校验
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
//    @Autowired
//    private GoogleAuthenticator googleAuthenticator;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User user = userService.findByName(username);
        if (user.getId() == null) {
            throw new GlobalException("用户名错误");
        }

        Integer secCode = CommonHolder.secCode.get();

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        if(!gAuth.authorize(user.getCode(),secCode)){
            throw new GlobalException("安全码错误,请联系管理员索要安全码");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
        return authenticationInfo;
    }
}
