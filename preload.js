const { contextBridge, ipcRenderer } = require('electron')

// 安全地暴露一个 API 给渲染进程（index.html）
contextBridge.exposeInMainWorld('electronAPI', {
  onAppStarted: (callback) => ipcRenderer.on('app-started', callback)
})
