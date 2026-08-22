# 构建与打包说明 — PUBG FOR WATCH v0.0.1

本文件说明如何把当前仓库的 Web 版本（HTML/JS/CSS）打包为：
- Android APK（使用 Capacitor/Cordova 路线）
- 桌面应用 EXE（使用 Electron + electron-builder）

目标与限制
- APK 尽量控制在 20MB（源码/资源极限优化）
- 安装后占用不超过 50MB（含约 10MB 预留用于缓存/匹配）
- 禁止依赖 OBB 或运行时大文件下载（尽量把必要资源内置并压缩）
- iOS 支持将在后续版本添加

目录
1) 前提准备
2) Android (Capacitor) 打包流程（推荐）
3) 桌面（Electron）打包流程（Windows/ Linux / macOS）
4) 体积优化建议
5) 签名、发布与注意事项

1) 前提准备
- Node.js (16+ 推荐)
- npm / yarn
- Android Studio（仅用于 Android 最终签包）
- Java JDK（Android 构建需要）
- 对于桌面：依赖 native 打包工具（在 Windows 上建议使用 Windows 打包机器/WSL2、在 macOS 上需要 macOS 机器来制作 .dmg/签名）

2) Android (Capacitor) 打包流程（推荐：轻量、接近原生 APK 大小）
说明：Capacitor 将把 web 构建产物拷贝到 Android 原生项目，并使用系统 WebView 渲染本地文件。若使用 Cordova，流程类似。

步骤：
1. 构建 Web 前端资源（确保把生产资源放到 dist/ 或 build/）
   - 如项目有构建脚本：
     npm install
     npm run build
   - 产出目录示例： ./dist

2. 安装并初始化 Capacitor（只需在项目根运行一次）
   npm install @capacitor/cli @capacitor/core --save
   npx cap init "PUBG-FOR-WATCH" com.example.pubgforwatch --web-dir=dist

3. 添加 Android 平台
   npx cap add android

4. 把前端资源拷贝到 Android 项目
   npx cap copy android

5. 打开 Android Studio 做最终构建与签名
   npx cap open android
   // Android Studio 中：
   // - 配置 release 签名（Gradle signingConfigs）
   // - 在 module:app 的 build.gradle 中启用压缩与资源瘦身：
   //   buildTypes {
   //     release {
   //       minifyEnabled true
   //       shrinkResources true
   //       proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
   //     }
   //   }
   // - Build > Generate Signed Bundle / APK > 选择 APK 或 AAB 输出

6. 优化 APK 大小（见第 4 节）

提示：
- Capacitor 本身较轻量，APK 的大小主要取决于内置资源（图片/音频/字体）以及 Android 框架代码。通过使用极简 native 依赖和压缩资源，有望把 APK 控制到目标范围。
- 若使用 WebView 渲染大量 JS 库，尽量 tree-shake、拆分、压缩。

3) 桌面（Electron）打包流程 — 生成 EXE (Windows)、AppImage/Deb (Linux)、dmg (macOS)
说明：Electron 会打包 Chromium + Node.js，通常二进制较大（几十 MB ~ 几百 MB 不等）。如果你需要尽量小体积的桌面客户端可以考虑原生容器（例如使用 Tauri 或 neutralinojs 更轻量），但功能限制需评估。

推荐：
- 若功能（Web 能力、Node 本地调用）要求较高，使用 Electron；否则为体积最小化推荐 Tauri（Rust + WebView），但构建复杂度更高。

Electron + electron-builder 简要流程：
1. 在项目中安装依赖：
   npm install --save-dev electron electron-builder

2. 在 package.json 中添加最小配置示例：
{
  "name": "pubg-for-watch",
  "version": "0.0.1",
  "main": "electron-main.js",
  "scripts": {
    "build:web": "npm run build", // 你的 web 打包脚本
    "electron:dev": "electron .",
    "dist": "electron-builder"
  },
  "build": {
    "appId": "com.example.pubgforwatch",
    "files": ["dist/**/*", "electron-main.js"],
    "win": { "target": ["nsis"] },
    "mac": { "target": ["dmg"] },
    "linux": { "target": ["AppImage"] }
  }
}

3. 创建 electron-main.js（主进程），示例：
const { app, BrowserWindow } = require('electron')
const path = require('path')
function createWindow () {
  const win = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      preload: path.join(__dirname, 'preload.js')
    }
  })
  win.loadFile(path.join(__dirname, 'dist', 'index.html'))
}
app.whenReady().then(createWindow)

4. 构建流程：
   npm run build:web
   npm run dist

注意（体积）：
- Electron 打包通常远大于移动 APK 要求；如果你对桌面体积没有严格限制可以使用 Electron；否则考虑 Tauri（体积通常更小）或将桌面端的资源与运行时代码分离。

4) 体积优化建议（控制 APK 到 20MB / 安装 50MB 的关键）
- 图片：使用 SVG 或非常低分辨率的 WebP，并启用高度压缩。
- 声音：不在 APK 中内置大型背景音乐；让玩家按需导入。短效音效可采用 8kHz/mono/低���特率 OGG。
- 字体：只包含必要字符集，使用系统字体优先，避免内置大型字体文件。
- JS/CSS：启用构建工具的压缩与 tree-shaking（Webpack/Rollup/Vite），开启 gzip/brotli 发布预压缩（但需本地加载时解压不得增加安装体积）。
- 删除开发依赖与调试信息，打包时只拷贝生产文件。
- 资源合并：把所有小图片合并为雪碧图（sprite）减少文件头开销。
- 若必须运行时下载资源，考虑把非关键资源延后加载（但当前需求禁止运行时下载）。

5) 签名、发布与法律注意事项
- Android：使用自己的签名密钥签名 release APK/AAB。保存 keystore 以便后续更新。
- Windows/macOS：为安装程序签名（建议），尤其在 macOS 上没有签名会影响用户体验。Windows 可使用 EV 证书做 SmartScreen 降低误报。
- 版权/商标：你请求使用“PUBG 旧版图标/ PUBG Lite 风格元素”，这可能涉及商标/版权问题。请在发布前确保有权使用或更换为自有/授权图形。

附录：常见命令示例（简版）
# 构建 web
npm ci
npm run build

# Android (Capacitor)
npx cap copy android
npx cap open android
# 在 Android Studio 中签包（Generate Signed Bundle / APK）

# Electron 打包
npm run build
npm run dist

---
如果你希望，我可以：
- 把示例的 electron-main.js、pack 脚本与 BUILDING.md 一并提交到仓库（我可以为你起草这些文件）。
- 基于你仓库的结构把打包脚本定制化（需要我先读取 repo 文件结构）。

请回复：是否把示例文件提交到仓库？