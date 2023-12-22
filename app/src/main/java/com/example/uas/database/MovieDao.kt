package com.example.uas.database

import androidx.room.*
@Dao
interface MovieDao {
    // fungsi movieRoomDao sebagai aks, setiap akses yang terjadi di database, ini harus melalui perantara interface ini
    // jadi setiap crud nya di harus melalui ini dulu

    // penambahan data movie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    // get data list movie, melakukan select list movie melalui movie_table dari data class Note
    @Query("SELECT * FROM MovieDB")
    fun getAllMovies(): List<Movie>

    // Hapus semua data dari tabel movie_table
    @Query("DELETE FROM moviedb")
    fun deleteAll()
}