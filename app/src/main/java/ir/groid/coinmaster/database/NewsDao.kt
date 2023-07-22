package ir.groid.coinmaster.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.util.Constans.TableNews


@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(list: List<RNewsData>)

    @Query("SELECT * FROM $TableNews")
    fun getAllNews(): LiveData<List<RNewsData>>
}