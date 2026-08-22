const { app, BrowserWindow } = require('electron')
const path = require('path')

function createWindow () {
  const win = new BrowserWindow({
    width: 800,
    height: 600,
    resizable: true,
    webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      nodeIntegration: false,
      contextIsolation: true
    }
  })

  // Load the root index.html from the repo
  win.loadFile(path.join(__dirname, 'index.html'))

  // Optional: Show a lightweight STARTING... overlay on first load
  win.webContents.on('did-finish-load', () => {
    win.webContents.send('app-started')
  })
}

app.whenReady().then(createWindow)

app.on('window-all-closed', () => {
  // On macOS apps commonly stay open until the user quits explicitly
  if (process.platform !== 'darwin') app.quit()
})

app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) createWindow()
})
