package com.example.workoutapp

import java.util.ArrayList

object Constants {

    fun exerciseList(): ArrayList<ExerciseModel>{
        val list = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(1, "Jumping jacks", R.drawable.ic_jumping_jacks, false, false)
        list.add(jumpingJacks)

        val wallSit = ExerciseModel(2, "Wall sit", R.drawable.ic_wall_sit, false, false)
        list.add(wallSit)

        val pushUp = ExerciseModel(3, "Push up", R.drawable.ic_push_up, false, false)
        list.add(pushUp)

        val abdominalCrunch = ExerciseModel(4, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false)
        list.add(abdominalCrunch)

        val stepUpOnChair = ExerciseModel(5, "Step up onto chair", R.drawable.ic_step_up_onto_chair, false, false)
        list.add(stepUpOnChair)

        val squat = ExerciseModel(6, "Squat", R.drawable.ic_squat, false, false)
        list.add(squat)

        val tricepsDipOnChair = ExerciseModel(7, "Triceps dip on chair", R.drawable.ic_triceps_dip_on_chair, false, false)
        list.add(tricepsDipOnChair)

        val plank = ExerciseModel(8, "Plank", R.drawable.ic_plank, false, false)
        list.add(plank)

        val highKneesRunningInPlace = ExerciseModel(9, "High knees in place", R.drawable.ic_high_knees_running_in_place, false, false)
        list.add(highKneesRunningInPlace)

        val lunges = ExerciseModel(9, "Lunges", R.drawable.ic_lunge, false, false)
        list.add(lunges)

        val pushUpAndRotation = ExerciseModel(10, "Push up and rotation", R.drawable.ic_push_up_and_rotation, false, false)
        list.add(pushUpAndRotation)

        val sidePlank = ExerciseModel(11, "Side plank", R.drawable.ic_side_plank, false, false)
        list.add(sidePlank)

        return list
    }
}