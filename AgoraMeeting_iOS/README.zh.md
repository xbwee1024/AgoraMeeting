> *Read this article in another language: [English](README.md)*

# 本项目已经不再维护。最新请到以下地址查看：
```
iOS: https://github.com/AgoraIO-Usecase/AgoraMeeting-iOS
Android: https://github.com/AgoraIO-Usecase/AgoraMeeting-Android
Web: https://github.com/AgoraIO-Usecase/AgoraMeeting-Desktop
```


本文指导你运行 iOS 示例项目。

## 前提条件

- 准备工作：请确保你已经完成 [Agora Meeting 项目指南](https://github.com/AgoraIO-Usecase/AgoraMeeting/blob/master/README.zh.md)中的准备工作。
- 开发环境：
  - Xcode 10.0 及以上
  - Cocoapods
- iOS 真机（iPhone 或 iPad）

## 运行示例项目

参考以下步骤编译和运行示例项目：

1.将代码克隆到本地

```
git clone https://github.com/AgoraIO-Community/eConferencing-iOS
```

2.下载AgoraRte Framework

下载 [AgoraRte](https://github.com/AgoraIO-Community/eConferencing-iOS/releases/download/iOS_2.0.0/AgoraRte.framework.zip) 并解压，然后将 "AgoraRte.framework" 文件移动到 "eConferencing-iOS/AgoraRte" 文件夹下。

3.进入项目目录

```
cd eConferencing-iOS/VideoConference
```

4.安装项目依赖库

```
pod install
```

5.打开项目

```
open VideoConference.xcworkspace
```

6.配置相关参数

在 `KeyCenter.m` 文件中配置以下参数：
- 你获取到的声网 App ID。
- 你生成的 `Authorization` 字段，用于 HTTP 基本认证。

详见 Agora e-Education 项目指南中的[前提条件](https://github.com/AgoraIO-Usecase/AgoraMeeting/blob/master/README.zh.md#%E5%89%8D%E6%8F%90%E6%9D%A1%E4%BB%B6)。

```
+ (NSString *)agoraAppid {
     return <#Your Agora App Id#>;
}

+ (NSString *)customerId {
     return <#Your customerId#>;
}

+ (NSString *)customerCertificate {
     return <#Your customerCertificate#>;
}
```

7.App Groups 的支持。屏幕共享时一个单独的扩展进程，需要App Groups，如果需要屏幕共享的功能：

- 为项目配置App Groups，可以获得一个App Groups字符串；
- 修改MeetingVM.Swift 的AppGroupsString字符串为项目配置的App Groups字符串；
- 修改SampleHandler.swift`的AppGroupsString字符串为项目配置的App Groups字符串；

8.通过 `command + r` 运行项目

## 联系我们

- 如需阅读完整的文档和 API 注释，你可以访问[声网开发者中心](https://docs.agora.io/cn/)。
- 如果在集成中遇到问题，你可以到[声网开发者社区](https://dev.agora.io/cn/)提问。
- 如果有售前咨询问题，你可以拨打 400 632 6626，或加入官方Q群 12742516 提问。
- 如果需要售后技术支持，你可以在 [Agora 控制台](https://dashboard.agora.io/)提交工单。
- 如果发现了示例代码的 bug，欢迎提交 [issue](https://github.com/AgoraIO-Usecase/AgoraMeeting/issues)。

## 代码许可

The MIT License (MIT).
