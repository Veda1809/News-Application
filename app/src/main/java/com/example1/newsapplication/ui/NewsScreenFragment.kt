package com.example1.newsapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import coil.compose.rememberAsyncImagePainter
import com.example1.newsapplication.R
import com.example1.newsapplication.data.domain.ArticlesData
import com.example1.newsapplication.data.Category
import com.example1.newsapplication.utils.components.Amber800
import com.example1.newsapplication.utils.components.Grey100
import com.example1.newsapplication.utils.components.Poppins
import com.example1.newsapplication.utils.components.Purple700
import com.example1.newsapplication.utils.components.blueGrey600
import com.example1.newsapplication.utils.components.LottieAnimationView
import com.example1.newsapplication.utils.components.ObserveAsEvents
import com.example1.newsapplication.utils.components.noRippleClickable
import com.example1.newsapplication.utils.components.greenA700
import com.example1.newsapplication.utils.components.lightBlue500
import com.example1.newsapplication.utils.components.pink300
import com.example1.newsapplication.utils.components.red500
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


class NewsScreenFragment : Fragment() {

    private val viewModel by viewModel<NewsScreenViewModule>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NewsScreen(
                    modifier = Modifier,
                    categoryItems = listOf(
                        Category(
                            color = lightBlue500,
                            category = "BUSINESS",
                            image = R.drawable.ic_business
                        ),
                        Category(
                            color = red500,
                            category = "SPORTS",
                            image = R.drawable.ic_sports
                        ),
                        Category(
                            color = Amber800,
                            category = "ENTERTAINMENT",
                            image = R.drawable.ic_entertainment
                        ),
                        Category(
                            color = greenA700,
                            category = "HEALTH",
                            image = R.drawable.ic_health
                        ),
                        Category(
                            color = pink300,
                            category = "SCIENCE",
                            image = R.drawable.ic_science
                        ),
                        Category(
                            color = Purple700,
                            category = "TECHNOLOGY",
                            image = R.drawable.ic_technology
                        )
                    ),
                    state = viewModel.state,
                    onNewsItemClick = {
                        viewModel.onAction(NewsAction.onNewsItemClick(it))
                    },
                    onCategoryItemCLick = {
                        viewModel.onAction(NewsAction.onCategoryItemClick(it))
                    },
                    onSearchedClick = {
                        viewModel.onAction(NewsAction.onSearchedClick(it))
                    },
                    onSearchCleared = {
                        viewModel.onAction(NewsAction.onSearchCleared)
                    }
                )
            }
        }
    }

    @Composable
    fun NewsScreen(
        modifier: Modifier = Modifier,
        categoryItems: List<Category>,
        state: TotalNewsState,
        onNewsItemClick: (String) -> Unit,
        onCategoryItemCLick: (String) -> Unit,
        onSearchedClick: (String) -> Unit,
        onSearchCleared: () -> Unit
    ) {
        var isSearchVisible by remember { mutableStateOf(false) }
        var searchQuery by remember { mutableStateOf("") }


        ObserveAsEvents(viewModel.event) { event ->
            when (event) {
                is NewsEvent.OpenUrl -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.url))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }

                else -> {}
            }
        }


        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Box(
                modifier = modifier
                    .shadow(elevation = 8.dp)
                    .background(Color.White)
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(0.dp),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .background(Color.White)
                ) {
                    if(!isSearchVisible){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .background(
                                        shape = RoundedCornerShape(20.dp),
                                        color = Color.LightGray
                                    )
                                    .size(25.dp)
                            ) {
                                Image(
                                    modifier = Modifier.size(30.dp),
                                    painter = painterResource(id = R.drawable.ic_usa),
                                    contentDescription = "Indian flag"
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = ".G O O D.N E W S.",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.W700,
                                    color = Color.Black
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(20.dp)
                                    .background(
                                        shape = RoundedCornerShape(1.dp),
                                        color = Color.White
                                    )
                                    .size(20.dp)
                                    .noRippleClickable {
                                        isSearchVisible = true
                                    }
                            ) {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.ic_search),
                                    contentDescription = "Indian flag"
                                )
                            }
                        }    
                    } else {
                        AnimatedVisibility(
                            visible = isSearchVisible,
                            enter = slideInVertically(initialOffsetY = { -it }),
                            exit = slideOutVertically(targetOffsetY = { -it })
                        ) {
                            SearchBar(
                                searchQuery = searchQuery,
                                onSearchQueryChange = { searchQuery = it },
                                onSearchClose = { isSearchVisible = false },
                                onSearchedClick = { text ->
                                    onSearchedClick.invoke(text)
                                },
                                onSearchCleared = {
                                    onSearchCleared.invoke()
                                }
                            )
                        }
                    }
                    
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            LazyRow(modifier = Modifier.padding(horizontal = 20.dp))
            {
                items(categoryItems) { categoryItem ->
                    CategoryItem(
                        color = categoryItem.color,
                        image = categoryItem.image,
                        category = categoryItem.category,
                        onCategoryItemCLick = { category ->
                            onCategoryItemCLick.invoke(category)
                        }
                    )
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp), text = "News",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = Poppins
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.padding(bottom = 40.dp),
                contentAlignment = Alignment.Center
            )
            {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.isLoading) {
                        LottieAnimationView(resId = R.raw.loader, modifier = Modifier.size(80.dp))
                    } else {
                        if (state.articles?.isNotEmpty() == true) {
                            LazyColumn(modifier = Modifier) {
                                items(state.articles) { article ->
                                    if (article.urlToImage.isNotEmpty()){
                                        NewsItem(
                                            articlesData = article,
                                            onNewsItemClick = { url ->
                                                onNewsItemClick.invoke(url)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SearchBar(
        searchQuery: String,
        onSearchQueryChange: (String) -> Unit,
        onSearchClose: () -> Unit,
        onSearchedClick: (String) -> Unit,
        onSearchCleared: () -> Unit
    ) {
        val focusRequester = remember { FocusRequester() }
        var isFocused by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        LaunchedEffect(Unit) {
            focusManager.clearFocus()
        }

        LaunchedEffect(searchQuery.ifEmpty { "EMPTY" }) {
            if (searchQuery.isEmpty()) {
                onSearchCleared.invoke()
            }
        }
        Box(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
                .height(50.dp),
            contentAlignment = Alignment.CenterStart
        ){
            Row(
                modifier = Modifier
                    .background(color = Grey100, shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        color = Color.Black // Use your SearchTextColor here
                    ),
                    cursorBrush = SolidColor(Color.Black), // Use your SearchTextColor here
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Search in everything",
                                color = Color.Gray,
                                modifier = Modifier.align(Alignment.CenterVertically),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        }
                )

                Spacer(modifier = Modifier.weight(1f))

                AnimatedVisibility(visible = searchQuery.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .noRippleClickable {
                                onSearchedClick.invoke(searchQuery)
                                keyboardController?.hide()
                            }
                            .background(
                                color = blueGrey600,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 20.dp, vertical = 5.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "search",
                            style = TextStyle(
                                color = Color.White,
                                fontFamily = Poppins,
                                fontSize = 14.sp,
                                fontWeight = W400
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                IconButton(onClick = onSearchClose) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close search",
                        tint = Color.Black
                    )
                }
            }
        }
    }





    @Composable
    fun CategoryItem(
        modifier: Modifier = Modifier,
        color: Color,
        image: Int,
        category: String,
        onCategoryItemCLick: (String) -> Unit
    ) {
        Box(
            modifier = modifier
                .padding(end = 10.dp)
                .background(color = color, shape = RoundedCornerShape(10.dp))
                .size(120.dp, 80.dp)
                .noRippleClickable {
                    onCategoryItemCLick.invoke(category.lowercase())
                }
        )
        {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            )
            {
                Box(
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(40.dp),
                            color = Color.White
                        )
                        .size(30.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(5.dp),
                        painter = painterResource(id = image),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color)
                    )

                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = category,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = Poppins,
                        color = Color.White
                    )
                )
            }
        }
    }

    @Composable
    fun NewsItem(
        modifier: Modifier = Modifier,
        articlesData: ArticlesData,
        onNewsItemClick: (String) -> Unit
    ) {
        Card(
            modifier = modifier
                .background(Color.White)
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .noRippleClickable {
                    onNewsItemClick.invoke(articlesData.url)
                },
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (articlesData.urlToImage.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .background(
                                    shape = RoundedCornerShape(1.dp),
                                    color = Color.White
                                )
                                .size(80.dp, 60.dp)

                        ) {
                            Image(
                                modifier = Modifier.size(80.dp, 60.dp),
                                painter = rememberAsyncImagePainter(model = articlesData.urlToImage),
                                contentDescription = ""
                            )
                        }
                    }
                    Text(
                        text = articlesData.title,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.W400,
                            color = Color.Black
                        )
                    )
                }

                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier,
                        text = articlesData.url.split("/")[2].removePrefix("www."),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.W400,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = convertTimestamp(articlesData.publishedAt),
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.W300,
                            color = Color.Gray
                        )
                    )
                }
            }
        }
    }

    private fun convertTimestamp(timestamp: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return try {
            val date = inputFormat.parse(timestamp)
            outputFormat.format(date ?: return "")
        } catch (e: Exception) {
            ""
        }
    }


}