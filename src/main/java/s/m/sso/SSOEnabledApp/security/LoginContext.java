package s.m.sso.SSOEnabledApp.security;

import org.springframework.security.core.context.SecurityContextHolder;

public enum LoginContext {
	CURRENT;
	private Object getPrincipal() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	public String getUsername(){
		Object principal=getPrincipal();
		String userName=null;
		if(principal!=null) {
			if(principal instanceof org.springframework.security.core.userdetails.User) {
				userName=((org.springframework.security.core.userdetails.User) principal).getUsername();
			}
			if(principal instanceof org.springframework.security.oauth2.core.oidc.user.OidcUser) {
				userName=((org.springframework.security.oauth2.core.oidc.user.OidcUser) principal).getEmail();
			}
		}
		return userName;
	}
	public String getAuthType() {
		Object principal=getPrincipal();
		if(principal!=null) {
			if(principal instanceof org.springframework.security.core.userdetails.User) {
				return "custom";
			}
			if(principal instanceof org.springframework.security.oauth2.core.oidc.user.OidcUser) {
				return "sso";
			}
		}
		return null;
	}
}