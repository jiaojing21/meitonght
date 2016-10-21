package com.itsv.gbp.core.security.util;

import org.apache.commons.codec.binary.Base64;

/**
 * 说明文字
 * 
 * @author <a href="mailto:Ace8@live.cn">admin </a>
 * @version 1.0, 2005-11-8
 */
public class BasicAuthTool
{
    /**
     * 解析客户端传来的Basic方式的认证信息，解析出用户名和密码。
     * 
     * @param authorization
     * @return 返回用户名和密码的数组，如果解析失败，返回的数组长度为0
     */
    public static String[] getUserAndPass(String authorization)
    {
        //String header = httpRequest.getHeader("Authorization");

        if ((authorization != null) && authorization.startsWith("Basic "))
        {
            String base64Token = authorization.substring(6);
            String token = new String(Base64.decodeBase64(base64Token.getBytes()));

            String username = "";
            String password = "";
            int delim = token.indexOf(":");

            if (delim != -1)
            {
                username = token.substring(0, delim);
                password = token.substring(delim + 1);

                return new String[] { username, password };
            }

        }

        return new String[] {};
    }
}
