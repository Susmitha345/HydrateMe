package com.uk.ac.tees.mad.hydrateme.presentation.add_screen

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uk.ac.tees.mad.hydrateme.ui.theme.HydrateMeTheme
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreenRoot(
    viewModel: AddScreenViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddScreen(
        state = state,
        onAction = viewModel::onAction
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    state: AddScreenState,
    onAction: (AddScreenAction) -> Unit,
) {
    val context = LocalContext.current
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            onAction(AddScreenAction.OnTimeChange(java.time.LocalTime.of(hour, minute)))
        },
        state.time.hour,
        state.time.minute,
        true
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Log Water Intake") })
        },
        containerColor = Color(0xFFF0F4F8)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Record Your Hydration",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Amount
                    Text(text = "Amount", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AmountStepper(
                            value = state.amount,
                            onValueChange = { onAction(AddScreenAction.OnAmountChange(it)) },
                            onDecrement = { onAction(AddScreenAction.OnDecrementAmount) },
                            onIncrement = { onAction(AddScreenAction.OnIncrementAmount) },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        UnitSelector(
                            selectedUnit = state.unit,
                            onUnitSelected = { onAction(AddScreenAction.OnUnitChange(it)) }
                        )
                    }

                    // Note
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Note (Optional)", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = state.note,
                        onValueChange = { onAction(AddScreenAction.OnNoteChange(it)) },
                        placeholder = { Text("e.g. Post-workout drink, Morning water") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Time
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Time", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = state.time.format(timeFormatter),
                        onValueChange = { /* No-op */ },
                        readOnly = true,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { timePickerDialog.show() }
                    )

                    // Buttons
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { onAction(AddScreenAction.OnSaveClick) },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF15A4B8),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Save Intake", modifier = Modifier.padding(vertical = 8.dp))
                        }
                        OutlinedButton(
                            onClick = { onAction(AddScreenAction.OnCancelClick) },
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Saved offline and synced to cloud when online",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun AmountStepper(
    value: String,
    onValueChange: (String) -> Unit,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onDecrement) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Decrement Amount")
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onIncrement) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Increment Amount")
        }
    }
}

@Composable
private fun UnitSelector(
    selectedUnit: WaterUnit,
    onUnitSelected: (WaterUnit) -> Unit
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color(0xFFE3E3E3))
    ) {
        WaterUnit.entries.forEach { unit ->
            val isSelected = selectedUnit == unit
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (isSelected) Color(0xFF15A4B8) else Color.Transparent)
                    .clickable { onUnitSelected(unit) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = unit.text,
                    color = if (isSelected) Color.White else Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun Preview() {
    HydrateMeTheme {
        AddScreen(
            state = AddScreenState(),
            onAction = {}
        )
    }
}
