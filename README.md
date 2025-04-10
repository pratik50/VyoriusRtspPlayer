# 📹 Vyorius RTSP Player

An Android application to **stream, record**, and **minimize live RTSP video feeds** with smooth Picture-in-Picture (PiP) support.

---

## 🚀 Features

- 🔴 **Live RTSP Streaming** with low-latency playback using **VLC**.
- 📼 **One-tap Recording** with animated camera-style toggle.
- 💾 **Saved Recordings** accessible in internal storage.
- 🪟 **Picture-in-Picture Mode** with full exit and resume handling.
- ⚡ **Optimized for Performance** on physical devices (no frame drops or playback delays).
- 🧱 **Modular Codebase** – clean separation for VLC utils, recording logic, and UI.

---

## 📱 Screenshots

<div align="center">
  <img src="https://github.com/user-attachments/assets/a7b73058-344a-44a7-b870-4b2fd63e4f6a" width="200" style="margin-right: 10px;"/>
  <img src="https://github.com/user-attachments/assets/81b6f6a5-8a8d-4de1-ab47-b038781577df" width="200" style="margin-right: 10px;"/>
  <img src="https://github.com/user-attachments/assets/f42173c7-fe72-482f-bd45-09ca7b3ba066" width="200" style="margin-right: 10px;"/>
  <img src="https://github.com/user-attachments/assets/05a8671a-c886-49a7-b413-547c5c6db582" width="200"/>
</div>

---

## 🛠 Built With

- **Kotlin**
- **VLC Android SDK**
- **Android Jetpack (ViewBinding, Lifecycle)**
- **Android SDK 21+**

---

## 📍 RTSP Testing Note

To test streaming:
1. Connect **both devices** to same Wi-Fi.
2. Use RTSP camera/server to stream video from source device.
3. Enter the stream URL like: rtsp://127.0.0.1:5550 (depends on individual ip)

---

## 📸 Recording Path

All recordings are saved to the following internal app-specific directory:
- 🔒 **Note:** This is internal app storage – recordings won't appear in the system **Gallery** by default.
- 📂 Use a **File Manager** app that allows access to `Android/data`, or use **ADB** to retrieve recordings.
