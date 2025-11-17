package com.uk.ac.tees.mad.hydrateme.presentation.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uk.ac.tees.mad.hydrateme.R
import com.uk.ac.tees.mad.hydrateme.ui.theme.HydrateMeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun HomeRoot(
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Hello 👋 ${state.userName}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    IconButton(onClick = { /* Notifications click */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onAddClick = { onAction(HomeAction.AddWater(250)) }
            )
        }
        ,
        floatingActionButton = {
            // Optional: keep a FAB too; remove if you only want the bottom bar Add
            FloatingActionButton(
                onClick = { onAction(HomeAction.AddWater(250)) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Water",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            WaterIntakeCard(state)
            Spacer(modifier = Modifier.height(24.dp))
            QuoteCard()
            Spacer(modifier = Modifier.height(24.dp))
            TodayLogsCard(state)
            Spacer(modifier = Modifier.weight(1f))
            QuickAddButtons(onAction)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// -----------------------------------------------------------------------------------------------
// UI Sections
// -----------------------------------------------------------------------------------------------

@Composable
fun WaterIntakeCard(state: HomeState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_water_bottle), // ensure this exists
                contentDescription = "Water bottle icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "${state.waterConsumed} / ${state.dailyGoal} ml",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            )
            Text(
                text = "Daily Goal",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun QuoteCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "\"The body is mainly water. The brain is 75% water, blood is 83% water, and muscles are 75% water. Drink up!\"",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "- HydrateMe Team",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun TodayLogsCard(state: HomeState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Today's Logs",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(state.todayLogs) { log ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = log.time,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "${log.amount} ml",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}

@Composable
fun QuickAddButtons(onAction: (HomeAction) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onAction(HomeAction.AddWater(200)) },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add 200ml", modifier = Modifier.size(20.dp))
                Text(text = "200 ml")
            }
        }

        Button(
            onClick = { onAction(HomeAction.AddWater(500)) },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add 500ml", modifier = Modifier.size(20.dp))
                Text(text = "500 ml")
            }
        }
    }
}

// -----------------------------------------------------------------------------------------------
// Bottom Navigation
// -----------------------------------------------------------------------------------------------

data class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun BottomNavigationBar(
    onAddClick: () -> Unit = {}
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavItem("Add", Icons.Default.Add, Icons.Default.Add), // now renders like others
        BottomNavItem("Stats", Icons.Filled.ShowChart, Icons.Outlined.ShowChart),
        BottomNavItem("Profile", Icons.Filled.Person, Icons.Outlined.Person)
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedItem == index

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selectedItem = index
                    if (item.label == "Add") onAddClick()
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}

// -----------------------------------------------------------------------------------------------
// Preview
// -----------------------------------------------------------------------------------------------

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun Preview() {
    HydrateMeTheme {
        HomeScreen(
            state = HomeState(
                waterConsumed = 1200,
                dailyGoal = 2500,
                todayLogs = listOf(
                    WaterLog("8:30 AM", 300),
                    WaterLog("10:15 AM", 250),
                    WaterLog("1:00 PM", 400),
                    WaterLog("3:45 PM", 250)
                )
            ),
            onAction = {}
        )
    }
}
