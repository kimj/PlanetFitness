package com.mentalmachines.planetfitness.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ProgramDaoTest {
    private lateinit var dao: ProgramDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.programDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetPrograms() = runTest {
        val program = ProgramEntity(
            programId = "p1",
            title = "Program 1",
            summary = null,
            level = null,
            focus = null,
            equipment = null,
            totalWorkouts = null,
            workouts = null,
            trainer = null,
            cta = null
        )
        dao.insertPrograms(program)

        dao.getAllPrograms().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("p1", list[0].programId)
        }
    }

    @Test
    fun getProgramById() = runTest {
        val program = ProgramEntity(
            programId = "p1",
            title = "Program 1",
            summary = null,
            level = null,
            focus = null,
            equipment = null,
            totalWorkouts = null,
            workouts = null,
            trainer = null,
            cta = null
        )
        dao.insertPrograms(program)

        dao.getProgramById("p1").test {
            val result = awaitItem()
            assertEquals("p1", result?.programId)
        }
    }
}
