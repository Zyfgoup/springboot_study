各种增强方法的执行顺序
环绕增强 调用process()前的语句-》before增强->执行方法-》环绕增强执行process()后
-》(有异常抛出)环绕增强方法内有捕获异常，则执行catch块内语句-》after增强-》afterReturning增强-》结束

如果环绕增强没有捕捉异常 只是抛出 那么不会执行process()后的方法 而是执行after增强-》afterThrowing增强-》结束

joinPoint.getSignature().getName() //返回方法名
Modifier.toString(joinPoint.getSignature().getModifiers())//方法权限修饰符
joinPoint.getArgs()[0].getClass().getName() //形参类型
joinPoint.getArgs()[0]//入参值
joinPoint.getArgs()[0].getClass().getName()+":"+joinPoint.getArgs()[0] //形参：值
((MethodSignature)joinPoint.getSignature()).getReturnType()//返回值类型

注意：如果使用了环绕增强要把结果return

环绕增强是阻塞的 如果要打印方法执行后的结果等需要一直等着方法执行完
建议使用before+afterReturning增强

SpringBoot是使用 Slf4j+logback  slf4j是日志抽象层 logback是日志实现层
使用方法
Logger log = LoggerFactory.getLogger(当前类.class);
或者直接使用@Slf4j注解 然后log.info debug....

logging:
  file:
    name: ./aop-demo/log/myApp.log
  level:
    root: info

