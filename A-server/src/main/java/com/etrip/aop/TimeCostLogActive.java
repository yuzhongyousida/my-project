package com.etrip.aop;

import com.alibaba.fastjson.JSONObject;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.collections.MapUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: wangteng
 * @description:
 * @date:2018/7/6
 */
public class TimeCostLogActive {


    /**
     * 记录方法执行时间的map（）
     */
    private Map<String, Long[]> costTimeMap = new HashMap<>();


    /**
     * before：前置通知
     *
     * @param joinPoint
     */
    public void before(JoinPoint joinPoint) throws Exception {

        // 获取方法入参值数组
        Object[] args = joinPoint.getArgs();

        // 获取类名
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();

        //获取方法名称
        String methodName = joinPoint.getSignature().getName();

        //获取参数名称和值
        Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, args);

        // nameAndArgs的两种类型，用实体类接收的类似这样： reqParams=com.ynet.finmall.innermanage.event.SaveOrUpdateRoleReqParam@616b9c0e
        // 用Map<String,Object>接收的是这样：menuNo=56473283，遍历这个map区分两种不同，使用不同的取值方式。
        // 根据获取到的值所属的不同类型通过两种不同的方法获取参数
        boolean flag = false;
        if (!MapUtils.isEmpty(nameAndArgs)) {
            for (Map.Entry<String, Object> entry : nameAndArgs.entrySet()) {
                if (entry.getValue() instanceof String) {
                    flag = true;
                    break;  // 跳出循环
                }
            }
        }

        StringBuffer sb = new StringBuffer();
        if (flag) {
            // 从Map中获取
            sb.append(JSONObject.toJSONString(nameAndArgs));
        } else {
            if (args != null) {
                for (Object object : args) {
                    if (object != null) {
                        if (object instanceof MultipartFile || object instanceof ServletRequest || object instanceof ServletResponse) {
                            continue;
                        }
                        sb.append(JSONObject.toJSONString(object))
                                .append(",");
                    }
                }
            }
        }


        System.out.println("--------before:" + sb.toString());
    }

    /**
     * 后置通知
     *
     * @param joinPoint
     */
    public void afterReturning(JoinPoint joinPoint) {
        System.out.println("--------afterReturning:" + joinPoint);
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     */
    public void around(ProceedingJoinPoint joinPoint) {
        System.out.println("--------around:" + joinPoint.toString());
    }

    /**
     * 抛出异常通知
     *
     * @param joinPoint
     */
    public void afterThrowing(JoinPoint joinPoint) {
        System.out.println("--------afterThrowing:" + joinPoint.toString());
    }

    /**
     * 最终通知
     *
     * @param joinPoint
     */
    public void after(JoinPoint joinPoint) {
        System.out.println("--------after:" + joinPoint.toString());
    }


    /**
     * 获取字段名和字段值 javassist工具
     *
     * @param cls        类
     * @param clazzName  类名
     * @param methodName 方法名
     * @param args       入参值数组
     * @return
     * @throws Exception
     */
    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();

        //
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);

        System.out.println(attr.toString());

        if (attr == null) {
            // exception
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            // paramNames即参数名
            map.put(attr.variableName(i + pos), args[i]);
        }
        return map;
    }


}
