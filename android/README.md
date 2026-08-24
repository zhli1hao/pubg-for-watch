# PUBG for Watch · v0.0.1 Beta
轻量版 PUBG 大逃杀（原生 Android，Kotlin + OpenGL ES 2.0），适配手表/手机方形小屏，400MB 内存可跑，不下 Unity，不靠 WebView。
## 工程结构
- android/ ：原生 Android 工程（Gradle/AGP 8.1.0 + Gradle 8.2），从 android/ 子目录构建。
- .github/workflows/ ：三个构建 workflow（单包名多 ABI / arm32 / arm64）。
## 快速开始（GitHub Actions 自动构建）
1. 将 android/ 目录上传到仓库根（旧文件可保留，不删除）。
2. 推送后点 Actions → 工作流自动/手动触发 → 3-5 分钟产出 APK（Artifacts 下载）。
3. 安装 APK → 打开 → STARTING... → 登录/大厅 → 开始游戏。
## 功能
登录（账号/设备/游客/开发者）· 30天游客注销 · 海岛风大厅 · 地图/模式 · 自建房 · 角色/皮肤 · 武器(5+MK14+榴弹炮+配件) · 真3D大逃杀 · 局域网联机(1v1/2v2/2v1/3v3+bot) · 实时同步 · 断线重连 · 好友(本地+Firebase云端) · 文字+LAN语音聊天 · 自适应图标(PUBG WATCH)。
## 图标替换
将做好的"PUBG WATCH"图标（三级头角色）各密度 PNG 同名覆盖到 android/app/src/main/res/mipmap-*/ic_launcher.png 即可。
## 版本
0.0.1-Beta
