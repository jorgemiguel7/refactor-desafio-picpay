package com.picpay.desafio.android.presentation.contacts

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.contacts.components.ItemContactHeader
import com.picpay.desafio.android.presentation.contacts.components.ItemUser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ContactsScreen(
    uiState: ContactsUiState,
    effect: SharedFlow<ContactsEffect>
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        effect.collectLatest { effect ->
            when (effect) {
                is ContactsEffect.ShowToastError -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFF1D1E20)
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag("contacts_list")
        ) {
            item(key = "header") {
                ItemContactHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 48.dp, bottom = 24.dp),
                    title = stringResource(R.string.title)
                )
            }

            items(
                items = uiState.users,
                key = { it.id },
                contentType = { "user" }
            ) { user ->
                ItemUser(
                    name = user.name,
                    username = user.username,
                    imageUrl = user.image,
                )
            }

            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .testTag("progress_indicator"),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF11C76F)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val users = listOf(
        User(id = 1, name = "John Doe", username = "johndoe", image = "avatar_url"),
        User(id = 2, name = "Jane Doe", username = "janedoe", image = "avatar_url"),
        User(id = 3, name = "Sam Smith", username = "samsmith", image = "avatar_url"),
        User(id = 4, name = "Alice Johnson", username = "alicejohnson", image = "avatar_url"),
        User(id = 5, name = "Bob Brown", username = "bobbrown", image = "avatar_url"),
        User(id = 6, name = "Charlie Black", username = "charlieblack", image = "avatar_url"),
        User(id = 7, name = "Daisy White", username = "daisywhite", image = "avatar_url"),
        User(id = 8, name = "Ethan Green", username = "ethangreen", image = "avatar_url"),
        User(id = 9, name = "Fiona Blue", username = "fionablue", image = "avatar_url"),
        User(id = 10, name = "George Yellow", username = "georgeyellow", image = "avatar_url"),
    )

    ContactsScreen(
        uiState = ContactsUiState(
            isLoading = false,
            users = users
        ),
        effect = MutableSharedFlow()
    )
}

@Preview
@Composable
private fun PreviewLoading() {
    ContactsScreen(
        uiState = ContactsUiState(
            isLoading = true,
            users = emptyList()
        ),
        effect = MutableSharedFlow()
    )
}