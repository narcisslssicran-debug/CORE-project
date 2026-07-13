package com.core.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items // FIX: без этого импорта items() внутри LazyVerticalGrid не скомпилируется
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    // Простое состояние поля ввода AI-агента (заглушка логики -
    // сюда позже можно подключить реальный вызов API)
    var query by remember { mutableStateOf("") }
    var lastReply by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("⚡ CORE", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Search, contentDescription = "Search") }
                    IconButton(onClick = {}) { Icon(Icons.Default.Settings, contentDescription = "Settings") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("👋 Добрый день, Александр!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "📊 12 файлов сегодня",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("📂 Категории", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(250.dp)
            ) {
                items(categories) { category ->
                    CategoryCard(category = category)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🤖", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("AI Агент", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(checked = true, onCheckedChange = {})
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = query,
                            onValueChange = { query = it },
                            placeholder = { Text("Чем могу помочь?") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        IconButton(onClick = { /* TODO: запуск распознавания речи */ }) {
                            Icon(Icons.Default.Mic, contentDescription = "Voice")
                        }
                        IconButton(onClick = {
                            if (query.isNotBlank()) {
                                lastReply = "Вы спросили: \"$query\" — логика ответа пока не подключена."
                                query = ""
                            }
                        }) {
                            Icon(Icons.Default.Send, contentDescription = "Send")
                        }
                    }
                    lastReply?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(it, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

data class Category(
    val icon: ImageVector,
    val name: String,
    val count: Int,
    val color: Color
)

val categories = listOf(
    Category(Icons.Default.Image, "Изображения", 234, Color(0xFF4CAF50)),
    Category(Icons.Default.Videocam, "Видео", 45, Color(0xFF2196F3)),
    Category(Icons.Default.MusicNote, "Аудио", 78, Color(0xFFFF9800)),
    Category(Icons.Default.Description, "Документы", 56, Color(0xFF9C27B0)),
    Category(Icons.Default.Folder, "Загрузки", 128, Color(0xFF607D8B)),
    Category(Icons.Default.Cloud, "Облако", 12, Color(0xFF00BCD4))
)

@Composable
fun CategoryCard(category: Category) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(category.icon, contentDescription = category.name, tint = category.color)
            Column {
                Text(category.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(
                    "${category.count} файлов",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
