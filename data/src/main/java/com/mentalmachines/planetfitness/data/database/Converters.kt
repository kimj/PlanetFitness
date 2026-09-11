package com.mentalmachines.planetfitness.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromWorkoutsList(value: List<com.mentalmachines.planetfitness.data.Workouts>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toWorkoutsList(value: String?): List<com.mentalmachines.planetfitness.data.Workouts>? {
        val listType = object : TypeToken<List<com.mentalmachines.planetfitness.data.Workouts>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
