package com.itsv.gbp.core.security.util;

import org.apache.commons.codec.binary.Base64;

/**
 * ˵������
 * 
 * @author <a href="mailto:Ace8@live.cn">admin </a>
 * @version 1.0, 2005-11-8
 */
public class BasicAuthTool
{
    /**
     * �����ͻ��˴�����Basic��ʽ����֤��Ϣ���������û��������롣
     * 
     * @param authorization
     * @return �����û�������������飬�������ʧ�ܣ����ص����鳤��Ϊ0
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
