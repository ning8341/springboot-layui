package com.blog.manager.common.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;

/**
 * @Title: DigestUtils
 * @Description:
 * @author: dongwn
 * @version: 1.0
 */
public class DigestUtils {

    /**
     *
     * 功能描述: MD5加密账号密码
     *
     * @param: 
     * @return: 
     * @auther: dongwn
     */
    public static String Md5(String userName,String password){
        Md5Hash hash = new Md5Hash(password, ByteSource.Util.bytes(userName), 2);
        return hash.toString();
    }
}
