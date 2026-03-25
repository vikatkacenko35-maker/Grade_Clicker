package com.tkachenko.grade_clicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tkachenko.grade_clicker.data.Datasource
import com.tkachenko.grade_clicker.model.Grade
import com.tkachenko.grade_clicker.ui.theme.GradeClickerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GradeClickerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ){
                    GradeClickerApp(grades = Datasource.gradeList)
                }

            }

        }
    }
}

@Composable
fun GradeClickerApp(grades: List<Grade>){
    var points by remember { mutableStateOf(0) }
    var clicks by remember { mutableStateOf(0) }
    val currentGrade = determinegradeToShow(grades, points)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
        Image(
            painter = painterResource(currentGrade.imageid),
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(R.dimen.image_size))
                .clickable{
                    points+=currentGrade.pointsPerClick
                    clicks++
                },
            contentScale = ContentScale.Fit
        )
        TransactionInfo(
            points = points,
            clicks = clicks
        )
    }
}

@Composable
fun TransactionInfo(points: Int,
                    clicks: Int,
                    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.points_earned),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = points.toString(),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.total_clicks),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = clicks.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}

fun determinegradeToShow(
    grades: List<Grade>,
    points: Int
) : Grade{
    var gradeToShow = grades.first()
    for (grade in grades){
        if (points >= grade.threshold){
            gradeToShow = grade
        }else{
            break
        }

    }
    return gradeToShow
}
@Preview
@Composable
fun GradeClickerPreview(){
    GradeClickerTheme {
        GradeClickerApp(grades = Datasource.gradeList)
    }
}

