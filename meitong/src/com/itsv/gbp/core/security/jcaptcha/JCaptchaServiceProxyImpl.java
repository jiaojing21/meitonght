package com.itsv.gbp.core.security.jcaptcha;

import org.acegisecurity.captcha.CaptchaServiceProxy;
import org.apache.log4j.Logger;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * ����CaptchaService�࣬��jcaptcha����֤����
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-1 ����03:08:52
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
		//������ע��
		//2007-07-16
		//ȡ����֤��ͼƬ
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
