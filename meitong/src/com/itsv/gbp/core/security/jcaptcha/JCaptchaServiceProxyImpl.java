package com.itsv.gbp.core.security.jcaptcha;

import org.acegisecurity.captcha.CaptchaServiceProxy;
import org.apache.log4j.Logger;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * 调用CaptchaService类，完jcaptcha的验证过程
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-1 下午03:08:52
 * @version 1.0
 */
public class JCaptchaServiceProxyImpl implements CaptchaServiceProxy {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(JCaptchaServiceProxyImpl.class);

	private CaptchaService jcaptchaService;

	public boolean validateReponseForId(String id, Object response) {
		if (logger.isDebugEnabled()) {
			logger.debug("validating captcha response");
		}
		return true;
		//杨文彦注销
		//2007-07-16
		//取消验证码图片
//
//		try {
//			boolean isHuman = jcaptchaService.validateResponseForID(id, response).booleanValue();
//			if (isHuman) {
//				if (logger.isDebugEnabled()) {
//					logger.debug("captcha passed");
//				}
//			} else {
//				if (logger.isDebugEnabled()) {
//					logger.debug("captcha failed");
//				}
//			}
//			return isHuman;
//
//		} catch (CaptchaServiceException cse) {
//			// fixes known bug in JCaptcha
//			logger.warn("captcha validation failed due to exception", cse);
//			throw new RuntimeException("captcha validation failed due to exception", cse);
//		}
	}

	public void setJcaptchaService(CaptchaService jcaptchaService) {
		this.jcaptchaService = jcaptchaService;
	}
}
