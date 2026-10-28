package com.example.tugaskampus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

private const val DAY_IN_MILLIS = 24L * 60L * 60L * 1000L

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tugas_kampus.db"
                )
                    .addCallback(InitialDataCallback())
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

/** Mengisi tiga tugas contoh hanya saat database pertama kali dibuat. */
private class InitialDataCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        val now = System.currentTimeMillis()

        insertSample(
            db,
            mataKuliah = "Pemrograman Aplikasi Bergerak",
            judul = "Praktikum 1 Offline App",
            deskripsi = "Bangun aplikasi manajemen tugas dengan Compose dan Room.",
            deadline = now + 3 * DAY_IN_MILLIS,
            prioritas = "Tinggi",
            status = "Proses"
        )
        insertSample(
            db,
            mataKuliah = "Basis Data",
            judul = "Normalisasi tabel",
            deskripsi = "Normalisasi sampai 3NF dan gambar ERD.",
            deadline = now + 7 * DAY_IN_MILLIS,
            prioritas = "Sedang",
            status = "Belum"
        )
        insertSample(
            db,
            mataKuliah = "Jaringan Komputer",
            judul = "Laporan subnetting",
            deskripsi = "",
            deadline = now - DAY_IN_MILLIS,
            prioritas = "Rendah",
            status = "Selesai"
        )
    }

    private fun insertSample(
        db: SupportSQLiteDatabase,
        mataKuliah: String,
        judul: String,
        deskripsi: String,
        deadline: Long,
        prioritas: String,
        status: String
    ) {
        db.execSQL(
            """INSERT INTO tasks (mata_kuliah, judul, deskripsi, deadline, prioritas, status)
               VALUES (?, ?, ?, ?, ?, ?)""".trimIndent(),
            arrayOf(mataKuliah, judul, deskripsi, deadline, prioritas, status)
        )
    }
}
