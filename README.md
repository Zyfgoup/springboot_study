#### 外层的父工程是SpringBoot整合了swagger2
启用只需要在启动类使用@EnableSwagger2
然后编写配置类即可
#### redis-demo
整合redis 实现一个点赞的小demo  详情见模块内的readme

#### handle-excepetion
ErrorCode 错误码 通过枚举类来实现自定义错误码
ErrorResponse 有异常时返回的错误实体类 包含一些信息
BaseException 继承RuntimeException 作为系统内所有自定义异常的父类
     成员变量为错误码和data
自定义的一些异常类 extends BaseException  构建实例时只需要传入data 每个异常类的错误码在构造方法里固定了
GlobalExceptionHandler 处理全局的异常 
@ControllerAdvise表示处理controller抛出的异常  可以指定特定的一些controller抛出的异常
加上 @ResponseBody 就是返回JSON格式的
可以直接使用@RestControllerAdvise

@ExceptionHandler使用在方法上 表示该方法处理哪些异常 一般只需要一个方法处理BaseException即可
也可以写更匹配的或者对应异常的处理方法  会走最匹配的

异常处理方法的返回值
可以使用ResponseEntity 那样需要设置Header HttpStatus 放入ErrorResponse作为body即可
也可以使用@ResponseStatus(code=HttpStatus.xxx)指定Http的相应状态 然后直接返回ErrorResponse即可
这两种的返回值 查看请求响应都会显示是400
而如果什么都不设置  直接返回ErrorResponse 返回的是200 但是Response Body还是ErrorResponse



#### springboot_CRUD
Springboot整合Mybaits、thymeleaf实现CRUD

```/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : testmybatis

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2020-05-07 15:42:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(10) NOT NULL,
  `department_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '市场部');
INSERT INTO `department` VALUES ('2', '技术部');
INSERT INTO `department` VALUES ('3', '销售部');
INSERT INTO `department` VALUES ('4', '客服部');
INSERT INTO `department` VALUES ('5', '公关部');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `employee_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` int(2) NOT NULL COMMENT '员工性别',
  `department_id` int(10) NOT NULL COMMENT '部门编号',
  `date` date NOT NULL COMMENT '入职日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '张三', 'zhangsan@gmail.com', '0', '1', '2020-02-12');
INSERT INTO `employee` VALUES ('2', '李四', 'lisi@qq.com', '1', '2', '2020-02-05');
INSERT INTO `employee` VALUES ('3', '王五', 'wangwu@126.com', '0', '3', '2020-02-15');
INSERT INTO `employee` VALUES ('4', '赵六', 'zhaoliu@163.com', '1', '4', '2020-02-21');
INSERT INTO `employee` VALUES ('5', '田七', 'tianqi@foxmail.com', '0', '3', '2020-02-14');
INSERT INTO `employee` VALUES ('10', '王伟', 'wangwei@gmail.com', '1', '3', '2020-02-08');
INSERT INTO `employee` VALUES ('11', '张伟', 'zhangwei@gmail.com', '1', '2', '2020-02-11');
INSERT INTO `employee` VALUES ('12', '李伟', 'liwei@gmail.com', '1', '3', '2020-02-18');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123456');
