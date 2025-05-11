package com.picpay.desafio.android.presentation.contacts.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ItemContactHeader(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        modifier = modifier,
        text = title,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = 28.sp,
        maxLines = 1,
    )
}

@Preview
@Composable
private fun Preview() {
    ItemContactHeader(
        title = "Title"
    )
}