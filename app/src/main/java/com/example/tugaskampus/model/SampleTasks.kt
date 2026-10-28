package com.example.tugaskampus.model

private const val DAY = 24L * 60 * 60 * 1000

/** Data contoh SEMENTARA untuk Preview dan demo Langkah 2. Dihapus setelah Room dipasang. */
fun sampleTasks(): List<Task> {
    val now = System.currentTimeMillis()
    return listOf(
        Task(1, "Pemrograman Aplikasi Bergerak", "Praktikum 1 Offline App",
            "Bangun aplikasi manajemen tugas dengan Compose dan Room.", now + 3 * DAY, "Tinggi", "Proses"),
        Task(2, "Basis Data", "Normalisasi tabel",
            "Normalisasi sampai 3NF dan gambar ERD.", now + 7 * DAY, "Sedang", "Belum"),
        Task(3, "Jaringan Komputer", "Laporan subnetting",
            "", now - 1 * DAY, "Rendah", "Selesai")
    )
}
