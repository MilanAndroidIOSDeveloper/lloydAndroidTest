package com.cube.cubeacademy.lib.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidtest.bymilanchothani.models.Nominee
import com.androidtest.bymilanchothani.models.NomineeName


@Dao
 abstract class NomineeDao {

   @Query("SELECT * FROM nominees")
   abstract suspend fun getAllNominees(): List<Nominee>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   abstract suspend fun insertAll(nominees: List<Nominee>)

   @Query("DELETE FROM nominees")
   abstract suspend fun deleteAllNominees()

   @Query("SELECT firstName, lastName FROM nominees WHERE nomineeId = :id")
   abstract suspend fun getNomineeNameById(id: String): NomineeName

}