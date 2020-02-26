**一、简单题**

1、Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？

​		答：动态sql用来拼接sql语句 动态sql有 if 、where、 foreach、 choose、trim 、bind等 

​				原理为使用OGNL从sql参数中计算表达式的值。根据表达式的值动态拼接sql

2、Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？

​		答：支持

​				设置lazyLoadingEnabled为true开启延迟加载 实现原理 遍历resultMapping 如果有需要懒加载的属性，通过动态代理为这个result对象创建一个代理对象。代理类继承了result对象并重写了对象方法 当对象触发"equals", "clone", "hashCode", "toString"等方法时就会触发懒加载也就是一次查询操作

3、Mybatis都有哪些Executor执行器？它们之间的区别是什么？

​	答：Mybatis的Executor执行器有：BaseExecutor、 CachingExecutor、 SimpleExecutor、 ReuseExecutor 、BatchExecutor。其中 SimpleExecutor、 ReuseExecutor 、BatchExecutor是BaseExecutor的子类，CachingExecutor在Executor的基础上加入了缓存的功能。Mybatis默认使用SimpleExecutor。SimpleExecutor每一次执行都会创建一个新的 Statement 对象 。ReuseExecutor会重复利用以前创建好的 Statement 对象BatchExecutor 批量addBatch 无法自动提交事务

4、简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？

​	答 1.Mybatis的一级缓存和二级缓存都是HashMap形式存储。二级缓存的pojo类需要实现Serializable接口，因为二级缓存数据存储介质多种多样，需要序列化。

​	Mybatis的一级缓存是sqlSession级别的，二级缓存是mapper级别的。如果不同mapper的namespace相同，这两个mapper将缓存同一个区域

​    当做增删改并进行事务提交时，一级缓存和二级缓存会被刷新，调用clearCache方法可以手动删除缓存。但是二级缓存可以通过设置flushCache来设置是否刷新缓存。

5、简述Mybatis的插件运行原理，以及如何编写一个插件？

​	答：插件原理  mybatis四大核心对象每个创建出来的对象不是直接返回，而是加入interceptorChain拦截器链中。通过获取所有的拦截器调用interceptor.plugin方法，返回target包装后的对象，创建一个代理对象。实现invoke方法，即可在对象方法执行前和执行后面向切面的拦截并添加功能

​     定义一个类实现Interceptor  接口 @Intercepts  可以设置多个@Signature来设置拦截哪个类的哪个方法 通过plugin  方法将target wrap包装目标对象成代理对象，在intercept  方法中对方法进行增强。 

**二、编程题**

请完善自定义持久层框架IPersistence，在现有代码基础上添加、修改及删除功能。【需要采用getMapper方式】
