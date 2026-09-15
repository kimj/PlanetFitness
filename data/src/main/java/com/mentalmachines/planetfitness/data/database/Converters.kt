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

    @TypeConverter
    fun fromTrainer(value: com.mentalmachines.planetfitness.data.Trainer?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toTrainer(value: String?): com.mentalmachines.planetfitness.data.Trainer? {
        return Gson().fromJson(value, com.mentalmachines.planetfitness.data.Trainer::class.java)
    }

    @TypeConverter
    fun fromCta(value: com.mentalmachines.planetfitness.data.Cta?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toCta(value: String?): com.mentalmachines.planetfitness.data.Cta? {
        return Gson().fromJson(value, com.mentalmachines.planetfitness.data.Cta::class.java)
    }
}
