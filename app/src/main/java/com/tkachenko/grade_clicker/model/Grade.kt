package com.tkachenko.grade_clicker.model

import androidx.annotation.DrawableRes

data class Grade(
    @DrawableRes val imageid: Int,
    val pointsPerClick: Int,
    val threshold: Int
)