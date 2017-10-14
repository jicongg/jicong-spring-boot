package cn.com.jicongg.config.security.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cong.ji
 *     <p>2017年10月10日
 */
public class MyAuthorizingRealm extends AuthorizingRealm {

  private static final Logger log = LoggerFactory.getLogger(MyAuthorizingRealm.class);

//  @Autowired 
//  private LoginInfoService loginInfoService;

  /** 认证. */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    log.info("doGetAuthenticationInfo()");
    String username = (String) token.getPrincipal();
    String password = new String((char[]) token.getCredentials()); // 得到密码
    //		try {
    //			LoginInfo loginInfo = loginInfoService.selectOne(record);
    //			if (!loginInfo.getPassword().equals(password)) {
    //				throw new IncorrectCredentialsException(); // 如果密码错误
    //			}
    //		} catch (IncorrectCredentialsException e) {
    //			throw new IncorrectCredentialsException(); // 如果密码错误
    //		} catch (Exception e) {
    //			// 如果用户名错误
    //			throw new UnknownAccountException();
    //		}

    return new SimpleAuthenticationInfo(
        username, password, this.getName()); // 放入shiro.调用CredentialsMatcher检验密码
  }

  /** 授权. */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
    log.info("doGetAuthorizationInfo()");
    List<String> permissions = new ArrayList<>();
    // Set<Role> roles = user.getRoles();
    // if (roles.size() > 0) {
    // for (Role role : roles) {
    // Set<Module> modules = role.getModules();
    // if (modules.size() > 0) {
    // for (Module module : modules) {
    // permissions.add(module.getMname());
    // }
    // }
    // }
    // }
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.addStringPermissions(permissions); // 将权限放入shiro中. return info;
    return info;
  }
}
