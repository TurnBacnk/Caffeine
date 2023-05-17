# Caffeine

## 缓存介绍

缓存在日常开发有至关重要的作用，由于是存储在内存中，数据的读取速度是非常快的，能大量减少对数据库的访问，减少数据库的压力。

常见的以Redis这种NoSQL作为缓存组件，能够很好的作为的分布式缓存组件提供给多个服务间的缓存，但是Redis需要网络开销，且增加部署成本。若项目中仅仅是数据量很小的缓存，采用本地缓存更加合适。

## Caffeine介绍

[Caffeine Github]: https://github.com/ben-manes/caffeine

按Caffeine Github文档描述，Caffeine是基于Java 8 的高性能缓存库。并且在Spring 5（Spring 2.x）后，Spring官方放弃了Guava，使用了性能更为优秀的Caffeine作为默认缓存组件。

### Caffeine配置说明

| 参数              | 类型     | 描述                                 |
| ----------------- | -------- | ------------------------------------ |
| initialCapacity   | integer  | 初始化缓存空间大小                   |
| maximumSize       | long     | 缓存的最大条数                       |
| maxmumWeight      | long     | 缓存的最大权重                       |
| expireAfterAccess | duration | 最后一次写入或访问后经过固定时间过期 |
| refreshAfterWrite | duration | 最后一次写入后经过固定时间过期       |
| weakKeys          | boolean  | 打开key的弱引用                      |
| weakValues        | boolean  | 打开value的弱引用                    |
| softValues        | boolean  | 打开value的软引用                    |

### 软引用与弱引用

**软引用**：如果一个对象只具有软引用，则内存空间足够，垃圾回收器不会回收它；内存不足，则回收这些对象。

**弱引用**：弱引用的对象拥有更短暂的生命周期。GC过程中，一旦发现弱引用对象，立即回收。

```java
// 软引用
Caffeine.newBuilder().softValues().build();
// 弱引用
Caffeine.newBuilder().weakKeys().weakValues().build();
```

## SpringBoot集成Caffeine两种方式

SpringBoot有两种使用Caffeine作为缓存的方式。

- 方式一：直接引用Caffeine依赖，使用Caffeine方法实现缓存
- 方式二：引入Caffeine和Spring Cache依赖，使用Spring Cache注解方法实现缓存

### 方式一：直接引入

#### 1.引入依赖

```xml
<dependency>
     <groupId>com.github.ben-manes.caffeine</groupId>
     <artifactId>caffeine</artifactId>
</dependency>
```

#### 2.配置缓存配置类

```java
@Bean
public Cache<String, Object> caffeineCache() {
    return Caffeine.newBuilder()
            // 设置最后一次写入或访问后经过固定时间过期
            .expireAfterWrite(60, TimeUnit.SECONDS)
            // 初始化缓存空间大小
            .initialCapacity(100)
            // 缓存的最大条数
            .maximumSize(1000)
            .build();
}
```

#### 3.简易使用

```java
@Resource
private Cache<String, Object> caffeineCache;


@Override
public void addUserInfo(UserInfo userInfo) {
    log.info("create user");
    USER_INFO_MAP.put(userInfo.getId(), userInfo);
    caffeineCache.put(String.valueOf(userInfo.getId()), userInfo);
}
```

