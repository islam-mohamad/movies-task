package com.islam.tasks.yassir.navigation

import android.content.Context
import com.islam.tasks.details.R
import com.islam.tasks.movies.presentation.navigation.MoviesNavigation
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class MoviesNavigationImpl @Inject constructor(@ActivityContext private val context: Context) :
    MoviesNavigation {
    override fun getDetailsDeepLink(id: Int): String {
        return context.getString(R.string.deeplink_movie_details, id)
    }
}