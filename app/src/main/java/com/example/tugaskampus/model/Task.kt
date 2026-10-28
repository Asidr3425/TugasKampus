package com.example.tugaskampus.model

/** Model data satu tugas. Dipakai UI; pemetaan ke tabel Room dibuat di Langkah 4. */
data class Task(
    val id: Int = 0,
    val mataKuliah: String,
    val judul: String,
    val deskripsi: String,
    val deadline: Long,      // epoch millis
    val prioritas: String,   // Rendah / Sedang / Tinggi
    val status: String       // Belum / Proses / Selesai
)

object TaskOptions {
    val statuses = listOf("Belum", "Proses", "Selesai")
    val priorities = listOf("Rendah", "Sedang", "Tinggi")
}
