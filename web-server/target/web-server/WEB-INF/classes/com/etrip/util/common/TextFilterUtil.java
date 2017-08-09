package com.etrip.util.common;

import com.etrip.dao.mysql.SensitiveWordDao;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/23.
 */
public class TextFilterUtil {

    private static final Logger logger = Logger.getLogger(TextFilterUtil.class);

    //敏感词库
    private static HashMap sensitiveWordMap = null;


    private static void init() {

        // 查询出数据库中的敏感词集合
        SensitiveWordDao sensitiveWordDao = (SensitiveWordDao)SpringBeanProxy.getBean("sensitiveWordDao");
        List<String> keyWords = sensitiveWordDao.selectSensitiveWordList();
//        List<String> keyWords = new ArrayList<String>();
//        keyWords.add("盘古李洪志志洪李");

        if(CollectionUtils.isEmpty(keyWords)){
            logger.error("敏感词数据库查询结果为空");
            return;
        }

        // 创建敏感词库
        sensitiveWordMap = new HashMap<>(keyWords.size());
        for (String keyWord : keyWords) {
            createKeyWord(keyWord);
        }
    }

    /**
     * 构建敏感词库(拆分)
     * @param keyWord
     */
    private static void createKeyWord(String keyWord) {
        if (sensitiveWordMap == null) {
            logger.error("sensitiveWordMap 未初始化!");
            return;
        }
        Map nowMap = sensitiveWordMap;
        for (Character c : keyWord.toCharArray()) {
            Object obj = nowMap.get(c);
            if (obj == null) {
                Map<String, Object> childMap = new HashMap<>();
                childMap.put("isEnd", "false");
                nowMap.put(c, childMap);
                nowMap = childMap;
            } else {
                nowMap = (Map) obj;
            }
        }
        nowMap.put("isEnd", "true");
    }


    /**
     * 检查敏感词
     * @param text
     * @return
     */
    public static List<String> checkSensitiveWord(String text) {
        if (sensitiveWordMap == null) {
            init();
        }
        List<String> sensitiveWords = new ArrayList<>();
        Map nowMap = sensitiveWordMap;
        for (int i = 0; i < text.length(); i++) {
            Character word = text.charAt(i);
            Object obj = nowMap.get(word);
            if (obj == null) {
                continue;
            }
            int j = i + 1;
            Map childMap = (Map) obj;
            while (j < text.length()) {
                if(j==text.length()-1 && childMap.size()==2){
                    sensitiveWords.add(text.substring(i));
                }
                if ("true".equals(childMap.get("isEnd"))) {
                    sensitiveWords.add(text.substring(i, j));
                }
                obj = childMap.get(text.charAt(j));
                if (obj != null) {
                    childMap = (Map) obj;
                } else {
                    break;
                }
                j++;
            }
        }
        return sensitiveWords;
    }

    /**
     * 将原字符串中的敏感词替代成*
     * @param sourceStr
     * @return
     */
    public static String replaceSensitiveWord(String sourceStr){
        // 入参空校验
        if(StringUtils.isEmpty(sourceStr)){
            return sourceStr;
        }

        // 敏感词校验
        List<String> sensitiveWords = checkSensitiveWord(sourceStr);
        if(CollectionUtils.isEmpty(sensitiveWords)){
            return sourceStr;
        }

        // 替换掉敏感词
        StringBuffer replaceStr = new StringBuffer("");
        for (String str : sensitiveWords){
            //清空
            replaceStr.delete(0,replaceStr.length());

            //获取等长的*字符串
            for (int i=0; i<str.length(); i++){
                replaceStr.append("*");
            }

            //将原字符串中的敏感词替换陈*
            sourceStr = sourceStr.replace(str, replaceStr);
        }

        System.out.println(sourceStr);
        return sourceStr;
    }



    public static void main(String[] args){
        init();
        String result = replaceSensitiveWord("盘古李洪志志洪李aaaa");
        System.out.println(result);
    }



}
