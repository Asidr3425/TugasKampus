# TugasKampus

TugasKampus adalah aplikasi Android untuk membantu mahasiswa mencatat dan mengelola tugas kuliah. Aplikasi menyediakan fungsi tambah, lihat, ubah, ubah status, dan hapus tugas. Data disimpan secara lokal menggunakan Room, sehingga tugas tetap tersedia setelah aplikasi ditutup dan dibuka kembali.

## Fitur

- **Daftar tugas** — menampilkan tugas yang tersimpan beserta informasi utama dan statusnya.
- **Tambah tugas** — mencatat mata kuliah, judul, deskripsi, deadline, prioritas, dan status awal.
- **Detail tugas** — melihat informasi lengkap dari tugas yang dipilih.
- **Edit tugas** — mengubah informasi tugas yang sudah ada.
- **Ubah status** — memperbarui progres tugas dengan tiga status: `Belum`, `Proses`, dan `Selesai`.
- **Hapus tugas dengan konfirmasi** — menampilkan dialog konfirmasi sebelum menghapus tugas.
- **Validasi input** — mata kuliah dan judul wajib diisi.
- **Penyimpanan lokal** — menggunakan Room/SQLite agar perubahan data tetap tersimpan setelah aplikasi dibuka kembali.
- **Dapat digunakan secara offline** — fitur inti tidak membutuhkan koneksi internet setelah aplikasi dan dependensinya tersedia.

Prioritas tugas menggunakan tiga pilihan: `Rendah`, `Sedang`, dan `Tinggi`. Pada instalasi awal, aplikasi menyediakan tiga tugas contoh.

## Teknologi

- **Kotlin** sebagai bahasa pemrograman.
- **Jetpack Compose** untuk antarmuka pengguna.
- **Navigation Compose** untuk navigasi antarlayar.
- **ViewModel** dan **StateFlow** untuk mengelola serta mengamati state UI.
- **Room** sebagai lapisan penyimpanan data lokal di atas SQLite.
- **Repository dan DAO** untuk memisahkan UI dari akses database.
- **KSP** untuk pemrosesan anotasi Room.

Alur arsitektur data:

```text
Compose UI → ViewModel → Repository → DAO → Room Database
```

UI tidak mengakses DAO atau database secara langsung. Perubahan data dari database diamati oleh ViewModel dan ditampilkan kembali pada UI.

## Persyaratan

- Android Studio yang mendukung konfigurasi Gradle proyek.
- Android SDK yang sesuai dengan konfigurasi proyek.
- Emulator Android atau perangkat Android fisik.
- Koneksi internet saat pertama kali melakukan Gradle Sync jika Gradle masih perlu mengunduh dependensi.

Aplikasi dapat menjalankan fitur inti tanpa koneksi internet setelah dependensi tersedia dan aplikasi terpasang.

## Cara Menjalankan

### Melalui Android Studio

1. Buka Android Studio.
2. Pilih **Open**, lalu pilih folder root proyek `TugasKampus`—folder yang berisi `settings.gradle.kts`.
3. Tunggu proses **Gradle Sync** selesai. Jika gagal, periksa pesan error pada panel Build atau Sync.
4. Jalankan emulator Android atau hubungkan perangkat Android dengan USB debugging aktif.
5. Pilih perangkat pada daftar target di toolbar Android Studio.
6. Pilih konfigurasi `app`, lalu klik **Run**.
7. Setelah aplikasi terbuka, uji penambahan, pengubahan, perubahan status, dan penghapusan tugas.

### Melalui terminal di Windows (opsional)

Jalankan perintah berikut dari folder root proyek:

```powershell
.\gradlew.bat assembleDebug
```

Jika build berhasil, APK debug biasanya tersedia di:

```text
app\build\outputs\apk\debug\app-debug.apk
```

Pasang APK tersebut pada perangkat atau emulator Android untuk menjalankan aplikasi.

## Cara Menggunakan

1. Buka aplikasi untuk melihat daftar tugas.
2. Tekan tombol tambah untuk membuat tugas. Isi mata kuliah dan judul, lalu lengkapi informasi lain sesuai kebutuhan.
3. Pilih tugas pada daftar untuk membuka detailnya.
4. Dari layar detail, ubah status atau buka layar edit untuk memperbarui data.
5. Untuk menghapus tugas, pilih aksi hapus lalu konfirmasikan pada dialog yang muncul.
6. Tutup dan buka kembali aplikasi untuk memastikan data tetap tersimpan.

## Penyimpanan Data

Data tugas disimpan di database lokal Room. Operasi tambah, ubah, ubah status, dan hapus diterapkan ke database. Aplikasi tidak memerlukan akun pengguna atau server untuk menjalankan fitur inti.

Tiga tugas contoh disediakan saat database pertama kali dibuat. Data contoh tidak dimasukkan kembali setiap kali aplikasi dibuka.

## Struktur Kode Utama

```text
com.example.tugaskampus
├── data/
│   ├── local/          # Entity, DAO, dan Room Database
│   └── repository/     # Repository untuk akses data
├── model/              # Model Task dan data contoh
├── navigation/         # Graph navigasi aplikasi
└── ui/
    ├── components/     # Komponen UI bersama
    ├── screens/        # Layar daftar, tambah, detail, dan edit
    ├── state/          # State UI
    └── viewmodel/      # Pengelolaan state dan operasi tugas
```

## Status Proyek

Fitur utama telah diuji pada lingkungan pengembangan: build berhasil, tugas contoh tampil saat instalasi awal, data dan perubahan tetap tersimpan setelah aplikasi dibuka kembali, perubahan status konsisten pada daftar dan detail, penghapusan tidak memunculkan kembali tugas, serta operasi CRUD tetap berjalan dalam mode pesawat.# TugasKampus
